package br.gov.inst.atlan.userapi.services;

import br.gov.inst.atlan.userapi.cache.AdminUser;
import br.gov.inst.atlan.userapi.domain.User;
import br.gov.inst.atlan.userapi.domain.enums.PerfilEnum;
import br.gov.inst.atlan.userapi.exceptions.UserNotAdminException;
import br.gov.inst.atlan.userapi.exceptions.UserNotFoundException;
import br.gov.inst.atlan.userapi.repositories.AdminUserRepository;
import br.gov.inst.atlan.userapi.repositories.UserRepository;
import br.gov.inst.atlan.userapi.rest.v1.dto.UserDTO;
import br.gov.inst.atlan.userapi.rest.v1.dto.UserDTOPagedList;
import br.gov.inst.atlan.userapi.rest.v1.mappers.UserMapper;
import br.gov.inst.atlan.userapi.security.UserSS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder pe;

    /**
     * Retorna o usuário vinculado a sessão
     * @return UserSS
     */
    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    private User findByUserId(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o id: " + userId));
    }

    public UserDTO findById(UUID userId) {
        UserDTO userDTO = this.userMapper.userToUserDTO(this.findByUserId(userId));
        userDTO.setPassword(null);
        return userDTO;
    }

    public UserDTOPagedList listUsers(PageRequest pageRequest) {
        Page<User> userPage = this.userRepository.findAll(pageRequest);
        Stream<UserDTO> userDTOStream = userPage.getContent().stream().map(user -> {
            UserDTO userDTO = this.userMapper.userToUserDTO(user);
            userDTO.setPassword(null);
            return userDTO;
        });
        return new UserDTOPagedList(userDTOStream.collect(Collectors.toList()), PageRequest.of(userPage.getPageable().getPageNumber(), userPage.getPageable().getPageSize()),
                userPage.getTotalElements());
    }

    @Transactional
    public UserDTO insertUser(UserDTO userDTO) {
        userDTO.setId(null);
        log.info("Inserindo usuário com nome: {} , email: {} e login: {}", userDTO.getName(), userDTO.getEmail(), userDTO.getLogin());
        userDTO.setPassword(this.pe.encode(userDTO.getPassword()));
        User user = this.userMapper.userDtoToUser(userDTO);

        this.userRepository.saveAndFlush(user);
        if (user.isAdmin()) {
            this.adminUserRepository.save(new AdminUser(user.getId()));
        }
        userDTO = this.userMapper.userToUserDTO(user);
        userDTO.setPassword(null);
        return userDTO;
    }

    @Transactional
    public void updateUser(UUID userId, UserDTO userDTO) {
        UserSS userSS = UserService.authenticated();
        boolean hasPassword = userDTO.getPassword() != null && !userDTO.getPassword().isBlank();
        if (hasPassword) {
            userDTO.setPassword(this.pe.encode(userDTO.getPassword()));
        }
        if (!userSS.hasRole(PerfilEnum.ADMIN) && hasPassword) {
            log.info("Usuário não é admin");
            throw new UserNotAdminException(userSS.getId().toString());
        }
        // Verifica se o usuário existe na base
        User user = this.findByUserId(userId);
        user.update(userDTO);
        this.userRepository.saveAndFlush(user);
        if (user.isAdmin()) {
            this.adminUserRepository.save(new AdminUser(user.getId()));
        }
    }

    @Transactional
    public void deleteUser(UUID userId) {
        log.info("Apagando usuário com id {}", userId);
        User user = this.findByUserId(userId);
        this.userRepository.delete(user);
        this.adminUserRepository.deleteById(userId);
    }
}

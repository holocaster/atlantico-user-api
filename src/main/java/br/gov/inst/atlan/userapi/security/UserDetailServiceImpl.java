package br.gov.inst.atlan.userapi.security;

import br.gov.inst.atlan.userapi.domain.User;
import br.gov.inst.atlan.userapi.domain.enums.PerfilEnum;
import br.gov.inst.atlan.userapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe que sobreescreve o padrão do security para procurar na base o usuário
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = this.userRepository.findByLoginIgnoreCase(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        Set<PerfilEnum> perfis = new HashSet<>();
        perfis.add(user.isAdmin() ? PerfilEnum.ADMIN : PerfilEnum.USER);
        return new UserSS(user.getId(), user.getEmail(), user.getLogin(), user.getPassword(), perfis);
    }
}

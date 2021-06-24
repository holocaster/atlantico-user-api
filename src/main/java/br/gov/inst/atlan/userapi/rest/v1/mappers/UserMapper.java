package br.gov.inst.atlan.userapi.rest.v1.mappers;

import br.gov.inst.atlan.userapi.domain.User;
import br.gov.inst.atlan.userapi.rest.v1.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    User userDtoToUser(UserDTO userDTO);
}

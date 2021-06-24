package br.gov.inst.atlan.userapi.services;

import br.gov.inst.atlan.userapi.domain.User;
import br.gov.inst.atlan.userapi.events.EmailEvent;
import br.gov.inst.atlan.userapi.repositories.UserRepository;
import br.gov.inst.atlan.userapi.rest.v1.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final RabbitTemplate rabbitTemplate;

    private final UserService userService;

    public void sendEmail(UUID userId, String email) {
        UserDTO userDTO = this.userService.findById(userId);
        log.info("Colocando email na fila para usuário com id {} e nome" , userId, userDTO.getName());
        this.rabbitTemplate.convertAndSend("email", new EmailEvent(userId, email));
    }
}

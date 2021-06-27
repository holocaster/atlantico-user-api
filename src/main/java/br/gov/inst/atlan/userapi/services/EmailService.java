package br.gov.inst.atlan.userapi.services;

import br.gov.inst.atlan.userapi.events.EmailEvent;
import br.gov.inst.atlan.userapi.rest.v1.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${userapi.rabbitmq.exchange}")
    private String exchange;

    @Value("${userapi.rabbitmq.routingkey}")
    private String routingkey;

    private final RabbitTemplate rabbitTemplate;

    private final UserService userService;


    public void sendEmail(UUID userId, String email) {
        log.info("Procurando pelo usuário com id {}", userId);
        UserDTO userDTO = this.userService.findById(userId);
        log.info("Colocando email na fila para usuário com id {} e nome {}" , userId, userDTO.getName());
        this.rabbitTemplate.convertAndSend(this.exchange, this.routingkey, new EmailEvent(userId, email));
    }
}

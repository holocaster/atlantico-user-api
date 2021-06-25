package br.gov.inst.atlan.userapi.rest.v1;

import br.gov.inst.atlan.userapi.services.EmailService;
import br.gov.inst.atlan.userapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequestMapping("api/v1/email")
@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailRest {

    private final EmailService emailService;

    @PostMapping("{userId}")
    public ResponseEntity sendEmail(@PathVariable("userId") UUID uuid, @NotNull @RequestBody String email) {
        log.info("Usuario autenticado: " + UserService.authenticated());
        this.emailService.sendEmail(uuid, email);
        return ResponseEntity.noContent().build();
    }
}

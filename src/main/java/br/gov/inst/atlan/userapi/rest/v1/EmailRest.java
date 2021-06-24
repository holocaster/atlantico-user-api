package br.gov.inst.atlan.userapi.rest.v1;

import br.gov.inst.atlan.userapi.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequestMapping("api/v1/email")
@RestController
@RequiredArgsConstructor
public class EmailRest {

    private final EmailService emailService;

    @PostMapping("{userId}")
    public ResponseEntity sendEmail(@PathVariable("userId") UUID uuid, @NotNull @RequestBody String email) {
        this.emailService.sendEmail(uuid, email);
        return ResponseEntity.noContent().build();
    }
}

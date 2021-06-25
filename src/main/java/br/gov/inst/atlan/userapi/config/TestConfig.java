package br.gov.inst.atlan.userapi.config;

import br.gov.inst.atlan.userapi.domain.User;
import br.gov.inst.atlan.userapi.domain.enums.SimNaoEnum;
import br.gov.inst.atlan.userapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Profile("dev")
public class TestConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Bean
    public boolean instantiateDatabase() {
        this.userRepository.saveAndFlush(User.builder().admin(SimNaoEnum.NAO).email("1@gmail.com").password(this.pe.encode("123")).login("guest")
                .name("Teste Dev para login").build());
        this.userRepository.saveAndFlush(User.builder().admin(SimNaoEnum.SIM).email("2@gmail.com").password(this.pe.encode("123")).login("admin")
                .name("Teste admin para login").build());
        return true;
    }

}

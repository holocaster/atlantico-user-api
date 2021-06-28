package br.gov.inst.atlan.userapi.config;

import br.gov.inst.atlan.userapi.cache.AdminUser;
import br.gov.inst.atlan.userapi.domain.User;
import br.gov.inst.atlan.userapi.domain.enums.SimNaoEnum;
import br.gov.inst.atlan.userapi.repositories.AdminUserRepository;
import br.gov.inst.atlan.userapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@Configuration
@Profile("dev")
public class TestConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Bean
    public boolean instantiateDatabase() {
        Stream.iterate(1, i -> i + 1).limit(15).forEach(number -> {
                    this.userRepository.saveAndFlush(User.builder().admin(SimNaoEnum.NAO).email(number + "@gmail.com").password(this.pe.encode("123")).login("guest" + number)
                            .name("Teste Dev para login " + number).build());
                }
        );

        final User user = this.userRepository.saveAndFlush(User.builder().admin(SimNaoEnum.SIM).email("admin@gmail.com").password(this.pe.encode("123")).login("admin")
                .name("Teste admin para login").build());
        this.adminUserRepository.deleteAll();
        this.adminUserRepository.save(new AdminUser(user.getId()));
        return true;
    }

}

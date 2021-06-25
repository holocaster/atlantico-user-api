package br.gov.inst.atlan.userapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.PostConstruct;

@EnableRedisHttpSession
@Slf4j
public class SessionConfig {

    @Value("${jwt.expiration}")
    private Integer maxInactiveIntervalInSeconds;

    @Autowired
    private RedisIndexedSessionRepository sessionRepository;

    @PostConstruct
    private void afterPropertiesSet() {
        log.info("Colocando sessão com intervalo de {} segundos", this.maxInactiveIntervalInSeconds);
        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
    }
}

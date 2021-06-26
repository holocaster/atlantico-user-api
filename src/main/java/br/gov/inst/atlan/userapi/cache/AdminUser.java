package br.gov.inst.atlan.userapi.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("adminUser")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdminUser implements Serializable {

    private UUID id;
}

package br.gov.inst.atlan.userapi.repositories;

import br.gov.inst.atlan.userapi.cache.AdminUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminUserRepository extends CrudRepository<AdminUser, UUID> {
}

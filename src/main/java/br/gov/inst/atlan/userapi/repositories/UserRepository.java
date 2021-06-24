package br.gov.inst.atlan.userapi.repositories;

import br.gov.inst.atlan.userapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Transactional(readOnly = true)
    public User findByEmailIgnoreCase(final String name);
}

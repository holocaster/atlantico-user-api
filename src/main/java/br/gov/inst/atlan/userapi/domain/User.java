package br.gov.inst.atlan.userapi.domain;

import br.gov.inst.atlan.userapi.domain.enums.SimNaoEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuario")
@Builder
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "admin", length = 3, nullable = false)
    private SimNaoEnum admin;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "login", length = 50, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    public boolean isAdmin() {
        return SimNaoEnum.SIM.equals(this.admin);
    }

}

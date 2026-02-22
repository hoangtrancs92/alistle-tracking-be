package alistle.com.identifyservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "users")
@DynamicUpdate
@DynamicInsert
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false, length = 255)
    String email;
    @Column(nullable = false, length = 255)
    String password;
}

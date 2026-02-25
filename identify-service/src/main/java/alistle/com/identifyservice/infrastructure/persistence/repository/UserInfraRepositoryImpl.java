package alistle.com.identifyservice.infrastructure.persistence.repository;

import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.repository.UserDomainRepository;
import alistle.com.identifyservice.infrastructure.persistence.entity.UserEntity;
import alistle.com.identifyservice.infrastructure.persistence.mapper.UserEntityMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInfraRepositoryImpl implements UserDomainRepository {
    UserJpaRepository userJpaRepository;
    UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return userEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userFindByEmail = userJpaRepository.findByEmail(email);
        System.out.println(userFindByEmail);
        return userFindByEmail.map(userEntityMapper::toDomain);
//        return userJpaRepository.findByEmail(email)
//                .map(userEntityMapper::toDomain);
    }
}

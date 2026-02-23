package alistle.com.identifyservice.infrastructure.persistence.repository;

import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.domain.model.UserEmail;
import alistle.com.identifyservice.domain.repository.UserDomainRepository;
import alistle.com.identifyservice.infrastructure.persistence.entity.UserEntity;
import alistle.com.identifyservice.infrastructure.persistence.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserInfraRepositoryImpl implements UserDomainRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return userEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(UserEmail email) {
        return userJpaRepository.findByEmail(email.value())
                .map(userEntityMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(UserEmail email) {
        return userJpaRepository.existsByEmail(email.value());
    }
}

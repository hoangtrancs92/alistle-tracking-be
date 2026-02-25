package alistle.com.identifyservice.infrastructure.persistence.mapper;

import alistle.com.identifyservice.domain.model.User;
import alistle.com.identifyservice.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity userEntity);
}

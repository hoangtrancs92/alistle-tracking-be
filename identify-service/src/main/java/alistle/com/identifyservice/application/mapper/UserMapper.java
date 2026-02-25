package alistle.com.identifyservice.application.mapper;

import org.mapstruct.Mapper;
import alistle.com.identifyservice.application.dto.request.CreateUserRequest;
import alistle.com.identifyservice.application.dto.response.UserResponse;
import alistle.com.identifyservice.domain.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    User toDomain(CreateUserRequest request);
}

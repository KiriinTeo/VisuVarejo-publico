package io.github.kiriinteo.visuvarejo.infra.persistence.mapper;

import io.github.kiriinteo.visuvarejo.core.domain.User;
import io.github.kiriinteo.visuvarejo.core.domain.Role;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                CompanyMapper.toEntity(user.getCompany())
        );
    }

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                Role.valueOf(entity.getRole()),
                CompanyMapper.toDomain(entity.getCompany())
        );
    }
}
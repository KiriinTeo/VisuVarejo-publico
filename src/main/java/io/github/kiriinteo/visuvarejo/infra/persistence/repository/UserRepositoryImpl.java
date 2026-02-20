package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.core.domain.User;
import io.github.kiriinteo.visuvarejo.core.port.UserRepository;
import io.github.kiriinteo.visuvarejo.infra.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        return UserMapper.toDomain(
                jpaRepository.save(UserMapper.toEntity(user))
        );
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }
}
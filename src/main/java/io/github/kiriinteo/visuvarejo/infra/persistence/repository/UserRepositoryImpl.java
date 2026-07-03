package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.core.domain.User;
import io.github.kiriinteo.visuvarejo.core.port.UserRepository;
import io.github.kiriinteo.visuvarejo.infra.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override   
    public Optional<User> findByEmailAndCompanyId(String email, UUID companyId) {
        return jpaRepository.findByEmailAndCompanyId(email, companyId)
                .map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAllByCompanyId(UUID companyId) {
        return jpaRepository.findAll()
                .stream()
                .filter(userEntity -> companyId.equals(userEntity.getCompanyId()))
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }
    
}
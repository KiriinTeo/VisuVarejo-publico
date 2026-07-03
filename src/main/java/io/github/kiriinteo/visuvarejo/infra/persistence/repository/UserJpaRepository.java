package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.infra.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    @Query("""
        SELECT u FROM UserEntity u
        JOIN FETCH u.company
        WHERE u.email = :email
    """)
    Optional<UserEntity> findByEmail(@Param("email") String email);

    Optional<UserEntity> findByEmailAndCompanyId(String email, UUID companyId);


}
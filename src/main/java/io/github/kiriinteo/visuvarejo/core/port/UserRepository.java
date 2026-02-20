package io.github.kiriinteo.visuvarejo.core.port;

import io.github.kiriinteo.visuvarejo.core.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);
}
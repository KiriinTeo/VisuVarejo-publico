package io.github.kiriinteo.visuvarejo.application.identity;

import io.github.kiriinteo.visuvarejo.core.domain.Company;
import io.github.kiriinteo.visuvarejo.core.domain.Role;
import io.github.kiriinteo.visuvarejo.core.domain.User;
import io.github.kiriinteo.visuvarejo.core.exception.DomainException;
import io.github.kiriinteo.visuvarejo.core.port.CompanyRepository;
import io.github.kiriinteo.visuvarejo.core.port.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository userRepository,
                               CompanyRepository companyRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResult execute(RegisterCommand command) {

        userRepository.findByEmail(command.email())
                .ifPresent(u -> {
                    throw new DomainException("Email already registered.");
                });

        Company company = new Company(
                command.companyName(),
                command.companyDocument()
        );

        company = companyRepository.save(company);

        User user = new User(
                command.name(),
                command.email(),
                passwordEncoder.encode(command.password()),
                Role.ADMIN,
                company,
                company.getTenantId()
        );

        user = userRepository.save(user);

        return new RegisterResult(
                user.getId().toString(),
                company.getId().toString(),
                user.getEmail()
        );
    }

    public record RegisterCommand(
            String name,
            String email,
            String password,
            String companyName,
            String companyDocument
    ) {}

    public record RegisterResult(
            String userId,
            String companyId,
            String email
    ) {}
}
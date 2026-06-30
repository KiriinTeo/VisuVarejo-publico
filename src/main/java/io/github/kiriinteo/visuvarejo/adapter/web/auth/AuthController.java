package io.github.kiriinteo.visuvarejo.adapter.web.auth;

import io.github.kiriinteo.visuvarejo.adapter.web.auth.dto.LoginRequest;
import io.github.kiriinteo.visuvarejo.adapter.web.auth.dto.LoginResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.auth.dto.RegisterRequest;
import io.github.kiriinteo.visuvarejo.adapter.web.auth.dto.RegisterResponse;
import io.github.kiriinteo.visuvarejo.application.identity.RegisterUserUseCase;
import io.github.kiriinteo.visuvarejo.infra.persistence.repository.UserRepositoryImpl;
import io.github.kiriinteo.visuvarejo.infra.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private final RegisterUserUseCase registerUserUseCase;
        private final UserRepositoryImpl userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtTokenProvider jwtTokenProvider;

        public AuthController(RegisterUserUseCase registerUserUseCase, UserRepositoryImpl userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
                this.registerUserUseCase = registerUserUseCase;
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtTokenProvider = jwtTokenProvider;
        }

        @PostMapping("/register")
        public ResponseEntity<RegisterResponse> register(
                @RequestBody RegisterRequest request
        ) {

                var result = registerUserUseCase.execute(
                        new RegisterUserUseCase.RegisterCommand(
                                request.name(),
                                request.email(),
                                request.password(),
                                request.companyName(),
                                request.companyDocument()
                        )
                );

                return ResponseEntity.ok(
                        new RegisterResponse(
                                result.userId(),
                                result.companyId(),
                                result.email(),
                                "Usuário registrado com sucesso"
                        )
                );
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(
                @RequestBody LoginRequest request
        ) {

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getCompany().getId().toString(),
                user.getTenantId()
        );

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
        }
}
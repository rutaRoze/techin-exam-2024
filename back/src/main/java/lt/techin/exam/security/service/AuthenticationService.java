package lt.techin.exam.security.service;

import jakarta.persistence.EntityNotFoundException;
import lt.techin.exam.dto.request.UserRequest;
import lt.techin.exam.dto.response.UserResponse;
import lt.techin.exam.exception.UserAlreadyExist;
import lt.techin.exam.mapper.UserMapper;
import lt.techin.exam.persistance.UserRepository;
import lt.techin.exam.persistance.entity.User;
import lt.techin.exam.security.mapper.SecurityUserDetailsMapper;
import lt.techin.exam.security.model.AuthenticationRequest;
import lt.techin.exam.security.model.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private SecurityUserDetailsMapper securityUserDetailsMapper;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, UserMapper userMapper,
                                 PasswordEncoder passwordEncoder, SecurityUserDetailsMapper securityUserDetailsMapper,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.securityUserDetailsMapper = securityUserDetailsMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserRequest registerRequest) {

        if (checkIfUserExistsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExist(String.format("User with email %s already exists", registerRequest.getEmail()));
        }

        User userToSave = userMapper.userRequestToUser(registerRequest);

        userToSave.setName(sanitizeData(registerRequest.getName()));
        userToSave.setSurname(sanitizeData(registerRequest.getSurname()));
        userToSave.setEmail(sanitizeData(registerRequest.getEmail()).toLowerCase(Locale.ROOT));
        userToSave.setPassword(passwordEncoder.encode(sanitizeData(registerRequest.getPassword())));
        userToSave.setDefaultRole();

        User savedUser = userRepository.save(userToSave);

        String jwtToken = jwtService.generateToken(securityUserDetailsMapper.mapToSecurityUserDetails(savedUser));
        UserResponse userResponse = userMapper.userToUserResponse(savedUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userResponse(userResponse)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticateRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateRequest.getEmail(),
                        authenticateRequest.getPassword()
                )
        );

        User userEntity = userRepository.findByEmail(authenticateRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Entity not hound"));

        String jwtToken = jwtService.generateToken(securityUserDetailsMapper.mapToSecurityUserDetails(userEntity));
        UserResponse userResponse = userMapper.userToUserResponse(userEntity);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userResponse(userResponse)
                .build();
    }

    private boolean checkIfUserExistsByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    private String sanitizeData(String data) {
        return data.trim();
    }
}

package lt.techin.exam.security.controller;

import jakarta.validation.Valid;
import lt.techin.exam.dto.request.UserRequest;
import lt.techin.exam.security.model.AuthenticationRequest;
import lt.techin.exam.security.model.AuthenticationResponse;
import lt.techin.exam.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody UserRequest registerRequest
    ) {
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest authenticateRequest
    ) {

        return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));

    }
}

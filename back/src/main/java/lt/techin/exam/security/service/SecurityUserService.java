package lt.techin.exam.security.service;

import jakarta.persistence.EntityNotFoundException;
import lt.techin.exam.persistance.UserRepository;
import lt.techin.exam.security.mapper.SecurityUserDetailsMapper;
import lt.techin.exam.security.model.SecurityUserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService {

    private UserRepository userRepository;
    private SecurityUserDetailsMapper securityUserDetailsMapper;

    public SecurityUserService(UserRepository userRepository, SecurityUserDetailsMapper securityUserDetailsMapper) {
        this.userRepository = userRepository;
        this.securityUserDetailsMapper = securityUserDetailsMapper;
    }

    public SecurityUserDetails findUserByEmail(String email) {
        return securityUserDetailsMapper
                .mapToSecurityUserDetails(userRepository.findByEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException("User not found")));
    }
}

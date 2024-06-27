package lt.techin.exam.config;

import lt.techin.exam.enums.Role;
import lt.techin.exam.persistance.UserRepository;
import lt.techin.exam.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialData {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public InitialData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initialDataRunner() {
        return runner -> {
            addUsers();
        };
    }

    private void addUsers() {
        final User user1 = User.builder()
                .name("John")
                .surname("Smith")
                .email("jhon@mail.com")
                .password(passwordEncoder.encode("Password1!"))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user1);

        final User user2 = User.builder()
                .name("Emily")
                .surname("Johnson")
                .email("emily@mail.com")
                .password(passwordEncoder.encode("Password1!"))
                .role(Role.USER)
                .build();
        userRepository.save(user2);

        final User user3 = User.builder()
                .name("David")
                .surname("Brown")
                .email("david@mail.com")
                .password(passwordEncoder.encode("Password1!"))
                .role(Role.USER)
                .build();
        userRepository.save(user3);

        final User user4 = User.builder()
                .name("Sophia")
                .surname("Williams")
                .email("sophia@mail.com")
                .password(passwordEncoder.encode("Password1!"))
                .role(Role.USER)
                .build();
        userRepository.save(user4);
    }
}

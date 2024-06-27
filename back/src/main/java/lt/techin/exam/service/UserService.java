package lt.techin.exam.service;

import jakarta.persistence.EntityNotFoundException;
import lt.techin.exam.dto.response.UserResponse;
import lt.techin.exam.mapper.UserMapper;
import lt.techin.exam.persistance.UserRepository;
import lt.techin.exam.persistance.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse findUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by ID: " + id));

        return userMapper.userToUserResponse(user);
    }
}

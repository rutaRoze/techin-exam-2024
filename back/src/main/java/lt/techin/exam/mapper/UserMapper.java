package lt.techin.exam.mapper;

import lt.techin.exam.dto.request.UserRequest;
import lt.techin.exam.dto.response.UserResponse;
import lt.techin.exam.persistance.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User userRequestToUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .uuid(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}

package lt.techin.exam.security.mapper;

import lt.techin.exam.persistance.entity.User;
import lt.techin.exam.security.model.SecurityUserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserDetailsMapper {

    public SecurityUserDetails mapToSecurityUserDetails(User userEntity) {
        return SecurityUserDetails.builder()
                .uuid(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build();
    }
}

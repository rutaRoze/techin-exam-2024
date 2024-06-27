package lt.techin.exam.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.techin.exam.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @NotBlank
    @Size(min = 2, max = 30)
    private String surname;

    @NotBlank
    @Email(message = "Invalid email format. Please provide a valid email address.")
    private String email;

    @NotBlank
    @Size(min = 8, max = 30)
    @Pattern(regexp = "^(?!.*\\s)(?=.*[A-Z])(?=.*\\d)(?=.*[a-z])(?=.*[!@#$%^&*()]).+$",
            message = "Invalid password format. Please provide a valid password.")
    private String password;

    private Role role;
}

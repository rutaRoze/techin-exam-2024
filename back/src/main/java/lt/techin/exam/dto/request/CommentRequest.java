package lt.techin.exam.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    @NotNull
    private UUID userId;
    @NotNull
    private Long advertId;
    @NotBlank
    @Size(max = 400)
    private String comment;
}

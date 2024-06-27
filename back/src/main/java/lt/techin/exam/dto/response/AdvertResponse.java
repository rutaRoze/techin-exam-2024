package lt.techin.exam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertResponse {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String city;
    private String categoryName;
}

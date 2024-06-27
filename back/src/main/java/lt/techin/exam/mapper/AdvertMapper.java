package lt.techin.exam.mapper;

import lt.techin.exam.dto.request.AdvertRequest;
import lt.techin.exam.dto.response.AdvertResponse;
import lt.techin.exam.persistance.entity.Advert;
import lt.techin.exam.persistance.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class AdvertMapper {
    public Advert requestToBook(AdvertRequest advertRequest, Category category) {
        return Advert.builder()
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .price(advertRequest.getPrice())
                .city(advertRequest.getCity())
                .category(category)
                .build();
    }

    public AdvertResponse bookToResponse(Advert advert) {
        return AdvertResponse.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .price(advert.getPrice())
                .city(advert.getCity())
                .categoryName(advert.getCategory().getName())
                .build();
    }
}

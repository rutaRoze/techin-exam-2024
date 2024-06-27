package lt.techin.exam.mapper;

import lt.techin.exam.dto.request.CategoryRequest;
import lt.techin.exam.dto.response.CategoryResponse;
import lt.techin.exam.persistance.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category categoryRequestToCategory(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }

    public CategoryResponse categoryToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}

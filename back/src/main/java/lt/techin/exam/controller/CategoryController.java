package lt.techin.exam.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lt.techin.exam.dto.request.CategoryRequest;
import lt.techin.exam.dto.response.CategoryResponse;
import lt.techin.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("category")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse userResponse = categoryService.saveCategory(categoryRequest);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Min(1) @PathVariable Long id) {
        CategoryResponse userResponse = categoryService.findCategoryById(id);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> userResponse = categoryService.findAllCategories();

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategoryById(
            @Min(1) @PathVariable Long id,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse userResponse = categoryService.updateCategoryById(id, categoryRequest);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(
            @Min(1) @PathVariable Long id) {
        categoryService.deleteCategoryById(id);

        return ResponseEntity.ok(String.format("Category by ID %d was successfully deleted from data base", id));
    }
}

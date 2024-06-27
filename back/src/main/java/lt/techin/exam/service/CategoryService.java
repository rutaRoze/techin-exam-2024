package lt.techin.exam.service;

import jakarta.persistence.EntityNotFoundException;
import lt.techin.exam.dto.request.CategoryRequest;
import lt.techin.exam.dto.response.CategoryResponse;
import lt.techin.exam.exception.CategoryAlreadyExist;
import lt.techin.exam.exception.NoChangesMadeException;
import lt.techin.exam.mapper.CategoryMapper;
import lt.techin.exam.persistance.AdvertRepository;
import lt.techin.exam.persistance.CategoryRepository;
import lt.techin.exam.persistance.entity.Advert;
import lt.techin.exam.persistance.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    AdvertRepository advertRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
                           AdvertRepository advertRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.advertRepository = advertRepository;
    }

    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        if (checkIfCategoryExists(categoryRequest.getName())) {
            throw new CategoryAlreadyExist(String.format("Category by name %s already exists", categoryRequest.getName()));
        }

        Category categoryToSave = categoryMapper.categoryRequestToCategory(categoryRequest);
        categoryToSave.setName(categoryToSave.getName().trim());
        Category savedCategory = categoryRepository.save(categoryToSave);

        return categoryMapper.categoryToCategoryResponse(savedCategory);
    }

    public CategoryResponse findCategoryById(Long id) {
        Category category = getCategoryByIdOrThrow(id);

        return categoryMapper.categoryToCategoryResponse(category);
    }

    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> categoryMapper.categoryToCategoryResponse(category))
                .collect(Collectors.toList());
    }

    public CategoryResponse updateCategoryById(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = getCategoryByIdOrThrow(id);

        if (isDataEqual(existingCategory, categoryRequest)) {
            throw new NoChangesMadeException("Category entry was not updated as no changes of entry were made");
        }

        existingCategory.setName(categoryRequest.getName().trim());
        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.categoryToCategoryResponse(updatedCategory);
    }

    public void deleteCategoryById(Long id) {
        getCategoryByIdOrThrow(id);

        List<Advert> advertList = advertRepository.findAllByCategoryId(id);
        advertList.forEach(advert -> advert.setCategory(null));
        advertRepository.saveAll(advertList);

        categoryRepository.deleteById(id);
    }

    public Category getCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found by ID: " + id));
    }

    private boolean checkIfCategoryExists(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    private boolean isDataEqual(Category existingCategory, CategoryRequest categoryRequest) {
        return existingCategory.getName().equals(categoryRequest.getName().trim());
    }
}

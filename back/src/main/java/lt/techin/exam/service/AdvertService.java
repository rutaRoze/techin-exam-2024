package lt.techin.exam.service;

import jakarta.persistence.EntityNotFoundException;
import lt.techin.exam.dto.request.AdvertRequest;
import lt.techin.exam.dto.response.AdvertResponse;
import lt.techin.exam.exception.NoChangesMadeException;
import lt.techin.exam.mapper.AdvertMapper;
import lt.techin.exam.persistance.AdvertRepository;
import lt.techin.exam.persistance.CategoryRepository;
import lt.techin.exam.persistance.entity.Advert;
import lt.techin.exam.persistance.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertService {

    AdvertRepository advertRepository;
    CategoryService categoryService;
    CategoryRepository categoryRepository;
    AdvertMapper advertMapper;

    @Autowired
    public AdvertService(AdvertRepository bookRepository, CategoryService categoryService,
                         CategoryRepository categoryRepository, AdvertMapper advertMapper) {
        this.advertRepository = bookRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.advertMapper = advertMapper;
    }

    public AdvertResponse saveAdvert(AdvertRequest advertRequest) {

        Category category = categoryService.getCategoryByIdOrThrow(advertRequest.getCategoryId());
        Advert advertToSave = advertMapper.requestToBook(advertRequest, category);
        Advert savedBook = advertRepository.save(advertToSave);

        return advertMapper.bookToResponse(savedBook);
    }

    public AdvertResponse findAdvertById(Long id) {
        Advert advert = getAdvertByIdOrThrow(id);
        return advertMapper.bookToResponse(advert);
    }

    public List<AdvertResponse> findAllAdverts() {
        return advertRepository.findAll().stream()
                .map(book -> advertMapper.bookToResponse(book))
                .collect(Collectors.toList());
    }

    public List<AdvertResponse> findAdvertsByCategory(String categoryName) {
        Category category = categoryRepository.findByNameIgnoreCase(categoryName.trim())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category by %s name not found", categoryName)));

        return advertRepository.findByCategory(category).stream()
                .map(book -> advertMapper.bookToResponse(book))
                .collect(Collectors.toList());
    }

    public List<AdvertResponse> findAdvertsByTitle(String bookName) {
        return advertRepository.findByTitleIgnoreCase(bookName.trim()).stream()
                .map(book -> advertMapper.bookToResponse(book))
                .collect(Collectors.toList());
    }

    public AdvertResponse updateAdvertById(Long id, AdvertRequest advertRequest) {
        Advert existingAdvert = getAdvertByIdOrThrow(id);
        Category category = categoryService.getCategoryByIdOrThrow(advertRequest.getCategoryId());

        if (isChangesMade(existingAdvert, advertRequest)) {
            throw new NoChangesMadeException("Book entry was not updated as no changes of entry were made");
        }

        updateBookData(existingAdvert, advertRequest, category);
        Advert savedAdvert = advertRepository.save(existingAdvert);

        return advertMapper.bookToResponse(savedAdvert);
    }

    public void deleteAdvertById(Long id) {
        getAdvertByIdOrThrow(id);
        advertRepository.deleteById(id);
    }

    private Advert getAdvertByIdOrThrow(Long id) {
        return advertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found by ID: " + id));
    }

    private boolean isChangesMade(Advert existingAdvert, AdvertRequest advertRequest) {
        return existingAdvert.getTitle().equals(advertRequest.getTitle().trim()) &&
                existingAdvert.getDescription().equals(advertRequest.getDescription().trim()) &&
                existingAdvert.getCity().equals(advertRequest.getCity().trim()) &&
                existingAdvert.getPrice() == advertRequest.getPrice() &&
                existingAdvert.getCategory().getId().equals(advertRequest.getCategoryId());
    }

    private void updateBookData(Advert existingAdvert, AdvertRequest advertRequest, Category category) {
        existingAdvert.setTitle(advertRequest.getTitle().trim());
        existingAdvert.setDescription(advertRequest.getDescription().trim());
        existingAdvert.setPrice(advertRequest.getPrice());
        existingAdvert.setCity(advertRequest.getCity());
        existingAdvert.setCategory(category);
    }
}

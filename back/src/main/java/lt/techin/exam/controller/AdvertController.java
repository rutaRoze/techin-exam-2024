package lt.techin.exam.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lt.techin.exam.dto.request.AdvertRequest;
import lt.techin.exam.dto.response.AdvertResponse;
import lt.techin.exam.service.AdvertService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/advert")
@CrossOrigin(origins = "http://localhost:5173")
public class AdvertController {

    private AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @PostMapping()
    public ResponseEntity<AdvertResponse> createAdvert(
            @Valid @RequestBody AdvertRequest advertRequest) {
        AdvertResponse advertResponse = advertService.saveAdvert(advertRequest);

        return new ResponseEntity<>(advertResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertResponse> getAdvertById(
            @Min(1) @PathVariable Long id) {
        AdvertResponse advertResponse = advertService.findAdvertById(id);

        return ResponseEntity.ok(advertResponse);
    }

    @GetMapping()
    public ResponseEntity<List<AdvertResponse>> getAllAdverts() {
        List<AdvertResponse> bookList = advertService.findAllAdverts();

        return ResponseEntity.ok(bookList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertResponse> updateAdvertById(
            @Min(1) @PathVariable Long id,
            @Valid @RequestBody AdvertRequest advertRequest) {
        AdvertResponse advertResponse = advertService.updateAdvertById(id, advertRequest);

        return new ResponseEntity<>(advertResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdvertById(
            @Min(1) @PathVariable Long id) {
        advertService.deleteAdvertById(id);

        return ResponseEntity.ok(String.format("Book by ID %d was successfully deleted", id));
    }

    @GetMapping("/category")
    public ResponseEntity<List<AdvertResponse>> getAdvertsByCategory(
            @RequestParam("category") @NotBlank String categoryName) {
        List<AdvertResponse> bookListByCategory = advertService.findAdvertsByCategory(categoryName);

        return ResponseEntity.ok(bookListByCategory);
    }

    @GetMapping("/title")
    public ResponseEntity<List<AdvertResponse>> getAdvertsByTitle(
            @RequestParam("title") @NotBlank String bookTitle) {
        List<AdvertResponse> bookListByName = advertService.findAdvertsByTitle(bookTitle);

        return ResponseEntity.ok(bookListByName);
    }
}

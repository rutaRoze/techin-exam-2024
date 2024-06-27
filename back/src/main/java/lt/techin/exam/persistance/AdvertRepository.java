package lt.techin.exam.persistance;

import lt.techin.exam.persistance.entity.Advert;
import lt.techin.exam.persistance.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {

    List<Advert> findByTitleIgnoreCase(String bookName);
    List<Advert> findByCategory(Category category);
    List<Advert> findAllByCategoryId(Long categoryId);
}

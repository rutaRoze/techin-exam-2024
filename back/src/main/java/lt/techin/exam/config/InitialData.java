package lt.techin.exam.config;

import lt.techin.exam.enums.Role;
import lt.techin.exam.persistance.AdvertRepository;
import lt.techin.exam.persistance.CategoryRepository;
import lt.techin.exam.persistance.UserRepository;
import lt.techin.exam.persistance.entity.Advert;
import lt.techin.exam.persistance.entity.Category;
import lt.techin.exam.persistance.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialData {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private CategoryRepository categoryRepository;

    private AdvertRepository advertRepository;

    public InitialData(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       CategoryRepository categoryRepository, AdvertRepository advertRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.advertRepository = advertRepository;
    }

    @Bean
    public CommandLineRunner initialDataRunner() {
        return runner -> {
            addUsers();
            addCategories();
            addAdverts();
        };
    }

    private void addUsers() {
        final User user1 = User.builder()
                .name("John")
                .surname("Smith")
                .email("jhon@mail.com")
                .password(passwordEncoder.encode("Password1!"))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user1);

        final User user2 = User.builder()
                .name("Emily")
                .surname("Johnson")
                .email("emily@mail.com")
                .password(passwordEncoder.encode("Password1!"))
                .role(Role.USER)
                .build();
        userRepository.save(user2);

        final User user3 = User.builder()
                .name("David")
                .surname("Brown")
                .email("david@mail.com")
                .password(passwordEncoder.encode("Password1!"))
                .role(Role.USER)
                .build();
        userRepository.save(user3);

        final User user4 = User.builder()
                .name("Sophia")
                .surname("Williams")
                .email("sophia@mail.com")
                .password(passwordEncoder.encode("Password1!"))
                .role(Role.USER)
                .build();
        userRepository.save(user4);
    }

    private void addCategories() {
        final Category category1 = Category.builder()
                .name("Furniture")
                .build();
        categoryRepository.save(category1);

        final Category category2 = Category.builder()
                .name("Clothes")
                .build();
        categoryRepository.save(category2);

        final Category category3 = Category.builder()
                .name("Pets")
                .build();
        categoryRepository.save(category3);

        final Category category4 = Category.builder()
                .name("Books")
                .build();
        categoryRepository.save(category4);
    }

    private void addAdverts() {
        Category category1 = categoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Category not found"));
        Category category2 = categoryRepository.findById(2L).orElseThrow(() -> new RuntimeException("Category not found"));
        Category category3 = categoryRepository.findById(3L).orElseThrow(() -> new RuntimeException("Category not found"));
        Category category4 = categoryRepository.findById(4L).orElseThrow(() -> new RuntimeException("Category not found"));

        final Advert advert1 = Advert.builder()
                .title("Sofa")
                .description("New and soft")
                .price(300)
                .city("Vilnius")
                .category(category1)
                .build();
        advertRepository.save(advert1);

        final Advert advert2 = Advert.builder()
                .title("Shirt")
                .description("New and soft")
                .price(10)
                .city("Kaunas")
                .category(category2)
                .build();
        advertRepository.save(advert2);

        final Advert advert3 = Advert.builder()
                .title("Pet Food")
                .description("High quality")
                .price(30)
                .city("Klaipeda")
                .category(category3)
                .build();
        advertRepository.save(advert3);

        final Advert advert4 = Advert.builder()
                .title("Dune")
                .description("Barely used")
                .price(20)
                .city("Ukmerge")
                .category(category4)
                .build();
        advertRepository.save(advert4);
    }

}

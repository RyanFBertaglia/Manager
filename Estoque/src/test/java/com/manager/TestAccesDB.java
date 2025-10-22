package com.manager;

import com.manager.repository.CategoriesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TestAccesDB {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Test
    public void testAccesDB() {
        categoriesRepository.findAll().forEach(System.out::println);
    }
}

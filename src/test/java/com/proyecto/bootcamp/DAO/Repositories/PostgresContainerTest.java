package com.proyecto.bootcamp.DAO.Repositories;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@SpringBootTest
@DirtiesContext
@Testcontainers
public class PostgresContainerTest {
    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres")
      .withDatabaseName("postgresTest")
      .withUsername("postgres")
      .withPassword("1234")
      .withCopyFileToContainer(
            MountableFile.forClasspathResource("init.sql"), 
        "/docker-entrypoint-initdb.d/init.sql"
      );

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        container.start();
        String url = container.getJdbcUrl();
        registry.add("spring.datasource.url", ()->url);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.jpa.properties.hibernate.default_schema",()->"materias_db");
    }
}

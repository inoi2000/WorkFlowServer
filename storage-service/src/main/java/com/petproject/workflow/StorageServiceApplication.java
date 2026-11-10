package com.petproject.workflow;

import com.petproject.workflow.store.entities.*;
import com.petproject.workflow.store.repositories.StorageRepository;
import com.petproject.workflow.store.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class StorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            StorageRepository storageRepository,
            ProductRepository productRepository
    ) {
        return args -> {

            storageRepository.deleteAll();
            productRepository.deleteAll();

            // Создаём нефтепродукты через конструктор
            Product heatingOil = new Product(
                    UUID.randomUUID(),
                    "Печное топливо",
                    0.86,
                    ProductStatus.LIQUID,
                    ProductType.PURCHASED_FOR_PRODUCTION,
                    new ArrayList<>()   // allowedStorages пока пустой
            );

            Product mazut = new Product(
                    UUID.randomUUID(),
                    "Мазут",
                    0.96,
                    ProductStatus.VISCOUS,
                    ProductType.PURCHASED_FOR_PRODUCTION,
                    new ArrayList<>()
            );

            productRepository.save(heatingOil);
            productRepository.save(mazut);

            // Создаём хранилище E1 через конструктор
            Storage e1 = new Storage(
                    UUID.randomUUID(),
                    "E1",
                    40.0,                                    // totalVolume
                    0.0,                                     // currentVolume
                    null,                                    // currentProduct
                    false,                                   // allowedMixing
                    null,                                    // outputProduct
                    StorageSector._858,                      // sector
                    StoragePlacement.ABOVE_GROUND,            // placement
                    StorageLocation.TANK_FARM,               // location
                    LocalDateTime.now(),                     // updatedAt
                    new ArrayList<>(List.of(heatingOil, mazut)) // allowedProducts
            );

            // Каскадное сохранение – продукты сохранятся автоматически
            storageRepository.save(e1);
        };
    }
}

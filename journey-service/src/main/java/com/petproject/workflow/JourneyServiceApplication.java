package com.petproject.workflow;

import com.petproject.workflow.store.entities.*;
import com.petproject.workflow.store.repositories.CarRepository;
import com.petproject.workflow.store.repositories.FuellingRepository;
import com.petproject.workflow.store.repositories.JourneyRepository;
import com.petproject.workflow.store.repositories.StatementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class JourneyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JourneyServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            CarRepository carRepository,
            FuellingRepository fuellingRepository,
            JourneyRepository journeyRepository,
            StatementRepository statementRepository
    ) {
        return args -> {
            // Очистка существующих данных (опционально)
            fuellingRepository.deleteAll();
            journeyRepository.deleteAll();
            statementRepository.deleteAll();
            carRepository.deleteAll();

            // 1. Создаем тестовые автомобили с полными данными
            Car car1 = new Car();
            car1.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
            car1.setBrand("Toyota");
            car1.setModel("Camry");
            car1.setLicensePlate("A123BC777");
            car1.setVin("1HGCM82633A123456");
            car1.setYear(2022);
            car1.setColor("Black");
            car1.setStatus(CarStatus.ACTIVE);

            Car car2 = new Car();
            car2.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
            car2.setBrand("Ford");
            car2.setModel("Focus");
            car2.setLicensePlate("B456DE777");
            car2.setVin("2FADP63E25B789012");
            car2.setYear(2021);
            car2.setColor("White");
            car2.setStatus(CarStatus.ACTIVE);

            Car car3 = new Car();
            car3.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
            car3.setBrand("Volkswagen");
            car3.setModel("Tiguan");
            car3.setLicensePlate("C789FG777");
            car3.setVin("3VWCM7AT4CM654321");
            car3.setYear(2023);
            car3.setColor("Blue");
            car3.setStatus(CarStatus.MAINTENANCE);

            carRepository.saveAll(List.of(car1, car2, car3));

            // 2. Создаем тестовые заявки (statements) - они первичны
            Statement statement1 = new Statement();
            statement1.setId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
            statement1.setLogisticianId(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
            statement1.setData("Доставка документов в офис");
            statement1.setAddress("ул. Ленина, д. 10, офис 25");
            statement1.setCreatedAt(LocalDateTime.now().minusDays(2));
            statement1.setUpdatedAt(LocalDateTime.now().minusDays(2));

            Statement statement2 = new Statement();
            statement2.setId(UUID.fromString("55555555-5555-5555-5555-555555555555"));
            statement2.setLogisticianId(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"));
            statement2.setData("Перевозка оборудования");
            statement2.setAddress("пр. Мира, д. 15, склад 3");
            statement2.setCreatedAt(LocalDateTime.now().minusDays(1));
            statement2.setUpdatedAt(LocalDateTime.now().minusDays(1));

            Statement statement3 = new Statement();
            statement3.setId(UUID.fromString("66666666-6666-6666-6666-666666666666"));
            statement3.setLogisticianId(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"));
            statement3.setData("Срочная доставка товара");
            statement3.setAddress("ул. Советская, д. 5, магазин 'Электроника'");
            statement3.setCreatedAt(LocalDateTime.now().minusHours(3));
            statement3.setUpdatedAt(LocalDateTime.now().minusHours(3));

            // Сначала сохраняем заявки
            statementRepository.saveAll(List.of(statement1, statement2, statement3));

            // 3. Создаем тестовые поездки (journeys) с полными временными метками
            Journey journey1 = new Journey();
            journey1.setId(UUID.fromString("77777777-7777-7777-7777-777777777777"));
            journey1.setStatement(statement1);
            journey1.setCar(car1);
            journey1.setDriverId(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"));
            journey1.setStatus(JourneyStatus.FINISHED);
            journey1.setCreatedAt(LocalDateTime.now().minusDays(2));
            journey1.setConfirmedAt(LocalDateTime.now().minusDays(2).plusHours(1));
            journey1.setStartedAt(LocalDateTime.now().minusDays(2).plusHours(2));
            journey1.setFinishedAt(LocalDateTime.now().minusDays(2).plusHours(4));
            journey1.setCanceledAt(null);

            Journey journey2 = new Journey();
            journey2.setId(UUID.fromString("88888888-8888-8888-8888-888888888888"));
            journey2.setStatement(statement2);
            journey2.setCar(car2);
            journey2.setDriverId(UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee"));
            journey2.setStatus(JourneyStatus.STARTED);
            journey2.setCreatedAt(LocalDateTime.now().minusDays(1));
            journey2.setConfirmedAt(LocalDateTime.now().minusDays(1).plusHours(1));
            journey2.setStartedAt(LocalDateTime.now().minusDays(1).plusHours(2));
            journey2.setFinishedAt(null);
            journey2.setCanceledAt(null);

            Journey journey3 = new Journey();
            journey3.setId(UUID.fromString("99999999-9999-9999-9999-999999999999"));
            journey3.setStatement(statement3);
            journey3.setCar(car1);
            journey3.setDriverId(UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"));
            journey3.setStatus(JourneyStatus.CONFIRMED);
            journey3.setCreatedAt(LocalDateTime.now().minusHours(3));
            journey3.setConfirmedAt(LocalDateTime.now().minusHours(2));
            journey3.setStartedAt(null);
            journey3.setFinishedAt(null);
            journey3.setCanceledAt(null);

            // Сохраняем поездки
            journeyRepository.saveAll(List.of(journey1, journey2, journey3));

            // 4. Связываем заявки с поездками (после сохранения поездок)
            statement1.setJourney(journey1);
            statement2.setJourney(journey2);
            statement3.setJourney(journey3);

            // Обновляем заявки со связями
            statementRepository.saveAll(List.of(statement1, statement2, statement3));

            // 5. Создаем тестовые заправки (fuellings) с правильными UUID
            Fuelling fuelling1 = new Fuelling();
            fuelling1.setId(UUID.fromString("a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a1"));
            fuelling1.setCar(car1);
            fuelling1.setDriverId(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"));
            fuelling1.setOperatorId(UUID.fromString("a2a2a2a2-a2a2-a2a2-a2a2-a2a2a2a2a2a2"));
            fuelling1.setVolume(45.5);
            fuelling1.setCreatedAt(LocalDateTime.now().minusDays(3));

            Fuelling fuelling2 = new Fuelling();
            fuelling2.setId(UUID.fromString("b1b1b1b1-b1b1-b1b1-b1b1-b1b1b1b1b1b1"));
            fuelling2.setCar(car2);
            fuelling2.setDriverId(UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee"));
            fuelling2.setOperatorId(UUID.fromString("b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2"));
            fuelling2.setVolume(38.0);
            fuelling2.setCreatedAt(LocalDateTime.now().minusDays(2));

            Fuelling fuelling3 = new Fuelling();
            fuelling3.setId(UUID.fromString("c1c1c1c1-c1c1-c1c1-c1c1-c1c1c1c1c1c1"));
            fuelling3.setCar(car1);
            fuelling3.setDriverId(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"));
            fuelling3.setOperatorId(UUID.fromString("a2a2a2a2-a2a2-a2a2-a2a2-a2a2a2a2a2a2"));
            fuelling3.setVolume(42.75);
            fuelling3.setCreatedAt(LocalDateTime.now().minusDays(1));

            Fuelling fuelling4 = new Fuelling();
            fuelling4.setId(UUID.fromString("d1d1d1d1-d1d1-d1d1-d1d1-d1d1d1d1d1d1"));
            fuelling4.setCar(car3);
            fuelling4.setDriverId(UUID.fromString("e1e1e1e1-e1e1-e1e1-e1e1-e1e1e1e1e1e1"));
            fuelling4.setOperatorId(UUID.fromString("f1f1f1f1-f1f1-f1f1-f1f1-f1f1f1f1f1f1"));
            fuelling4.setVolume(55.25);
            fuelling4.setCreatedAt(LocalDateTime.now().minusHours(6));

            fuellingRepository.saveAll(List.of(fuelling1, fuelling2, fuelling3, fuelling4));

            System.out.println("✅ Тестовые данные успешно загружены в БД!");
            System.out.println("🚗 Автомобилей: " + carRepository.count());
            System.out.println("📋 Заявок: " + statementRepository.count());
            System.out.println("🛣️ Поездок: " + journeyRepository.count());
            System.out.println("⛽ Заправок: " + fuellingRepository.count());

            // Дополнительная проверка связей
            System.out.println("\n🔗 Проверка связей:");
            statementRepository.findAll().forEach(statement -> {
                System.out.println("Заявка " + statement.getId() + " -> Поездка " +
                        (statement.getJourney() != null ? statement.getJourney().getId() : "null"));
            });
        };
    }
}

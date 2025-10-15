package com.petproject.workflow;

import com.petproject.workflow.store.entities.*;
import com.petproject.workflow.store.repositories.*;
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
            TrailerRepository trailerRepository,
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
            trailerRepository.deleteAll();

            // Фиксированные ID
            UUID logistId = UUID.fromString("96690d40-dfb1-473c-a1ef-e6abb05061ca");
            UUID operatorId = UUID.fromString("8d41cbf0-f0e5-4b62-b8b5-419381457931");
            UUID driverId = UUID.fromString("1a6fce5a-cd67-11eb-b8bc-0242ac130003");

            // Создание тестовых автомобилей для перевозки нефтепродуктов
            Car car1 = new Car(
                    UUID.randomUUID(),
                    "Volvo", "FH16", "A123BC", "YV2R2DBS5CA123456", 2022, "Orange", 85000.5, CarStatus.ACTIVE
            );

            Car car2 = new Car(
                    UUID.randomUUID(),
                    "MAN", "TGX", "B456DE", "MANTGXS00CL123456", 2021, "White", 125000.0, CarStatus.MAINTENANCE
            );

            Car car3 = new Car(
                    UUID.randomUUID(),
                    "Scania", "R450", "C789FG", "SCCZHZBT0F0S12345", 2023, "Blue", 35000.0, CarStatus.ACTIVE
            );

            Car car4 = new Car(
                    UUID.randomUUID(),
                    "Mercedes-Benz", "Actros", "D012GH", "W1M953ZZ0KP123789", 2022, "Yellow", 95000.0, CarStatus.INACTIVE
            );

            carRepository.saveAll(List.of(car1, car2, car3, car4));

            // Создание тестовых цистерн и прицепов для нефтепродуктов и химотходов
            Trailer trailer1 = new Trailer(
                    UUID.randomUUID(),
                    "Schmitz", "T123TR", 38000.0, "Stainless Steel"
            );

            Trailer trailer2 = new Trailer(
                    UUID.randomUUID(),
                    "Krone", "T456TR", 42000.0, "Carbon Steel"
            );

            Trailer trailer3 = new Trailer(
                    UUID.randomUUID(),
                    "Kögel", "T789TR", 35000.0, "Stainless Steel"
            );

            Trailer trailer4 = new Trailer(
                    UUID.randomUUID(),
                    "Ravensberger", "T012TR", 28000.0, "HDPE"
            );

            trailerRepository.saveAll(List.of(trailer1, trailer2, trailer3, trailer4));

            // Создание тестовых заявок (Statements) для нефтеперевозок
            LocalDateTime now = LocalDateTime.now();

            Statement statement1 = new Statement(
                    UUID.randomUUID(),
                    logistId,
                    "Перевозка дизельного топлива - 38 тонн. ADR: 3. Требуется цистерна из нержавеющей стали.",
                    "+79161234567",
                    "НПЗ 'Газпромнефть', Московская обл., г. Одинцово",
                    now.minusDays(2),
                    now.minusDays(1),
                    null
            );

            Statement statement2 = new Statement(
                    UUID.randomUUID(),
                    logistId,
                    "Транспортировка бензина АИ-95 - 42 тонны. ADR: 3. Класс опасности: 1.",
                    "+79167654321",
                    "АЗС 'Лукойл', Москва, ш. Энтузиастов, 45",
                    now.minusDays(1),
                    now.minusHours(12),
                    null
            );

            Statement statement3 = new Statement(
                    UUID.randomUUID(),
                    logistId,
                    "Вывоз химических отходов - 28 тонн. ADR: 8. Коррозионные вещества. Требуется HDPE цистерна.",
                    "+79169998877",
                    "Химкомбинат 'Азот', г. Дзержинск, Нижегородская обл.",
                    now.minusHours(6),
                    now.minusHours(2),
                    null
            );

            Statement statement4 = new Statement(
                    UUID.randomUUID(),
                    logistId,
                    "Перевозка мазута - 35 тонн. ADR: 3. Требуется подогрев цистерны.",
                    "+79165554433",
                    "ТЭЦ-21, Москва, ул. Вавилова, 13",
                    now.minusHours(3),
                    now.minusHours(1),
                    null
            );

            statementRepository.saveAll(List.of(statement1, statement2, statement3, statement4));

            // Создание тестовых выездов (Journeys)
            Journey journey1 = new Journey(
                    UUID.randomUUID(),
                    car1,
                    trailer1,
                    statement1,
                    driverId,
                    JourneyStatus.FINISHED,
                    84500.0,
                    85000.5,
                    now.minusDays(2),
                    now.minusDays(2).plusHours(1),
                    now.minusDays(2).plusHours(2),
                    now.minusDays(1),
                    null
            );

            Journey journey2 = new Journey(
                    UUID.randomUUID(),
                    car3,
                    trailer2,
                    statement2,
                    driverId,
                    JourneyStatus.STARTED,
                    34800.0,
                    0.0,
                    now.minusDays(1),
                    now.minusDays(1).plusHours(1),
                    now.minusHours(4),
                    null,
                    null
            );

            Journey journey3 = new Journey(
                    UUID.randomUUID(),
                    car2,
                    trailer4,
                    statement3,
                    driverId,
                    JourneyStatus.CONFIRMED,
                    0.0,
                    0.0,
                    now.minusHours(6),
                    now.minusHours(3),
                    null,
                    null,
                    null
            );

            Journey journey4 = new Journey(
                    UUID.randomUUID(),
                    car4,
                    trailer3,
                    statement4,
                    driverId,
                    JourneyStatus.NEW,
                    0.0,
                    0.0,
                    now.minusHours(3),
                    null,
                    null,
                    null,
                    null
            );

            journeyRepository.saveAll(List.of(journey1, journey2, journey3, journey4));

            // Обновление связи Statement -> Journey
            statement1.setJourney(journey1);
            statement2.setJourney(journey2);
            statement3.setJourney(journey3);
            statement4.setJourney(journey4);
            statementRepository.saveAll(List.of(statement1, statement2, statement3, statement4));

            // Создание тестовых заправок (Fuellings)
            Fuelling fuelling1 = new Fuelling(
                    UUID.randomUUID(),
                    driverId,
                    operatorId,
                    car1,
                    450.5,
                    now.minusDays(1).plusHours(2)
            );

            Fuelling fuelling2 = new Fuelling(
                    UUID.randomUUID(),
                    driverId,
                    operatorId,
                    car3,
                    380.0,
                    now.minusHours(6)
            );

            Fuelling fuelling3 = new Fuelling(
                    UUID.randomUUID(),
                    driverId,
                    operatorId,
                    car1,
                    420.0,
                    now.minusDays(3)
            );

            Fuelling fuelling4 = new Fuelling(
                    UUID.randomUUID(),
                    driverId,
                    operatorId,
                    car2,
                    520.0,
                    now.minusDays(4)
            );

            fuellingRepository.saveAll(List.of(fuelling1, fuelling2, fuelling3, fuelling4));

            System.out.println("Тестовые данные для нефтеперевозок успешно загружены!");
            System.out.println("Создано: 4 автомобиля, 4 цистерны, 4 заявки, 4 выезда, 4 заправки");
            System.out.println("Типы перевозок: дизельное топливо, бензин, химические отходы, мазут");
            System.out.println("Использованы фиксированные ID сотрудников");
        };
    }
}

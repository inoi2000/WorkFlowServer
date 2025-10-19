package com.petproject.workflow;

import com.petproject.workflow.store.Announcement;
import com.petproject.workflow.store.AnnouncementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class AnnouncementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnnouncementServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            AnnouncementRepository announcementRepository
    ) {
        return args -> {
            announcementRepository.deleteAll();
            announcementRepository.save(new Announcement(
                    UUID.randomUUID(),
                    "Проводится планновая вакцинация от COVID-19",
                    LocalDateTime.now(),
                    "Просьба всем сотрудникам 25.12.2026 прибыть в пунк вакцинациии и получить привиыку от вирусв COVID-19. Данное мероприятие является обязательным к исполнению. При неявки необходимо предоставить объяснительную",
                    null
            ));

            announcementRepository.save(new Announcement(
                    UUID.randomUUID(),
                    "Плановое совещание в 10:30",
                    LocalDateTime.now(),
                    "Просьба всем сотрудникам 25.12.2026 прибыть в пунк вакцинациии и получить привиыку от вирусв COVID-19. Данное мероприятие является обязательным к исполнению. При неявки необходимо предоставить объяснительную",
                    null
            ));
        };
    }
}

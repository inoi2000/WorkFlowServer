package com.petproject.workflow.api;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class JourneyService {


    // Валидация времени от клиента
    public void validateClientTime(LocalDateTime clientTime) throws InvalidTimeException {
        LocalDateTime serverTime = LocalDateTime.now();
        Duration difference = Duration.between(clientTime, serverTime);

        if (Math.abs(difference.toHours()) > 24) {
            throw new InvalidTimeException("Client time differs too much from server time");
        }
    }
}

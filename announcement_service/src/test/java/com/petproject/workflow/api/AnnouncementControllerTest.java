package com.petproject.workflow.api;

import com.petproject.workflow.store.Announcement;
import com.petproject.workflow.store.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementControllerTest {

    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private AnnouncementController announcementController;

    private Announcement announcement1;
    private Announcement announcement2;
    private UUID testId;
    private UUID nonExistentId;

//    @BeforeEach
//    void setUp() {
//        testId = UUID.randomUUID();
//        nonExistentId = UUID.randomUUID();
//
//        announcement1 = new Announcement(
//                testId,
//                "Test Announcement 1",
//                LocalDate.of(2024, 1, 15),
//                "Content of announcement 1",
//                "https://example.com/image1.jpg"
//        );
//
//        announcement2 = new Announcement(
//                UUID.randomUUID(),
//                "Test Announcement 2",
//                LocalDate.of(2024, 1, 16),
//                "Content of announcement 2",
//                "https://example.com/image2.jpg"
//        );
//    }

    @Test
    void findAll_ShouldReturnAllAnnouncements() {
        // Arrange
        List<Announcement> announcements = Arrays.asList(announcement1, announcement2);
        when(announcementRepository.findAll()).thenReturn(announcements);

        // Act
        Iterable<Announcement> result = announcementController.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, ((List<Announcement>) result).size());
        assertTrue(((List<Announcement>) result).contains(announcement1));
        assertTrue(((List<Announcement>) result).contains(announcement2));

        verify(announcementRepository, times(1)).findAll();
    }

    @Test
    void findAll_WhenNoAnnouncements_ShouldReturnEmptyList() {
        // Arrange
        when(announcementRepository.findAll()).thenReturn(List.of());

        // Act
        Iterable<Announcement> result = announcementController.findAll();

        // Assert
        assertNotNull(result);
        assertFalse(((List<Announcement>) result).iterator().hasNext());
        verify(announcementRepository, times(1)).findAll();
    }

    @Test
    void findById_WithExistingId_ShouldReturnAnnouncement() {
        // Arrange
        when(announcementRepository.findById(testId)).thenReturn(Optional.of(announcement1));

        // Act
        ResponseEntity<Announcement> response = announcementController.findById(testId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(announcement1, response.getBody());

        verify(announcementRepository, times(1)).findById(testId);
    }

    @Test
    void findById_WithNonExistentId_ShouldReturnNotFound() {
        // Arrange
        when(announcementRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Announcement> response = announcementController.findById(nonExistentId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());

        verify(announcementRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void findById_WithNullId_ShouldReturnNotFound() {
        // Act
        ResponseEntity<Announcement> response = announcementController.findById(null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());

//        verify(announcementRepository, never()).findById(any());
    }

//    @Test
//    void create_ShouldSaveAndReturnAnnouncement() {
//        // Arrange
//        Announcement newAnnouncement = new Announcement(
//                null, // ID должен быть null перед созданием
//                "New Announcement",
//                LocalDate.now(),
//                "New content",
//                "https://example.com/new-image.jpg"
//        );
//
//        Announcement savedAnnouncement = new Announcement(
//                testId, // ID будет сгенерирован
//                newAnnouncement.getTitle(),
//                newAnnouncement.getPostData(),
//                newAnnouncement.getContent(),
//                newAnnouncement.getImgUrl()
//        );
//
//        when(announcementRepository.save(any(Announcement.class))).thenReturn(savedAnnouncement);
//
//        // Act
//        Announcement result = announcementController.create(newAnnouncement);
//
//        // Assert
//        assertNotNull(result);
//        assertNotNull(result.getId()); // Проверяем, что ID был установлен
//        assertEquals(savedAnnouncement.getId(), result.getId());
//        assertEquals(newAnnouncement.getTitle(), result.getTitle());
//        assertEquals(newAnnouncement.getContent(), result.getContent());
//
//        verify(announcementRepository, times(1)).save(any(Announcement.class));
//    }

//    @Test
//    void create_ShouldGenerateNewId() {
//        // Arrange
//        Announcement announcementWithId = new Announcement(
//                UUID.randomUUID(), // Уже есть ID, но должен быть перезаписан
//                "Test Title",
//                LocalDate.now(),
//                "Test Content",
//                "https://example.com/test.jpg"
//        );
//
//        UUID newId = UUID.randomUUID();
//        Announcement savedAnnouncement = new Announcement(
//                newId,
//                announcementWithId.getTitle(),
//                announcementWithId.getPostData(),
//                announcementWithId.getContent(),
//                announcementWithId.getImgUrl()
//        );
//
//        when(announcementRepository.save(any(Announcement.class))).thenReturn(savedAnnouncement);
//
//        // Act
//        Announcement result = announcementController.create(announcementWithId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(newId, result.getId()); // Проверяем, что использован новый ID
//        verify(announcementRepository, times(1)).save(any(Announcement.class));
//    }

    @Test
    void create_WithNullAnnouncement_ShouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> announcementController.create(null));
        verify(announcementRepository, never()).save(any());
    }
}
package com.petproject.workflow.api;

import com.jayway.jsonpath.JsonPath;
import com.petproject.workflow.store.Announcement;
import com.petproject.workflow.store.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnnouncementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @BeforeEach
    void setUp() {
        announcementRepository.deleteAll();
    }

//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void findAll_ShouldReturnEmptyList_WhenNoAnnouncements() throws Exception {
//        mockMvc.perform(get("/api/announcements"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(0)));
//    }
//
//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void findAll_ShouldReturnAllAnnouncements() throws Exception {
//        // Arrange
//        Announcement announcement1 = createTestAnnouncement("Test Title 1", "Content of announcement 1");
//        Announcement announcement2 = createTestAnnouncement("Test Title 2", "Content of announcement 2");
//
//        announcementRepository.save(announcement1);
//        announcementRepository.save(announcement2);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/announcements"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].title", is("Test Title 1")))
//                .andExpect(jsonPath("$[1].title", is("Test Title 2")));
//    }
//
//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void findById_ShouldReturnAnnouncement_WhenExists() throws Exception {
//        // Arrange
//        Announcement announcement = createTestAnnouncement("Test Title", "Test Content");
//        Announcement saved = announcementRepository.save(announcement);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/announcements/{id}", saved.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(saved.getId().toString())))
//                .andExpect(jsonPath("$.title", is("Test Title")))
//                .andExpect(jsonPath("$.content", is("Test Content")));
//    }
//
//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void findById_ShouldReturnNotFound_WhenNotExists() throws Exception {
//        UUID nonExistentId = UUID.randomUUID();
//
//        mockMvc.perform(get("/api/announcements/{id}", nonExistentId))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void create_ShouldCreateAnnouncement() throws Exception {
//        String announcementJson = """
//        {
//            "title": "New Announcement",
//            "postData": "2024-01-15",
//            "content": "This is a new announcement",
//            "imgUrl": "https://example.com/image.jpg"
//        }
//        """;
//
//        MvcResult result = mockMvc.perform(post("/api/announcements")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(announcementJson))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.title", is("New Announcement")))
//                .andExpect(jsonPath("$.content", is("This is a new announcement")))
//                .andExpect(jsonPath("$.postData", is(LocalDate.now().toString())))
//                .andReturn();
//
//        // Verify that announcement was actually saved in database
//        String responseContent = result.getResponse().getContentAsString();
//        String id = JsonPath.parse(responseContent).read("$.id");
//        UUID announcementId = UUID.fromString(id);
//
//        assertTrue(announcementRepository.existsById(announcementId));
//    }
//
//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void create_ShouldGenerateNewId_EvenIfProvided() throws Exception {
//        String announcementJson = """
//        {
//            "id": "550e8400-e29b-41d4-a716-446655440000",
//            "title": "Announcement with ID",
//            "postData": "2024-01-15",
//            "content": "Content of announcement",
//            "imgUrl": "https://example.com/image.jpg"
//        }
//        """;
//
//        mockMvc.perform(post("/api/announcements")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(announcementJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.id").value(not("550e8400-e29b-41d4-a716-446655440000")))
//                .andExpect(jsonPath("$.title", is("Announcement with ID")));
//    }
//
//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void create_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
//        String invalidAnnouncementJson = """
//        {
//            "title": "",
//            "postData": "2024-01-15",
//            "content": "",
//            "imgUrl": "not-a-url"
//        }
//        """;
//
//        mockMvc.perform(post("/api/announcements")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidAnnouncementJson))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void create_ShouldReturnBadRequest_WhenMissingRequiredFields() throws Exception {
//        String incompleteAnnouncementJson = """
//        {
//            "postData": "2024-01-15",
//            "imgUrl": "https://example.com/image.jpg"
//        }
//        """;
//
//        mockMvc.perform(post("/api/announcements")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(incompleteAnnouncementJson))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @WithMockUser(username = "test-user", roles = {"HR"})
//    void endpoints_ShouldProduceJson() throws Exception {
//        Announcement announcement = createTestAnnouncement("Test Title", "Content of announcement");
//        announcementRepository.save(announcement);
//
//        mockMvc.perform(get("/api/announcements"))
//                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE));
//
//        mockMvc.perform(get("/api/announcements/" + announcement.getId()))
//                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE));
//    }
//
//    private Announcement createTestAnnouncement(String title, String content) {
//        return new Announcement(
//                UUID.randomUUID(),
//                title,
//                LocalDate.now(),
//                content,
//                "https://example.com/image.jpg"
//        );
//    }
}

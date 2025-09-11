package com.petproject.workflow.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.workflow.api.dtos.CommentDto;
import com.petproject.workflow.store.*;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testGetAllCommentsByTask_WhenCommentsExist_ShouldReturnComments() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();

        Comment comment1 = new Comment(UUID.randomUUID(), "First comment text", LocalDate.now(),
                CommentStatus.INFORMATION, taskId);
        Comment comment2 = new Comment(UUID.randomUUID(), "Second comment text", LocalDate.now(),
                CommentStatus.FOR_APPROVAL, taskId);
        Comment comment3 = new Comment(UUID.randomUUID(), "Third comment text", LocalDate.now(),
                CommentStatus.FOR_NOT_APPROVAL, taskId);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        // Act & Assert
        mockMvc.perform(get("/api/comments")
                        .param("task_id", taskId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].text", is("First comment text")))
                .andExpect(jsonPath("$[0].commentStatus", is(CommentStatus.INFORMATION.toString())))
                .andExpect(jsonPath("$[1].text", is("Second comment text")))
                .andExpect(jsonPath("$[1].commentStatus", is(CommentStatus.FOR_APPROVAL.toString())))
                .andExpect(jsonPath("$[2].text", is("Third comment text")))
                .andExpect(jsonPath("$[2].commentStatus", is(CommentStatus.FOR_NOT_APPROVAL.toString())))
                .andExpect(jsonPath("$[0].taskId", is(taskId.toString())));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testGetAllCommentsByTask_WhenNoCommentsExist_ShouldReturnEmptyList() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();

        // Act & Assert
        mockMvc.perform(get("/api/comments")
                        .param("task_id", taskId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testGetAllCommentsByTask_WhenInvalidTaskId_ShouldReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/comments"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testCreateComment_WithInformationStatus_ShouldCreateComment() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();
        CommentDto commentDto = new CommentDto(
                null,
                "Valid comment text that is long enough",
                null,
                CommentStatus.INFORMATION,
                taskId
        );

        // Act & Assert
        testCreateCommentWithStatus(commentDto, CommentStatus.INFORMATION);
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testCreateComment_WithForApprovalStatus_ShouldCreateComment() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();
        CommentDto commentDto = new CommentDto(
                null,
                "Valid comment text that is long enough for approval",
                null,
                CommentStatus.FOR_APPROVAL,
                taskId
        );

        // Act & Assert
        testCreateCommentWithStatus(commentDto, CommentStatus.FOR_APPROVAL);
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testCreateComment_WithForNotApprovalStatus_ShouldCreateComment() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();
        CommentDto commentDto = new CommentDto(
                null,
                "Valid comment text that is long enough for not approval",
                null,
                CommentStatus.FOR_NOT_APPROVAL,
                taskId
        );

        // Act & Assert
        testCreateCommentWithStatus(commentDto, CommentStatus.FOR_NOT_APPROVAL);
    }

    private void testCreateCommentWithStatus(CommentDto commentDto, CommentStatus expectedStatus) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is(commentDto.getText())))
                .andExpect(jsonPath("$.taskId", is(commentDto.getTaskId().toString())))
                .andExpect(jsonPath("$.commentStatus", is(expectedStatus.toString())))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.creation").exists())
                .andReturn();

        // Verify that comment was saved in database
        String responseContent = result.getResponse().getContentAsString();
        CommentDto createdComment = objectMapper.readValue(responseContent, CommentDto.class);

        assertThat(commentRepository.findById(createdComment.getId())).isPresent();
        Comment savedComment = commentRepository.findById(createdComment.getId()).get();
        assertThat(savedComment.getCommentStatus()).isEqualTo(expectedStatus);
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testCreateComment_WithNullStatus_ShouldReturnBadRequest() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();
        CommentDto commentDto = new CommentDto(
                null,
                "Valid comment text that is long enough",
                null,
                null, // null status
                taskId
        );

        // Act & Assert
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testCreateComment_WithTextTooShort_ShouldReturnBadRequest() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();
        CommentDto commentDto = new CommentDto(
                null,
                "", // Меньше 5 символов
                null,
                CommentStatus.INFORMATION,
                taskId
        );

        // Act & Assert
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testCreateComment_WithNullText_ShouldReturnBadRequest() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();
        CommentDto commentDto = new CommentDto(
                null,
                null, // null текст
                null,
                CommentStatus.INFORMATION,
                taskId
        );

        // Act & Assert
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testCreateComment_WithNullTaskId_ShouldReturnBadRequest() throws Exception {
        // Arrange
        CommentDto commentDto = new CommentDto(
                null,
                "Valid comment text that is long enough",
                null,
                CommentStatus.INFORMATION,
                null // null taskId
        );

        // Act & Assert
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    public void testCreateComment_VerifyAutoGeneratedFields() throws Exception {
        // Arrange
        UUID taskId = addTaskToDatabase();
        CommentDto commentDto = new CommentDto(
                null,
                "Test comment for verifying auto-generated fields",
                null,
                CommentStatus.FOR_APPROVAL,
                taskId
        );

        // Act
        MvcResult result = mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert
        String responseContent = result.getResponse().getContentAsString();
        CommentDto createdComment = objectMapper.readValue(responseContent, CommentDto.class);

        // Проверяем, что ID был сгенерирован
        assertThat(createdComment.getId()).isNotNull();

        // Проверяем, что дата создания была установлена
        assertThat(createdComment.getCreation()).isEqualTo(LocalDate.now());

        // Проверяем, что данные сохранились в БД
        Comment savedComment = commentRepository.findById(createdComment.getId()).orElseThrow();
        assertThat(savedComment.getId()).isEqualTo(createdComment.getId());
        assertThat(savedComment.getCreation()).isEqualTo(LocalDate.now());
        assertThat(savedComment.getText()).isEqualTo(commentDto.getText());
        assertThat(savedComment.getCommentStatus()).isEqualTo(commentDto.getCommentStatus());
        assertThat(savedComment.getTaskId()).isEqualTo(commentDto.getTaskId());
    }

    private UUID addTaskToDatabase() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task(
                taskId,
                "Task description",
                TaskStatus.NEW,
                TaskPriority.COMMON,
                LocalDate.now(),
                LocalDate.now(),
                "Task destination",
                false,
                UUID.randomUUID(),
                UUID.randomUUID(),
                null
        );
        taskRepository.save(task);
        return taskId;
    }
}
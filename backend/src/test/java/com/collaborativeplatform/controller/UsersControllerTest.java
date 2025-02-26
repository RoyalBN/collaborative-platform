package com.collaborativeplatform.controller;

import com.collaborativeplatform.exceptions.UserNotFoundException;
import com.collaborativeplatform.model.User;
import com.collaborativeplatform.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private UserService userService;

  @InjectMocks
  private UsersController usersController;

  private User user1;
  private User user2;
  private List<User> usersList;


  @BeforeEach
  void setUp() {
    user1 = new User(1L, "John Doe", "pass", "john.doe@example.com", "admin");
    user2 = new User(2L, "Bryan Deker", "word", "bryan.deker@example.com", "user");
    usersList = Arrays.asList(user1, user2);
  }

  // GET all 200
  @Test
  void should_return_all_users_with_status_200_ok() throws Exception {
    when(userService.getAllUsers()).thenReturn(usersList);

    mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$[0].username").value("John Doe"))
      .andExpect(jsonPath("$[1].username").value("Bryan Deker"));

    verify(userService, times(1)).getAllUsers();
  }

  @Test
  void should_return_user_by_id_with_status_200_ok() throws Exception {
    // Arrange: on simule un user existant
    when(userService.getUserById(1L)).thenReturn(Optional.of(user1));

    // Act & Assert: On vérifie que le user est bien retourné
    mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(user1.getId()))
      .andExpect(jsonPath("$.username").value(user1.getUsername()))
      .andExpect(jsonPath("$.email").value(user1.getEmail()));

    verify(userService, times(1)).getUserById(1L);
  }

  // GET by ID 404
  @Test
  void should_return_status_404_if_user_is_not_found() throws Exception {
    Long userId = 342L;
    when(userService.getUserById(userId)).thenReturn(Optional.empty());

    mockMvc.perform(get("/users/" + userId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());

    verify(userService, times(1)).getUserById(userId);
  }

  // Update 200
  @Test
  void should_update_user_by_id_and_return_status_200_ok() throws Exception {
    // Arrange
    user1.setUsername("Johnny Doees");

    // Act & Assert
    when(userService.updateUser(any(User.class))).thenReturn(user1);

    mockMvc.perform(post("/users/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user1))
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(user1.getId()))
      .andExpect(jsonPath("$.username").value("Johnny Doees"))
      .andExpect(jsonPath("$.email").value(user1.getEmail()));

    verify(userService, times(1)).updateUser(user1);
  }

  // Delete 200 OK
  @Test
  void should_delete_user_id_and_return_status_204_no_content() throws Exception {
    Long userIdToDelete = 2L;
    doNothing().when(userService).deleteUser(userIdToDelete);

    mockMvc.perform(delete("/users/delete/" + userIdToDelete)
      .contentType(MediaType.APPLICATION_JSON)
    )
      .andExpect(status().isNoContent());

    verify(userService, times(1)).deleteUser(userIdToDelete);
  }

  // Delete 404 NOK
  @Test
  void should_return_status_404_when_deleting_non_existing_user() throws Exception {
    Long nonExistingUserId = 35L;
    String userNotFoundExeptionMessage = "User with ID " + nonExistingUserId + " not found.";

    doThrow(new UserNotFoundException(userNotFoundExeptionMessage))
      .when(userService).deleteUser(nonExistingUserId);

    mockMvc.perform(delete("/users/delete/" + nonExistingUserId)
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());

    verify(userService, times(1)).deleteUser(nonExistingUserId);
  }

}

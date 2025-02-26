package com.collaborativeplatform.service;

import com.collaborativeplatform.model.User;
import com.collaborativeplatform.repository.UserRepository;

import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  private UserService userService;

  private User user1;
  private User user2;
  private List<User> usersList;

  @BeforeEach
  void setUp() {
    userService = new UserService(userRepository);
    user1 = new User(1L, "John Doe", "pass", "john.doe@example.com", "admin");
    user2 = new User(2L, "Bryan Deker", "word", "bryan.deker@example.com", "user");
    usersList = Arrays.asList(user1, user2);
  }

  @Test
  void should_return_all_users() {
    when(userRepository.findAll()).thenReturn(usersList);

    List<User> allUsers = userService.getAllUsers();

    assertNotNull(allUsers);
    assertEquals(2, allUsers.size());
    assertTrue(allUsers.contains(user1));
    assertTrue(allUsers.contains(user2));
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void should_return_user_by_id() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

    Optional<User> foundUser = userService.getUserById(1L);

    assertNotNull(foundUser);
    assertEquals(user1.getId(), foundUser.get().getId());
    verify(userRepository, times(1)).findById(user1.getId());
  }

  @Test
  void should_return_empty_if_user_not_found() {
    when(userRepository.findById(99L)).thenReturn(Optional.empty());

    Optional<User> foundUser = userService.getUserById(99L);

    assertTrue(foundUser.isEmpty());
    verify(userRepository, times(1)).findById(99L);
  }


  @Test
  void should_create_user_and_return_saved_user() {
    // Arrange (Préparation)
    when(userRepository.save(any(User.class))).thenReturn(user1);

    // Act (Exécution)
    User savedUser = userService.addUser(user1);

    // Assert (Vérifications)
    assertNotNull(savedUser);
    assertEquals(user1.getId(), savedUser.getId());
    verify(userRepository, times(1)).save(user1);
  }

  @Test
  void should_update_user_and_return_updated_user() {
    // On modifie le mot de passe
    user1.setPassword("passmdp");

    // Une action d'update va être réalisée
    when(userRepository.save(any(User.class))).thenReturn(user1);

    User userUpdated = userService.updateUser(user1);

    assertNotNull(userUpdated);
    assertEquals(user1.getId(), userUpdated.getId());
    assertEquals("passmdp", userUpdated.getPassword());
  }

  @Test
  void should_delete_user() {
    Long userId = 2L;

    userService.deleteUser(userId);

    verify(userRepository, times(1)).deleteById(userId);
  }

}

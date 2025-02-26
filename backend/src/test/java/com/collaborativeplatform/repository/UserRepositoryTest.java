package com.collaborativeplatform.repository;

import com.collaborativeplatform.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EntityManager entityManager;

  private User testUser;

  @BeforeEach
  void setUp() {
    // Avant chaque test, on créé et save un nouvel User
    testUser = User.builder()
      .username("John Doenas")
      .password("pass")
      .email("john.doenas@example.com")
      .role("admin")
      .build();
  }

  // Delete by ID --> 200 OK
  @Test
  @Transactional
  void should_delete_user_by_id_and_return_status_200_ok() {
    // Arrange
    User savedUser = userRepository.save(testUser);
    entityManager.flush();

    // Act
    userRepository.deleteById(savedUser.getId());
    entityManager.flush();

    // Verify
    Optional<User> notFoundUser = userRepository.findById(savedUser.getId());
    assertThat(notFoundUser).isEmpty();
  }

  // Delete by ID --> 404 Not Found
  @Test
  void should_not_found_user_and_return_status_404_not_found() {
    // Arrange
    Long nonExistantUserId = 34L;

    // Act & Assert
    assertThatNoException().isThrownBy(() -> {
      userRepository.deleteById(nonExistantUserId);
    });
  }

  @Test
  void should_not_find_user_after_deleting_non_existent_user() {
    // Arrange
    Long nonExistantUserId = 34L;

    // Vérifier que le user n'existe pas avant la suppression
    Optional<User> userBeforeDeletion = userRepository.findById(nonExistantUserId);
    assertThat(userBeforeDeletion).isEmpty();

    // Act
    userRepository.deleteById(nonExistantUserId);

    // Verify
    Optional<User> userAfterDeletion = userRepository.findById(nonExistantUserId);
    assertThat(userAfterDeletion).isEmpty();

  }

}

package com.collaborativeplatform.service;

import com.collaborativeplatform.exceptions.UserNotFoundException;
import com.collaborativeplatform.model.User;
import com.collaborativeplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long userId) {
    return userRepository.findById(userId);
  }

  public User addUser(User user) {
    return userRepository.save(user);
  }

  public User updateUser(User user) {
    return userRepository.save(user);
  }

  public void deleteUser(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException("User with ID " + userId + " not found.");
    }
    userRepository.deleteById(userId);
  }

}

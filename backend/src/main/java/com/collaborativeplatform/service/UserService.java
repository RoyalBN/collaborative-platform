package com.collaborativeplatform.service;

import com.collaborativeplatform.model.User;
import com.collaborativeplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("User not found."));
  }

  public User addUser(User user) {
    return userRepository.save(user);
  }

  public User updateUser(User user) {
    return userRepository.save(user);
  }

  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

}

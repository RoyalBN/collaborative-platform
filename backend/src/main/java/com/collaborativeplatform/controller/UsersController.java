package com.collaborativeplatform.controller;

import com.collaborativeplatform.model.User;
import com.collaborativeplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

//  @GetMapping("/:id")

//  PostMapping


}

package com.collaborativeplatform.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "users")
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  private String email;
  private String role;

  public User(Long id, String username, String password, String email, String role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
  }
}

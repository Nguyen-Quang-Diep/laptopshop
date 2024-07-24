package com.kh.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.laptopshop.domain.User;
import com.kh.laptopshop.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  public User handleSaveUser(User user) {
    User userDetail = this.userRepository.save(user);
    return userDetail;
  }

  public User getUserById(Long id) {
    return this.userRepository.findOneById(id);
  }
}

package com.kh.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.laptopshop.service.UserService;
import com.kh.laptopshop.domain.User;
import com.kh.laptopshop.repository.UserRepository;

@Controller
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  public UserController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @RequestMapping("/")
  public String home(Model model) {
    return "hello";
  }

  @RequestMapping("/admin/user")
  public String getUserPage(Model model) {
    List<User> users = this.userService.getAllUsers();
    model.addAttribute("users", users);
    return "admin/user/table-user";
  }

  @RequestMapping("/admin/user/{id}")
  public String getUserDetailPage(Model model, @PathVariable Long id) {
    User user = this.userService.getUserById(id);
    model.addAttribute("user", user);
    model.addAttribute("id", id);
    return "admin/user/user-detail";
  }

  @RequestMapping("/admin/user/create")
  public String getCreateUserPage(Model model) {
    model.addAttribute("newUser", new User());
    return "admin/user/create";
  }

  @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
  public String createUserPage(Model model, @ModelAttribute("newUser") User user) {
    userService.handleSaveUser(user);
    return "redirect:/admin/user";
  }

  @RequestMapping(value = "/admin/user/update/{id}", method = RequestMethod.GET)
  public String getUpdateUserPage(Model model, @PathVariable Long id) {
    User currentUser = this.userService.getUserById(id);
    model.addAttribute("newUser", currentUser);
    return "admin/user/update";
  }

  @PostMapping("/admin/user/update") // get
  public String postUpdateUser(Model model, @ModelAttribute("newUser") User user) {
    User currentUser = this.userService.getUserById(user.getId());
    if (currentUser != null) {
      currentUser.setFullName(user.getFullName());
      currentUser.setPhone(user.getPhone());
      currentUser.setAddress(user.getAddress());

      this.userService.handleSaveUser(currentUser);
    }

    return "redirect:/admin/user";
  }

}

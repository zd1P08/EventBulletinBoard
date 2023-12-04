package com.example.app.controller;

import com.example.app.domain.User;
import com.example.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserService service;

  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("user", new User());
    return "auth/login";
  }

  @GetMapping("/register")
  public String showRegistrationForm(Model model) throws Exception {
    model.addAttribute("title", "アカウント新規登録");
    model.addAttribute("user", new User());
    model.addAttribute("levels", service.getLevelList());
    return "auth/register";
  }

  @PostMapping("/register")
  public String register(@Valid User user, Errors errors, RedirectAttributes rd, Model model)
      throws Exception {
    User existingUser = service.findUserByLoginId(user.getLoginId());
    if (existingUser != null && existingUser.getLoginId() != null && !existingUser.getLoginId()
        .isEmpty()) {
      errors.rejectValue("loginId", null,
          "There is already an account registered with the same loginId");
    }

    if (errors.hasErrors()) {
      model.addAttribute("title", "アカウント新規登録");
      model.addAttribute("user", user);
      model.addAttribute("levels", service.getLevelList());
      return "/auth/register";
    }

    user.setAdminDiv(0);
    service.addUser(user);

    return "redirect:/auth/register?success";
  }
}

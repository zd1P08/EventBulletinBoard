package com.example.app.controller;

import com.example.app.domain.User;
import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping("/edit")
  public String editGet(Model model, @AuthenticationPrincipal User user) throws Exception {
    int userId = user.getUserId();
    model.addAttribute("title", "プロフィールの編集");
    model.addAttribute("levels", service.getLevelList());
    return "events/add";
  }

  @PostMapping("/edit")
  public String editPost(Errors errors, RedirectAttributes rd, Model model,
      @AuthenticationPrincipal User user)
      throws Exception {
    int userId = user.getUserId();
    if (errors.hasErrors()) {
      model.addAttribute("title", "アカウント情報の変更");
      model.addAttribute("levels", service.getLevelList());
      return "/users/add";
    }
    service.editUser(user);
    rd.addFlashAttribute("statusMessage", "アカウント情報を更新しました。");
    return "redirect:/users/myPage";
  }

  @GetMapping("/delete")
  public String delete(RedirectAttributes rd, @AuthenticationPrincipal User user) throws Exception {
    int userId = user.getUserId();
    service.deleteUser(userId);
    rd.addFlashAttribute("statusMessage", "アカウントを削除しました。");
    return "redirect:/";
  }

}

package com.example.app.controller;

import com.example.app.domain.User;
import com.example.app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/myPage")
public class MyPageController {

  // 1ページ当たりの表示人数
  private static final int NUM_PER_PAGE = 8;

  @Autowired
  private EventService service;

  @GetMapping
  public String index(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model,
      @AuthenticationPrincipal User user)
      throws Exception {
    int userId = user.getUserId(); // ユーザーIDを取得
    int offset = (page - 1) * NUM_PER_PAGE; // ページングのオフセットを計算
    // ユーザーに関連するイベントを取得
    model.addAttribute("events", service.getEventsByUserId(userId, offset, NUM_PER_PAGE));
    model.addAttribute("page", page);
    model.addAttribute("totalPages", service.getTotalPagesByUserId(userId, NUM_PER_PAGE));
    return "myPage/index";
  }

}

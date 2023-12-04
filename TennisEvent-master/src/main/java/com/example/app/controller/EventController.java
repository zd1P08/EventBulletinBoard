package com.example.app.controller;

import com.example.app.domain.Event;
import com.example.app.domain.User;
import com.example.app.service.EventService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events")
public class EventController {

  // 1ページ当たりの表示人数
  private static final int NUM_PER_PAGE = 8;

  @Autowired
  private EventService service;

  @GetMapping
  public String list(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model,
      @AuthenticationPrincipal User user)
      throws Exception {
    int userId = user.getUserId();
    int offset = (page - 1) * NUM_PER_PAGE;
    // ユーザーに関連するイベントを取得
    model.addAttribute("events", service.getEventListByPage(userId, offset, NUM_PER_PAGE));
    model.addAttribute("page", page);
    model.addAttribute("totalPages", service.getTotalPagesByUserId(userId, NUM_PER_PAGE));
    return "events/index";
  }

  @GetMapping("/listJoinedEvent")
  public String JoinedList(@RequestParam(name = "page", defaultValue = "1") Integer page,
      Model model, @AuthenticationPrincipal User user)
      throws Exception {
    int userId = user.getUserId();
    int offset = (page - 1) * NUM_PER_PAGE;

    List<Event> joinedEvents = service.listJoinedEvents(userId);

    model.addAttribute("join", joinedEvents);
    model.addAttribute("page", page);
    model.addAttribute("totalPages", service.getTotalPagesByUserId(userId, NUM_PER_PAGE));

    return "events/listJoinedEvent";
  }

  @GetMapping("/add")
  public String add(Model model) throws Exception {
    model.addAttribute("title", "イベントの追加");
    model.addAttribute("event", new Event());
    model.addAttribute("locations", service.getLocationList());
    model.addAttribute("levels", service.getLevelList());
    return "events/add";
  }

  @PostMapping("/add")
  public String add(@Valid Event event, Errors errors, RedirectAttributes rd, Model model,
      HttpSession session, @AuthenticationPrincipal User user)
      throws Exception {
    if (errors.hasErrors()) {
      System.out.println(errors);
      model.addAttribute("title", "イベントの追加");
      model.addAttribute("locations", service.getLocationList());
      model.addAttribute("levels", service.getLevelList());
      return "/events/add";
    }

    int userId = user.getUserId();
    // 新規イベントを作成し、ユーザーIDを設定
    event.setUserId(userId);
    service.addEvent(event);

    rd.addFlashAttribute("statusMessage", "イベントを追加しました.");
    return "redirect:/events";
  }

  @GetMapping("/edit/{eventId}")
  public String edit(@PathVariable Integer eventId, Model model) throws Exception {
    model.addAttribute("title", "イベント情報の変更");
    model.addAttribute("event", service.getEventById(eventId));
    model.addAttribute("locations", service.getLocationList());
    model.addAttribute("levels", service.getLevelList());
    return "events/add";
  }

  @PostMapping("/edit/{eventId}")
  public String edit(@PathVariable Integer eventId, @Valid Event event, Errors errors,
      RedirectAttributes rd,
      Model model, @AuthenticationPrincipal User user) throws Exception {
    if (errors.hasErrors()) {
      model.addAttribute("title", "イベント情報の変更");
      model.addAttribute("locations", service.getLocationList());
      model.addAttribute("levels", service.getLevelList());
      return "/events/add";
    }
    int userId = user.getUserId();
    // イベント情報にユーザーIDを設定
    event.setUserId(userId);

    service.editEvent(event);
    rd.addFlashAttribute("statusMessage", "イベント情報を更新しました。");
    return "redirect:/myPage";
  }

  @GetMapping("/delete/{eventId}")
  public String delete(@PathVariable Integer eventId, RedirectAttributes rd) throws Exception {
    service.deleteEvent(eventId);
    rd.addFlashAttribute("statusMessage", "イベント情報を削除しました。");
    return "redirect:/myPage";
  }

  @GetMapping("/join/{eventId}")
  public String joinEventGet(@PathVariable Integer eventId, Model model)
      throws Exception {
    model.addAttribute("title", "イベント参加");
    model.addAttribute("event", service.getEventById(eventId));
    return "events/joinEvent";
  }

  @PostMapping("/join/{eventId}")
  public String joinEventPost(@PathVariable Integer eventId, @AuthenticationPrincipal User user,
      RedirectAttributes rd)
      throws Exception {
    int userId = user.getUserId();

    val event = service.getEventById(eventId);
    if (event == null) {
      rd.addFlashAttribute("statusMessage", "不正な操作です。");
    } else if (user.getLevelId() < event.getLevelId()) {
      rd.addFlashAttribute("statusMessage", "ユーザーのレベルが不足しているため、参加できません。");
    } else if (event.getUserId() == userId) {
      rd.addFlashAttribute("statusMessage", "自分が作成したイベントには参加できません。");
    } else if (service.isUserAlreadyJoined(userId, event.getEventId())) {
      rd.addFlashAttribute("statusMessage", "既に参加済みです。");
    } else if (service.countEventJoinedUser(event.getEventId()) >= event.getCapacity()) {
      rd.addFlashAttribute("statusMessage", "募集人数に達しているため、参加できません。");
    }
    if (!rd.getFlashAttributes().isEmpty()) {
      return "redirect:/events";
    }

    // イベントにユーザーを参加させるロジックを追加
    try {
      service.joinEvent(userId, eventId);
      rd.addFlashAttribute("statusMessage", "イベントに参加しました。");
    } catch (Exception e) {
      rd.addFlashAttribute("errorMessage", "エラーが発生しました。");
    }
    return "redirect:/events";
  }

  @GetMapping("/cancel/{eventId}")
  public String cancel(@PathVariable Integer eventId, @AuthenticationPrincipal User user,
      RedirectAttributes rd)
      throws Exception {
    int userId = user.getUserId();

    service.cancelEvent(eventId, userId);
    rd.addFlashAttribute("statusMessage", "参加をキャンセルしました。");
    return "redirect:/events/listJoinedEvent";
  }

}

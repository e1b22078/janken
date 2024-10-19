package oit.is.z2618.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2618.kaizi.janken.model.Janken;
import oit.is.z2618.kaizi.janken.model.User;
import oit.is.z2618.kaizi.janken.model.UserMapper;
import oit.is.z2618.kaizi.janken.model.Match;
import oit.is.z2618.kaizi.janken.model.MatchMapper;

@Controller
public class JankenController {
  private Janken janken;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  public JankenController(Janken janken) {
    this.janken = janken;
  }

  @GetMapping("/janken")
  public String janken(Model model, Principal prin) {
    if (prin == null) {
      return "redirect:/login"; // Redirect if user is not logged in
    }

    String loginUser = prin.getName();
    model.addAttribute("login_user", loginUser);

    // Fetch user information
    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);

    // Fetch match information
    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("matches", matches);

    return "janken.html";
  }

  @GetMapping("/janken/play")
  public String playJanken(@RequestParam(name = "hand") String yourHand, Model model) {
    String cpuHand = janken.getCpuHand(); // CPUの手を取得
    String result = janken.judge();

    model.addAttribute("yourHand", janken.getPlayerHand());
    model.addAttribute("cpuHand", cpuHand);
    model.addAttribute("result", result);

    // ユーザー情報を再度取得
    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);

    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam int id, Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();

    User currentUser = userMapper.selectByUsername(username);
    if (currentUser == null) {
      return "redirect:/login"; // Redirect if user not found
    }

    User opponent = userMapper.selectById(id);
    if (opponent == null) {
      return "redirect:/janken"; // Redirect if opponent not found
    }

    model.addAttribute("user", currentUser);
    model.addAttribute("opponent", opponent);

    return "match.html";
  }
}

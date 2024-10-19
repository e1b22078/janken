package oit.is.z2618.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2618.kaizi.janken.model.Janken;
import oit.is.z2618.kaizi.janken.model.User;
import oit.is.z2618.kaizi.janken.model.UserMapper;
import oit.is.z2618.kaizi.janken.model.Match; // 追加
import oit.is.z2618.kaizi.janken.model.MatchMapper; // 追加

@Controller
public class JankenController {
  private Janken janken;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper; // 追加

  @Autowired
  public JankenController(Janken janken) {
    this.janken = janken;
  }

  @GetMapping("/janken")
  public String janken(Model model, Principal prin) {
    String loginUser = prin.getName();
    model.addAttribute("login_user", loginUser);

    // ユーザー情報を取得
    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);

    // 試合情報を取得
    ArrayList<Match> matches = matchMapper.selectAllMatches(); // 追加
    model.addAttribute("matches", matches); // 追加

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
}

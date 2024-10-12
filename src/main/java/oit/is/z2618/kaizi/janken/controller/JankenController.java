package oit.is.z2618.kaizi.janken.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2618.kaizi.janken.model.Janken;
import oit.is.z2618.kaizi.janken.model.Entry;

@Controller
public class JankenController {
  private Janken janken;
  private Entry entry;

  @Autowired
  public JankenController(Entry entry) {
    this.janken = new Janken();
    this.entry = entry;
  }

  @GetMapping("/janken")
  public String janken(Model model, Principal prin) {
    String loginUser = prin.getName();
    entry.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    model.addAttribute("users", entry.getUsers());
    return "janken.html";
  }

  @GetMapping("/janken/play")
  public String playJanken(@RequestParam(name = "hand") String yourHand, Model model) {
    janken.setPlayerHand(yourHand);
    String cpuHand = "グー"; // CPUの手は固定
    String result = janken.judge();

    model.addAttribute("yourHand", janken.getPlayerHand());
    model.addAttribute("cpuHand", cpuHand);
    model.addAttribute("result", result);
    model.addAttribute("users", entry.getUsers()); // ユーザ情報も渡す

    return "janken.html";
  }
}

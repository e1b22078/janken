package oit.is.z2618.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

  @Autowired
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
  public String janken(Model model, Principal principal) {
    UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
    model.addAttribute("user", userDetails);
    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);

    // 試合の結果を取得してモデルに追加
    ArrayList<Match> matches = matchMapper.selectAllMatches(); // すべての試合を取得
    model.addAttribute("matches", matches); // モデルに追加
    return "janken";
  }

  @GetMapping("/match")
  public String match(@RequestParam int id, Model model, Principal principal) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();

    User currentUser = userMapper.selectByUsername(username);
    User opponent = userMapper.selectById(id);

    model.addAttribute("user", currentUser);
    model.addAttribute("opponent", opponent);

    return "match";
  }

  @GetMapping("/fight")
  public String playJanken(@RequestParam(name = "hand") String yourHand, Model model, Principal principal) {
    janken.setPlayerHand(yourHand);
    String cpuHand = janken.getCpuHand();
    String result = janken.judge();

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User currentUser = userMapper.selectByUsername(username);

    User opponent = userMapper.selectById(1);

    Match match = new Match();
    match.setUser1(currentUser.getId());
    match.setUser2(opponent.getId());
    match.setUser1Hand(yourHand);
    match.setUser2Hand(cpuHand);
    matchMapper.insertMatch(match);

    model.addAttribute("yourHand", yourHand);
    model.addAttribute("cpuHand", cpuHand);
    model.addAttribute("result", result);
    model.addAttribute("user", currentUser);
    model.addAttribute("opponent", opponent);

    return "match";
  }
}

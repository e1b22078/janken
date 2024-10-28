package oit.is.z2618.kaizi.janken.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import oit.is.z2618.kaizi.janken.model.Janken;
import oit.is.z2618.kaizi.janken.model.User;
import oit.is.z2618.kaizi.janken.model.UserMapper;
import oit.is.z2618.kaizi.janken.model.Match;
import oit.is.z2618.kaizi.janken.model.MatchMapper;
import oit.is.z2618.kaizi.janken.model.MatchInfo;
import oit.is.z2618.kaizi.janken.model.MatchInfoMapper;

@Controller
public class JankenController {

  @Autowired
  private Janken janken;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private MatchInfoMapper matchInfoMapper;

  @GetMapping("/janken")
  public String janken(Model model, Principal principal) {
    UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
    model.addAttribute("user", userDetails);

    List<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);

    List<MatchInfo> activeMatches = matchInfoMapper.selectActiveMatches();
    model.addAttribute("activeMatches", activeMatches);

    List<Match> matches = matchMapper.selectAllMatches(); // ここでエラーが発生していた
    model.addAttribute("matches", matches);

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
  public String playJanken(@RequestParam(name = "hand") String yourHand,
      @RequestParam(name = "opponentId") int opponentId,
      Model model,
      Principal principal) {
    janken.setPlayerHand(yourHand);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User currentUser = userMapper.selectByUsername(username);
    User opponent = userMapper.selectById(opponentId);

    MatchInfo matchInfo = new MatchInfo();
    matchInfo.setUser1(currentUser.getId());
    matchInfo.setUser2(opponent.getId());
    matchInfo.setUser1Hand(yourHand);
    matchInfo.setActive(true);
    matchInfoMapper.insertMatchInfo(matchInfo);

    return "redirect:/wait";
  }

  @GetMapping("/wait")
  public String waitPage(Model model, Principal principal) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User currentUser = userMapper.selectByUsername(username);
    model.addAttribute("user", currentUser);

    List<MatchInfo> activeMatches = matchInfoMapper.selectActiveMatchByUser(currentUser.getId());
    model.addAttribute("activeMatches", activeMatches);

    return "wait";
  }

  @GetMapping("/api/get-match-results")
  public ResponseEntity<Match> getResults(Principal principal) {
    Match latestMatch = matchMapper.selectLatestResult();

    if (latestMatch != null) {
      return ResponseEntity.ok(latestMatch);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}

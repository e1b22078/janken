package oit.is.z2618.kaizi.janken.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import oit.is.z2618.kaizi.janken.model.User;
import oit.is.z2618.kaizi.janken.model.UserMapper;
import oit.is.z2618.kaizi.janken.model.Match;
import oit.is.z2618.kaizi.janken.model.MatchMapper;

@RestController
public class AsyncKekka {

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private UserMapper userMapper;

  @Async
  @GetMapping("/api/get-match-result")
  public ResponseEntity<?> getResults(Principal principal) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User currentUser = userMapper.selectByUsername(username);

    // アクティブな試合を取得
    List<Match> activeMatches = matchMapper.selectActiveMatchesByUser(currentUser.getId()); // エラーが発生していた

    // 結果があれば返却
    if (!activeMatches.isEmpty()) {
      Match match = activeMatches.get(0); // 最初の試合を取得
      return ResponseEntity.ok(Map.of("match", match, "result", "勝者: " + match.getWinner()));
    } else {
      // デバッグ用ログを追加
      System.out.println("アクティブな試合が存在しません。");
      return ResponseEntity.ok(Map.of("match", null, "result", "結果はまだありません。"));
    }
  }

  @PostMapping("/updateMatch/{matchId}")
  public ResponseEntity<?> updateMatch(@PathVariable int matchId) {
    // 試合のisActiveをfalseに更新
    matchMapper.updateMatchActiveStatus(matchId, false);
    return ResponseEntity.ok(Map.of("message", "試合のステータスが更新されました。"));
  }
}

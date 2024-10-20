package oit.is.z2618.kaizi.janken.model;

import org.springframework.stereotype.Component;

@Component
public class Janken {
  private String playerHand;
  private String cpuHand = "グー"; // CPUの手を「グー」に固定

  public void setPlayerHand(String playerHand) {
    this.playerHand = playerHand; // プレイヤーの手を設定
  }

  public String getPlayerHand() {
    return playerHand;
  }

  public String getCpuHand() {
    return cpuHand; // CPUの手は常に「グー」
  }

  public String judge() {
    if (playerHand.equals(cpuHand)) {
      return "引き分け";
    } else if ((playerHand.equals("グー") && cpuHand.equals("チョキ")) ||
        (playerHand.equals("チョキ") && cpuHand.equals("パー")) ||
        (playerHand.equals("パー") && cpuHand.equals("グー"))) {
      return "勝ち";
    } else {
      return "負け";
    }
  }
}

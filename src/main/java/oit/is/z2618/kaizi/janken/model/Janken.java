package oit.is.z2618.kaizi.janken.model;

import org.springframework.stereotype.Component;

@Component
public class Janken {
  private String playerHand;
  private String cpuHand;

  public Janken() {
    this.cpuHand = "グー";
  }

  public void setPlayerHand(String playerHand) {
    this.playerHand = playerHand;
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

  public String getPlayerHand() {
    return playerHand;
  }

  public String getCpuHand() {
    return cpuHand;
  }
}

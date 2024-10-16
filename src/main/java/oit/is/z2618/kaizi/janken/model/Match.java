package oit.is.z2618.kaizi.janken.model;

public class Match {
  private int id;
  private int user1;
  private int user2;
  private String user1Hand;
  private String user2Hand;

  public int getid() {
    return id;
  }

  public void setid(int id) {
    this.id = id;
  }

  public int getuser1() {
    return user1;
  }

  public void setuser1(int user1) {
    this.user1 = user1;
  }

  public int getuser2() {
    return user2;
  }

  public void setuser2(int user2) {
    this.user2 = user2;
  }

  public String getuser1Hand() {
    return user1Hand;
  }

  public void setuser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  public String setuser2Hand() {
    return user2Hand;
  }

  public void setuser2Hand(String user2Hand) {
    this.user2Hand = user2Hand;
  }
}

package oit.is.z2618.kaizi.janken.model;

public class Match {
  private int id;
  private int user1;
  private int user2;
  private String user1Hand;
  private String user2Hand; // これは必要です

  public int getId() { // getidをgetIdに修正
    return id;
  }

  public void setId(int id) { // setidをsetIdに修正
    this.id = id;
  }

  public int getUser1() { // getuser1をgetUser1に修正
    return user1;
  }

  public void setUser1(int user1) { // setuser1をsetUser1に修正
    this.user1 = user1;
  }

  public int getUser2() { // getuser2をgetUser2に修正
    return user2;
  }

  public void setUser2(int user2) { // setuser2をsetUser2に修正
    this.user2 = user2;
  }

  public String getUser1Hand() { // getuser1HandをgetUser1Handに修正
    return user1Hand;
  }

  public void setUser1Hand(String user1Hand) { // setuser1HandをsetUser1Handに修正
    this.user1Hand = user1Hand;
  }

  public String getUser2Hand() { // getterを追加
    return user2Hand;
  }

  public void setUser2Hand(String user2Hand) { // setterを追加
    this.user2Hand = user2Hand;
  }
}

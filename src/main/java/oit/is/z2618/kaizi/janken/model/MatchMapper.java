package oit.is.z2618.kaizi.janken.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MatchMapper {
  @Insert("INSERT INTO matches (user1, user2, user1Hand, user2Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{user2Hand}, #{isActive})")
  void insertMatch(Match match);

  @Select("SELECT * FROM matches WHERE isActive = true")
  List<Match> selectActiveMatches();

  @Select("SELECT * FROM matches ORDER BY id DESC LIMIT 1")
  Match selectLatestResult();

  @Update("UPDATE matches SET isActive = #{isActive} WHERE id = #{id}")
  void updateMatchActiveStatus(int id, boolean isActive);

  @Select("SELECT * FROM matches")
  List<Match> selectAllMatches();

  @Select("SELECT * FROM matches WHERE (user1 = #{userId} OR user2 = #{userId}) AND isActive = true")
  List<Match> selectActiveMatchesByUser(int userId); // このメソッドを追加
}

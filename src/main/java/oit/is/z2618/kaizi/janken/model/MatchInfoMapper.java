package oit.is.z2618.kaizi.janken.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatchInfoMapper {
  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  void insertMatchInfo(MatchInfo matchInfo);

  @Select("SELECT * FROM matchinfo WHERE isActive = true")
  List<MatchInfo> selectActiveMatches();

  @Select("SELECT * FROM matchinfo WHERE (user1 = #{userId} OR user2 = #{userId}) AND isActive = true")
  List<MatchInfo> selectActiveMatchByUser(int userId);
}

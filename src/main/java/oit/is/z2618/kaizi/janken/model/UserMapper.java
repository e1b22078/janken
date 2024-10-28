package oit.is.z2618.kaizi.janken.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM users")
  List<User> selectAllUsers();

  @Select("SELECT * FROM users WHERE id = #{id}")
  User selectById(int id);

  @Select("SELECT * FROM users WHERE username = #{username}")
  User selectByUsername(String username);
}

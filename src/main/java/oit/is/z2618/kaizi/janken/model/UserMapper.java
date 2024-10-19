package oit.is.z2618.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT id, username from users where id = #{id}")
  User selectById(int id);

  @Select("SELECT id, username FROM users")
  ArrayList<User> selectAllUsers();
}

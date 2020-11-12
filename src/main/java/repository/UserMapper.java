package repository;

import domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    @Select(value = "SELECT * FROM community.users WHERE id = #{id}")
    User getUserById(@Param("id") Long id);

    @Select(value = "SELECT * FROM community.users WHERE account = #{account}")
    User getUserByAccount(@Param("account") String account);

    @Select(value = "SELECT * FROM community.users WHERE nickname = #{nickname}")
    User getUserByNickName(@Param("nickname") String nickName);

    @Select(value = "INSERT INTO community.users (account, password, nickname) VALUES(#{account}, #{password}, #{nickname})")
    void createUser(User user);
}

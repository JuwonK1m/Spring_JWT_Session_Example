package service;

import domain.User;
import exception.ConflictException;
import exception.UnauthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import repository.UserMapper;
import util.Jwt;
import util.Session;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Jwt jwt;
    @Autowired
    private Session session;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> register(User user) throws Exception {
        // 존재하는 아이디인지 확인
        User selectUser1 = userMapper.getUserByAccount(user.getAccount());
        if (selectUser1 != null) {
            throw new ConflictException("이미 존재하는 아이디입니다.");
        }

        // 존재하는 닉네임인지 확인
        User selectUser2 = userMapper.getUserByNickName(user.getNickname());
        if (selectUser2 != null) {
            throw new ConflictException("닉네임이 중복됩니다.");
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        userMapper.createUser(user);

        return new HashMap<String, Object>() {{
            put("message", "회원가입이 완료되었습니다.");
        }};
    }

    @Override
    public Map<String, Object> loginByJwt(User user) throws Exception {
        User selectUser = userMapper.getUserByAccount(user.getAccount());

        if (selectUser == null) {
            throw new UnauthorizeException("유저 정보가 없습니다.");
        }

        if (!passwordEncoder.matches(user.getPassword(), selectUser.getPassword())) {
            throw new UnauthorizeException("비밀번호가 틀립니다.");
        }

        String token = jwt.generateToken(selectUser.getId());
        return new HashMap<String, Object>() {{
            put("token", token);
            put("message", "로그인에 성공하였습니다.");
        }};
    }

    @Override
    public Map<String, Object> loginBySession(User user) throws Exception {
        User selectUser = userMapper.getUserByAccount(user.getAccount());

        if (selectUser == null) {
            throw new UnauthorizeException("유저 정보가 없습니다.");
        }

        if (!passwordEncoder.matches(user.getPassword(), selectUser.getPassword())) {
            throw new UnauthorizeException("비밀번호가 틀립니다.");
        }

        session.generateSession(user);

        return new HashMap<String, Object>() {{
            put("message", "로그인에 성공하였습니다.");
        }};
    }

    @Override
    public Map<String, Object> logoutByJwt() throws Exception {
        return new HashMap<String, Object>() {{
            put("message", "로그아웃에 성공하였습니다.");
        }};
    }


    @Override
    public Map<String, Object> logoutBySession() throws Exception {
        session.invalidateSession();

        return new HashMap<String, Object>() {{
            put("message", "로그아웃에 성공하였습니다.");
        }};
    }
}

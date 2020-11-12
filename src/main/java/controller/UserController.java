package controller;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.UserService;

import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // 회원가입
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody User user) throws Exception {
        return new ResponseEntity<Map<String, Object>>(userService.register(user), HttpStatus.OK);
    }

    // 로그인 (JWT 이용)
    @RequestMapping(value = "/user/login/jwt", method = RequestMethod.POST)
    public ResponseEntity loginByJwt(@RequestBody User user) throws Exception {
        return new ResponseEntity<Map<String, Object>>(userService.loginByJwt(user), HttpStatus.OK);
    }

    // 로그인 (세션 이용)
    @RequestMapping(value = "/user/login/session", method = RequestMethod.POST)
    public ResponseEntity loginBySession(@RequestBody User user) throws Exception {
        return new ResponseEntity<Map<String, Object>>(userService.loginBySession(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/logout/jwt", method = RequestMethod.POST)
    public ResponseEntity logoutByJwt() throws Exception {
        return new ResponseEntity<Map<String, Object>>(userService.logoutByJwt(), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/logout/session", method = RequestMethod.POST)
    public ResponseEntity logoutBySession() throws Exception {
        return new ResponseEntity<Map<String, Object>>(userService.logoutBySession(), HttpStatus.OK);
    }
}

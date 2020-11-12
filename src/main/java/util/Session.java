package util;

import domain.User;
import exception.UnauthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import repository.UserMapper;

import javax.servlet.http.HttpSession;

@Component
public class Session {
    @Autowired
    private UserMapper userMapper;

    private HttpSession getSession(boolean generateNewSessionIfNotExist) {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getSession(generateNewSessionIfNotExist);
    }

    public void generateSession(User user) {
        HttpSession session = getSession(true);

        User selectUser = userMapper.getUserByAccount(user.getAccount());
        session.setAttribute("userId", selectUser.getId());
    }

    public void invalidateSession() {
        HttpSession session = getSession(true);

        session.invalidate();
    }

    public User getUser() throws Exception {
        HttpSession session = getSession(false);

        if (session == null) {
            throw new UnauthorizeException("로그인 하십시오.");
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userMapper.getUserById(userId);
        return user;
    }
}

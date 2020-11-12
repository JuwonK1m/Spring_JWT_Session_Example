package service;

import domain.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> register(User user) throws Exception;
    Map<String, Object> loginByJwt(User user) throws Exception;
    Map<String, Object> loginBySession(User user) throws Exception;
    Map<String, Object> logoutByJwt() throws Exception;
    Map<String, Object> logoutBySession() throws Exception;
}

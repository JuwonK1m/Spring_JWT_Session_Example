package service;

import domain.Article;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BoardMapper;
import util.Jwt;
import util.Session;

import java.util.HashMap;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private Jwt jwt;
    @Autowired
    private Session session;
    @Autowired
    private BoardMapper boardMapper;

    public Map<String, Object> createArticleByJwt(Article article) throws Exception {
        User user = jwt.validateToken();

        article.setUser_id(user.getId());
        article.setNickname(user.getNickname());

        boardMapper.createArticle(article);

        return new HashMap<String, Object>() {{
            put("message", "게시물 등록에 성공했습니다.");
        }};
    }

    @Override
    public Map<String, Object> createArticleBySession(Article article) throws Exception {
        User user = session.getUser();

        article.setUser_id(user.getId());
        article.setNickname(user.getNickname());

        boardMapper.createArticle(article);

        return new HashMap<String, Object>() {{
            put("message", "게시물 등록에 성공하였습니다.");
        }};
    }
}

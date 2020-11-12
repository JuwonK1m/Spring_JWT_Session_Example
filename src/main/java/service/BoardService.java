package service;

import domain.Article;

import java.util.Map;

public interface BoardService {
    Map<String, Object> createArticleByJwt(Article article) throws Exception;
    Map<String, Object> createArticleBySession(Article article) throws Exception;
}

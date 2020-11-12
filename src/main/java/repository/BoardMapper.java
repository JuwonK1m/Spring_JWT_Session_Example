package repository;

import domain.Article;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMapper {
    @Insert(value = "INSERT INTO community.articles (title, user_id, content, nickname) VALUES(#{title}, #{user_id}, #{content}, #{nickname})")
    public void createArticle(Article article);
}

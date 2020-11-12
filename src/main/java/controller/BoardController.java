package controller;

import domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.BoardService;

import java.util.Map;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/article/jwt", method = RequestMethod.POST)
    public ResponseEntity createArticleByJwt(@RequestBody Article article) throws Exception {
        return new ResponseEntity<Map<String, Object>>(boardService.createArticleByJwt(article), HttpStatus.OK);
    }

    @RequestMapping(value = "/article/session", method = RequestMethod.POST)
    public ResponseEntity createArticleBySession(@RequestBody Article article) throws Exception {
        return new ResponseEntity<Map<String, Object>>(boardService.createArticleBySession(article), HttpStatus.OK);
    }
}

package org.koreait.service;

import org.koreait.container.Container;
import org.koreait.dao.ArticleDao;
import org.koreait.dto.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {

    private ArticleDao articleDao;


    public ArticleService() {
        this.articleDao = Container.articleDao;
    }

    public int doWrite(int memberId, String title, String content) {
        return articleDao.doWrite(memberId, title, content);
    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void doUpdate(int id, String title, String content) {
        articleDao.doUpdate(id, title, content);
    }

    public void doDelete(int id) {
        articleDao.doDelete(id);
    }

    public List<Article> getForPrintArticles(int page, int itemsInAPage, String searchKeyword) {
        int limitFrom = (page - 1) * itemsInAPage;
        int limitTake = itemsInAPage;

        Map<String, Object> args = new HashMap<>();

        args.put("searchKeyword", searchKeyword);
        args.put("limitTake", limitTake);
        args.put("limitFrom", limitFrom);

        return articleDao.getForPrintArticles(args);
    }
}

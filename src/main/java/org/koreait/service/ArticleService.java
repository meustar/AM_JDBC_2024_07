package org.koreait.service;

import org.koreait.Article;
import org.koreait.dao.ArticleDao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {

    private ArticleDao articleDao;


    public ArticleService(Connection conn) {
        this.articleDao = new ArticleDao(conn);
    }

    public int doWrite(String title, String content) {
        return articleDao.doWrite(title, content);
    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    public Map<String, Object> getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void doUpdate(int id, String title, String content) {
        articleDao.doUpdate(id, title, content);
    }

    public void doDelete(int id) {
        articleDao.doDelete(id);
    }
}

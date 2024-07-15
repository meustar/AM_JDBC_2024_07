package org.koreait.controller;

import org.koreait.Article;
import org.koreait.service.ArticleService;
import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController {

    Connection conn;
    Scanner sc;

    private ArticleService articleService;

    public ArticleController(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
        this.articleService = new ArticleService(conn);
    }

    public void doWrite() {
        System.out.println("== write ==");
        System.out.print("title: ");
        String title = sc.nextLine();
        System.out.print("content: ");
        String content = sc.nextLine();

        int id = articleService.doWrite(title, content);

        System.out.println(id + "번 글이 작성되었습니다.");
    }

    public void showList() {
        System.out.println("== list ==");

        List<Article> articles = articleService.getArticles();

        if (articles.size() == 0 ) {
            System.out.println("게시글이 없습니다");
            return;
        }

        System.out.println("   번호   //        제목         //          내용        ");
        for (Article article : articles) {
            System.out.printf("   %d   //        %s        //              %s         \n", article.getId(), article.getTitle(),article.getContent());
        }
    }

    public void doModify(String cmd) {

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("정수 입력");
            return;
        }

        Map<String, Object> articleMap = articleService.getArticleById(id);

        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }

        System.out.println("== modify ==");
        System.out.print("새 제목 : ");
        String title = sc.nextLine();
        System.out.print("새 내용 : ");
        String content = sc.nextLine();

        articleService.doUpdate(id, title, content);

        System.out.println(id + "번 글이 수정되었습니다.");
    }

    public void showDetail(String cmd) {
        int id = 0;

        try{
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해주세요. ");

            return;
        }
        System.out.println("== detail ==");

        Map<String, Object> articleMap = articleService.getArticleById(id);

        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }

        Article article = new Article(articleMap);

        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getContent());

    }

    public void doDelete(String cmd) {
        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해주세요. ");

            return;
        }

        Map<String, Object> articleMap = articleService.getArticleById(id);

        if (articleMap.isEmpty()) {
            System.out.println(id + "번 글은 없어");
            return;
        }

        System.out.println("== delete ==");

        articleService.doDelete(id);

        System.out.println(id + "번 글이 삭제되었습니다.");

    }
}

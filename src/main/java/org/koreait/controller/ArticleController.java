package org.koreait.controller;

import org.koreait.container.Container;
import org.koreait.dto.Article;
import org.koreait.service.ArticleService;

import java.util.List;
import java.util.Map;

public class ArticleController {

    private ArticleService articleService;

    public ArticleController() {
        this.articleService = Container.articleService;
    }

    public void doWrite() {
        System.out.println("== write ==");
        System.out.print("title: ");
        String title = Container.sc.nextLine();
        System.out.print("content: ");
        String content = Container.sc.nextLine();

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
        String title = Container.sc.nextLine();
        System.out.print("새 내용 : ");
        String content = Container.sc.nextLine();

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

package org.koreait.controller;

import org.koreait.container.Container;
import org.koreait.dto.Article;
import org.koreait.service.ArticleService;

import java.util.List;

public class ArticleController {

    private ArticleService articleService;

    public ArticleController() {
        this.articleService = Container.articleService;
    }

    public void doWrite() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그아웃 후 이용하세요.");
            return;
        }
        System.out.println("== write ==");
        System.out.print("title: ");
        String title = Container.sc.nextLine();
        System.out.print("content: ");
        String content = Container.sc.nextLine();

        int memberId = Container.session.loginedMemberId;

        int id = articleService.doWrite(memberId, title, content);

        System.out.println(id + "번 글이 작성되었습니다.");
    }

    public void showList() {
        System.out.println("== list ==");

        List<Article> articles = articleService.getArticles();

        if (articles.size() == 0 ) {
            System.out.println("게시글이 없습니다");
            return;
        }

        System.out.println("   번호   //  작성자  //        제목         //          내용        ");

        for (Article article : articles) {
            System.out.printf("   %d   //   %s   //        %s        //              %s         \n", article.getId(), article.getName(), article.getTitle(),article.getContent());
        }
    }

    public void doModify(String cmd) {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 상태가 아닙니다. 로그인해주세요.");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("정수 입력");
            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.println(id + "번 글은 없습니다.");
            return;
        }
        if (article.getMemberId() != Container.session.loginedMemberId) {
            System.out.println("권한이 없습니다.");
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
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 상태가 아닙니다. 로그인해주세요.");
        }

        int id = 0;

        try{
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해주세요. ");

            return;
        }
        System.out.println("== detail ==");

        Article article = articleService.getArticleById(id);

        if (article == null){
            System.out.println(id + "번 글은 없습니다.");
            return;
        }

        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("작성자 : " + article.getMemberId());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getContent());

    }

    public void doDelete(String cmd) {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 상태가 아닙니다. 로그인해주세요.");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해주세요. ");

            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.println(id + "번 글은 없어");
            return;
        }

        if(article.getMemberId() != Container.session.loginedMemberId) {
            System.out.println("권한이 없습니다.");
            return;
        }

        System.out.println("== delete ==");

        articleService.doDelete(id);

        System.out.println(id + "번 글이 삭제되었습니다.");

    }
}

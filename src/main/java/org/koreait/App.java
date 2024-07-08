package org.koreait;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private Scanner sc;

    public App(Scanner sc) {
        this.sc = sc;
    }

    public void run() throws SQLException {
        System.out.println("프로그램 실행");
        int lastArticleId = 0;

        List<Article> articles = new ArrayList<>();

        while (true) {
            System.out.print("명령어: ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                Controller.exit();
                break;
            }
            if (cmd.length() == 0) {
                System.out.println("명령어를 입력해주세요. :)");
                continue;
            }

            if (cmd.equals("article write")) {
                int lastid = lastArticleId + 1;
                System.out.println("== write ==");
                System.out.print("title: ");
                String title = sc.nextLine();
                System.out.print("content: ");
                String content = sc.nextLine();

                Article aricle = new Article(lastid, title, content);
                articles.add(aricle);

                System.out.println(aricle);
                lastArticleId++;

            } else if (cmd.equals("article list")) {
                System.out.println("== list ==");
                if (articles.size() == 0 ) {
                    System.out.println("게시글이 없습니다");
                    continue;
                }
                System.out.println("   번호   //        제목         ");
                for (Article article : articles) {
                    System.out.printf("   %d   //        %d         ", article.getId(), article.getTitle());
                }
            }
        }

    }
}

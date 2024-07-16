package org.koreait;

import org.koreait.container.Container;
import org.koreait.controller.ArticleController;
import org.koreait.controller.MemberController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    private Scanner sc;

    public App() {
        Container.init();
        this.sc = Container.sc;
    }

    public void run() {
        System.out.println("== 프로그램 실행 ==");

        while (true) {
            System.out.print("명령어: ");
            String cmd = sc.nextLine().trim();

            Connection conn = null;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";

            try {
                conn = DriverManager.getConnection(url, "root", "");
                Container.conn = conn;

                int actionResult = action(cmd);

                if (actionResult == -1) {
                    System.out.println("== 프로그램 종료 ==");
                    sc.close();
                    break;
                }
            } catch (SQLException e) {
                System.out.println("에러 1 : " + e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (cmd.length() == 0) {
                System.out.println("명령어를 입력해주세요. :)");
                continue;
            }
        }
    }

    private int action(String cmd) {

        if (cmd.equals("exit")) {
            return -1;
        }

        MemberController memberController = Container.memberController;
        ArticleController articleController = Container.articleController;

        if (cmd.equals("member profile")) {
            memberController.showProfile();
        } else if (cmd.equals("member join")) {
            memberController.doJoin();
        } else if (cmd.equals("member login")) {
            memberController.login();
        } else if (cmd.equals("member logout")) {
            memberController.logout();
        } else if (cmd.equals("article write")) {
            articleController.doWrite();
        } else if (cmd.startsWith("article list")) {
            articleController.showList(cmd);
        } else if (cmd.startsWith("article modify")) {
            articleController.doModify(cmd);
        } else if (cmd.startsWith("article detail")) {
            articleController.showDetail(cmd);
        } else if (cmd.startsWith("article delete")) {
            articleController.doDelete(cmd);
        } else {
            System.out.println("사용할 수 없는 명령어 입니다.");
        }
        return 0;
    }
}

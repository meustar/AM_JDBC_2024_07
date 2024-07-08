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

    public void run() {
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
                int id = lastArticleId + 1;
                System.out.println("== write ==");
                System.out.print("title: ");
                String title = sc.nextLine();
                System.out.print("content: ");
                String content = sc.nextLine();

                Article aricle = new Article(id, title, content);

                System.out.println(aricle);
                lastArticleId++;

                System.out.println(id + "번 글이 작성되었습니다.");


                Connection conn = null;
                PreparedStatement pstmt = null;

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공");

                    String sql = "INSERT INTO article ";
                    sql += "SET regDate = NOW(),";
                    sql += "updateDate = NOW(),";
                    sql += "title = '" + title + "',";
                    sql += "content = '" + content + "';";

                    System.out.println(sql);

                    pstmt = conn.prepareStatement(sql);

                    int affectedRows = pstmt.executeUpdate();

                    System.out.println("affected rows : " + affectedRows);


                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


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

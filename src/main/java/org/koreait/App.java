package org.koreait;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public void run() {
        System.out.println("== 프로그램 실행 ==");
        Scanner sc = new Scanner(System.in);

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

                int actionResult = doAction(conn, sc, cmd);

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

    private int doAction(Connection conn, Scanner sc, String cmd) {

        if (cmd.equals("exit")) {
            return -1;
        }

        if (cmd.equals("article write")) {
            System.out.println("== write ==");
            System.out.print("title: ");
            String title = sc.nextLine();
            System.out.print("content: ");
            String content = sc.nextLine();

            PreparedStatement pstmt = null;

            try {
                String sql = "INSERT INTO article ";
                sql += "SET regDate = NOW(),";
                sql += "updateDate = NOW(),";
                sql += "title = '" + title + "',";
                sql += "content = '" + content + "';";

                System.out.println(sql);

                pstmt = conn.prepareStatement(sql);

                int affectedRows = pstmt.executeUpdate();

                System.out.println(affectedRows + "열에 적용됨");

            } catch (SQLException e) {
                System.out.println("에러 : " + e);
            } finally {
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

            PreparedStatement pstmt = null;
            ResultSet rs = null;            // 결과를 담을 ResultSet.(DB에 명령할 해당 SQL문의 결과를 rs에 담는다. ex> select 한 table 통째로 담기.

            List<Article> articles = new ArrayList<>();

            try {
                String sql = "SELECT * ";
                sql += "FROM article ";
                sql += "ORDER BY id DESC";

                System.out.println(sql);

                pstmt = conn.prepareStatement(sql);

                rs = pstmt.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String regDate = rs.getString("regDate");
                    String updateDate = rs.getString("updateDate");
                    String title = rs.getString("title");
                    String content = rs.getString("content");

                    Article article = new Article(id, regDate, updateDate, title, content);

                    articles.add(article);
                }

            } catch (SQLException e) {
                System.out.println("에러 3 : " + e);
            } finally {

                try {
                    if (rs != null && !rs.isClosed()) {
                        rs.close();
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

            if (articles.size() == 0 ) {
                System.out.println("게시글이 없습니다");
                return 0;
            }

            System.out.println("   번호   //        제목         //          내용        ");
            for (Article article : articles) {
                System.out.printf("   %d   //        %s        //              %s         \n", article.getId(), article.getTitle(),article.getContent());
            }

        } else if (cmd.startsWith("article modify")) {

            int id = 0;

            try {
                id = Integer.parseInt(cmd.split(" ")[2]);
            } catch (Exception e) {
                System.out.println("정수 입력");
                return 0;
            }
            System.out.println("== modify ==");
            System.out.print("새 제목 : ");
            String title = sc.nextLine();
            System.out.print("새 내용 : ");
            String content = sc.nextLine();

            PreparedStatement pstmt = null;

            try {
                String sql = "UPDATE article ";
                sql += "SET updateDate = NOW(),";
                if (title.length() > 0) {
                    sql += " , title = '" + title + "'";
                }
                if (content.length() > 0) {
                    sql += " , content = '" + content + "'";
                }

                sql += " WHERE id = " + id + ";";

                System.out.println(sql);

                pstmt = conn.prepareStatement(sql);

                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("에러 4 : " + e);
            } finally {
                try {
                    if (pstmt != null && !pstmt.isClosed()) {
                        pstmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(id + "번 글이 수정되었습니다.");

        }
        return 0;
    }
}

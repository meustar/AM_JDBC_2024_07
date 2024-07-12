package org.koreait;

import org.koreait.util.DBUtil;
import org.koreait.util.SecSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

            SecSql sql = new SecSql();

            sql.append("INSERT INTO article");
            sql.append("SET regDate = NOW(),");
            sql.append("updateDate = NOW(),");
            sql.append("title = ?,", title);
            sql.append("content = ?,", content);

            int id = DBUtil.insert(conn, sql);

            System.out.println(id + "번 글이 작성되었습니다.");

        } else if (cmd.equals("article list")) {
            System.out.println("== list ==");

            List<Article> articles = new ArrayList<>();

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append("FROM article");
            sql.append("ORDER BY id DESC");

            List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

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

            SecSql sql = new SecSql();
            sql.append("UPDATE article");
            sql.append("SET updateDate = NOW()");

            if(title.length() > 0) {
                sql.append(",title = ?", title);
            }
            if(content.length() > 0) {
                sql.append(",content = ?", content);
            }
            sql.append("WHERE id = ?", id);

            DBUtil.update(conn, sql);

            System.out.println(id + "번 글이 수정되었습니다.");

        }
        return 0;
    }
}

package org.koreait;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    private Scanner sc;

    public App(Scanner sc) {
        this.sc = sc;
    }

    public void run() throws SQLException {
        System.out.println("프로그램 실행");
        int lastArticleId = 0;

//        List<Article> articles = new ArrayList<>();

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

//                Article aricle = new Article(id, title, content);

//                System.out.println(aricle);
                lastArticleId++;

                System.out.println(id + "번 글이 작성되었습니다.");


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
                        if (pstmt != null && !pstmt.isClosed()) {
                            pstmt.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else if (cmd.equals("article list")) {
                System.out.println("== list ==");

                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;            // 결과를 담을 ResultSet.(DB에 명령할 해당 SQL문의 결과를 rs에 담는다. ex> select 한 table 통째로 담기.

                List<Article> articles = new ArrayList<>();

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공");

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

                    for (int i = 0; i < articles.size(); i++) {
                        System.out.println("번호: " + articles.get(i).getId());
                        System.out.println("작성날짜: " + articles.get(i).getRegDate());
                        System.out.println("수정날짜: " + articles.get(i).getUpdateDate());
                        System.out.println("제목: " + articles.get(i).getTitle());
                        System.out.println("내용: " + articles.get(i).getContent());
                    }

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
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
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

                if (articles.size() == 0 ) {
                    System.out.println("게시글이 없습니다");
                    continue;
                }
                System.out.println("   번호   //        제목         //          내용        ");
                for (Article article : articles) {
                    System.out.printf("   %d   //        %s        //              %s         \n", article.getId(), article.getTitle(),article.getContent());
                }

//            } else if (cmd.startsWith("article delete")) {
//                System.out.println("== delete ==");
//
//                int id = Integer.parseInt(cmd.split(" ")[2]);
//
//                Article foundArticle = null;
//
//                for (Article article : articles) {
//                    if (article.getId() == id) {
//                        foundArticle = article;
//                        break;
//                    }
//                }
//                if (foundArticle == null) {
//                    System.out.println("해당 게시글은 없습니다.");
//                    continue;
//                }
//                articles.remove(foundArticle);
//                System.out.println(id + "번 게시글이 삭제되었습니다.");
//
            } else if (cmd.startsWith("article modify")) {
                int id = 0;

                try {
                    id = Integer.parseInt(cmd.split(" ")[2]);
                } catch (Exception e) {
                    System.out.println("정수 입력");
                    continue;
                }
                System.out.println("== modify ==");
                System.out.print("새 제목 : ");
                String title = sc.nextLine();
                System.out.print("새 내용 : ");
                String content = sc.nextLine();

                ///////////////////////////////

                Connection conn = null;
                PreparedStatement pstmt = null;

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
                    conn = DriverManager.getConnection(url, "root", "");
                    System.out.println("연결 성공");

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

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
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
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println(id + "번 글이 수정되었습니다.");

//                Article foundArticle = null;          // DB가 없을때 ArrayList를 DB 저장소로 쓰였을때, 찾고자하는 id를 순회할때 쓰던 방법. => DB를 사용하고 부터는 select id로 찾을수 있다.
//
//                for (Article article : articles) {
//                    if (article.getId() == id) {
//                        foundArticle = article;
//                        break;
//                    }
//                }
//                if (foundArticle == null) {
//                    System.out.println("해당 게시글은 없습니다.");
//                    continue;
//                }
//                System.out.println("기존 제목 : " + foundArticle.getTitle());
//                System.out.println("기존 내용 : " + foundArticle.getContent());
//                System.out.print("새 제목 : ");
//                String newTitle = sc.nextLine();
//                System.out.print("새 내목 : ");
//                String newContent = sc.nextLine();
//
//                foundArticle.setTitle(newTitle);
//                foundArticle.setContent(newContent);
//                System.out.println(id + "번 게시글이 수정 되었습니다.");
//            } else {
//                System.out.println("사용할 수 없는 명령어입니다.");
            }


        }
    }
}

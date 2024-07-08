package org.koreait;

import java.sql.*;

public class JDBCSelectTest {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:3306/AM_JDBC_2024_07?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println("연결 성공");

            String sql = "INSERT INTO article ";
            sql += "SET regDate = NOW(),";
            sql += "updateDate = NOW(),";
            sql += "title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),";
            sql += "content = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));";

            System.out.println(sql);

            pstmt = conn.prepareStatement(sql);

            int affectedRows = pstmt.executeUpdate();

            System.out.println("affected rows : " + affectedRows);

            rs = pstmt.executeQuery("select * from article");

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String regDate = rs.getString("regDate");
                String updateDate = rs.getString("updateDate");
                String title = rs.getString("title");
                String content = rs.getString("content");
                System.out.println(id + " " + regDate + " " + updateDate + " " + title + " " + content);
            }

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
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}

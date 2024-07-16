package org.koreait;

import org.koreait.exception.SQLErrorException;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        try {
            new App().run();
        } catch (SQLErrorException e) {
            System.err.println("e.getOrigin : " + e.getOrigin());
            e.getOrigin().printStackTrace();
        }

    }
}
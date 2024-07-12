package org.koreait.service;

import java.sql.Connection;

public class MemberService {

    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public boolean isLoginIdDup(Connection conn, String loginId) {
    }
}

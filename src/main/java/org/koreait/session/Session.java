package org.koreait.session;

import org.koreait.dto.Member;

public class Session {
    // 로그인 정보는 여기에다.


    public Member loginedMember;
    public int loginedMemberId;

    public Session() {
        loginedMember = null;
        loginedMemberId = -1;
    }

}

package org.koreait.controller;

import org.koreait.container.Container;
import org.koreait.dto.Member;
import org.koreait.service.MemberService;

public class MemberController {

    private MemberService memberService;

    public MemberController() {

        this.memberService = Container.memberService;
    }

    public void doJoin() {
        if (Container.session.isLogined()) {
            System.out.println("로그아웃 후 이용하세요.");
            return;
        }
        String loginId = null;
        String loginPw = null;
        String loginPwConfirm = null;
        String name = null;

        System.out.println("== 회원가입 ==");
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = Container.sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디를 정확하게 입력해주세요.");
                continue;
            }

            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

            if (isLoginIdDup) {
                System.out.println(loginId + "는(은) 이미 사용중인 아이디 입니다.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("비밀번호 아이디 : ");
            loginPw = Container.sc.nextLine().trim();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                System.out.println("비밀번호 정확하게 입력해주세요.");
                continue;
            }

            boolean loginPwCheck = true;

            while (true) {
                System.out.print("비밀번호 확인 : ");
                loginPwConfirm = Container.sc.nextLine().trim();

                if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
                    System.out.println("비밀번호 확인을 정확히 입력해주세요.");
                    continue;
                }
                if (loginPw.equals(loginPwConfirm) == false) {
                    System.out.println("일치하지 않습니다.");
                    loginPwCheck = false;
                }
                break;
            }
            if (loginPwCheck) {
                break;
            }
        }
        while (true) {
            System.out.print("이름 : ");
            name = Container.sc.nextLine().trim();

            if (name.length() == 0 || name.contains(" ")) {
                System.out.println("이름을 다시 작성해주세요.");
                continue;
            }
            break;
        }


        int id = memberService.doJoin(loginId, loginPw, name);

        System.out.println(id + "번 회원이 생성되었습니다.");
    }

    public void login() {
        if (Container.session.isLogined()) {
            System.out.println("로그아웃 후 이용하세요.");
            return;
        }

        String loginId = null;
        String loginPw = null;

        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = Container.sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디를 다시 입력해주세요.");
                continue;
            }

            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

            if (isLoginIdDup == false) {
                System.out.println(loginId + "는(은) 없는 아이디 입니다.");
                continue;
            }
            break;
        }
        Member member = memberService.getMemberByLoginId(loginId);

        int tryMaxCount = 3;
        int tryCount = 0;

        while (true) {
            if (tryCount >= tryMaxCount) {
                System.out.println("비밀번호를 다시 확인하고 시도해주세요.");
                System.out.println(tryCount + "번 실패하였습니다." + (tryMaxCount - tryCount) + "번 남았습니다.");
                break;
            }

            System.out.print("비밀번호 : ");
            loginPw = Container.sc.nextLine().trim();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                tryCount++;
                System.out.println("비밀번호 정확하게 입력해주세요.");
                System.out.println(tryCount + "번 실패하였습니다." + (tryMaxCount - tryCount) + "번 남았습니다.");
                continue;
            }
            if (member.getLoginPw().equals(loginPw) == false) {
                tryCount++;
                System.out.println("일치하지 않아");
                System.out.println(tryCount + "번 실패하였습니다." + (tryMaxCount - tryCount) + "번 남았습니다.");
                continue;
            }

            Container.session.login(member);

            System.out.println(member.getName() + "님 환영합니다.");
            break;
        }

    }

    public void logout() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 상태가 아닙니다. 로그인해주세요.");
            return;
        }

        System.out.println("== Logout ==");
        Container.session.logout();
        System.out.println("로그아웃 되었습니다.");
    }

    public void showProfile() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 상태가 아닙니다. 로그인해주세요.");
            return;
        } else {
            System.out.println(Container.session.loginedMember);
        }
    }

}


package kr.co.connect.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class GuestbookAdminController {

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    /**
     * 로그인 처리
     *
     * @param passwd
     * @param session            비밀번호가 맞는 경우 세션에 값을 저장하기 위해 사용
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam(name = "passwd", required = true) String passwd,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        if ("1234".equals(passwd)) {
            session.setAttribute("isAdmin", true);
        } else {
            redirectAttributes.addFlashAttribute("message", "비밀번호가 틀렸습니다.");
            return "redirect:/login";
        }

        return "redirect:/list";
    }

    /**
     * 로그아웃 처리
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("isAdmin");

        return "redirect:/list";
    }
}

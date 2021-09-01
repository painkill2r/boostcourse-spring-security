package kr.co.connect.springsecurity.controller;

import kr.co.connect.springsecurity.dto.Guestbook;
import kr.co.connect.springsecurity.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GuestbookController {

    @Autowired
    private GuestbookService guestbookService;

    /**
     * @param request  쿠키를 사용하기 위해 request 객체 사용
     * @param response 쿠키를 사용하기 위해 response 객체 사용
     * @param start
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam(name = "start", required = false, defaultValue = "0") int start,
                       Model model) {

        String value = null;
        boolean find = false;

        Cookie[] cookies = request.getCookies(); //클라이언트로 부터 쿠키 배열을 가져옴.
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("count".equals(cookie.getName())) { //"count"라는 이름과 동일한 쿠키인 경우
                    find = true;
                    value = cookie.getValue();
                    break;
                }
            }
        }

        if (!find) {
            value = "1"; //쿠키는 String 타입의 값을 가질 수 있다.
        } else {
            try {
                int i = Integer.parseInt(value);
                value = Integer.toString(++i);
            } catch (Exception e) {
                value = "1";
            }
        }

        Cookie cookie = new Cookie("count", value);
        cookie.setMaxAge(60 * 60 * 24 * 365); //1년 동안 유지
        cookie.setPath("/"); //경로 이하에 모두 쿠키 적용

        response.addCookie(cookie); //응답에 쿠키를 같이 보냄(클라이언트에 저장)

        List<Guestbook> list = guestbookService.getGuestbooks(start);
        int count = guestbookService.getCount(); //전체 페이지 수
        int pageCount = count / GuestbookService.LIMIT;

        if (count % GuestbookService.LIMIT > 0)
            pageCount++;

        // 페이지 수만큼 start의 값을 리스트로 저장
        // 예를 들면 페이지수가 3이면
        // 0, 5, 10 이렇게 저장된다.
        // list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
        List<Integer> pageStartList = new ArrayList<>();

        for (int i = 0; i < pageCount; i++) {
            pageStartList.add(i * GuestbookService.LIMIT);
        }

        model.addAttribute("count", count);
        model.addAttribute("list", list);
        model.addAttribute("pageStartList", pageStartList);
        model.addAttribute("visitCount", value);

        return "guestbook/list";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Guestbook guestbook, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();

        System.out.println("clientIp : " + clientIp);

        guestbookService.addGuestbook(guestbook, clientIp);

        return "redirect:/list";
    }
}
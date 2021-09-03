package kr.co.connect.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class GuessNumberController {

    /**
     * @param session 스프링을 사용하지 않았을 때는 HttpServletRequest 객체에서 Session을 직접 얻어서 사용했었는데, 스프링에서는 그 작업을 대신 처리 해줌.
     * @param number
     * @return
     */
    @GetMapping("/guess")
    public String guess(HttpSession session,
                        @RequestParam(name = "number", required = false) Integer number,
                        ModelMap model) {
        String message = null;

        if (number == null) {
            session.setAttribute("count", 0);
            session.setAttribute("randomNumber", (int) (Math.random() * 100) + 1); //1 ~ 100 사이의 숫자 저장
            message = "생각한 숫자를 맞춰보세요.";
        } else {
            int count = (Integer) session.getAttribute("count");
            int randomNumber = (Integer) session.getAttribute("randomNumber");

            if (number < randomNumber) {
                message = "입력한 값은 생각한 숫자보다 작습니다.";
                session.setAttribute("count", count + 1);
            } else if (number > randomNumber) {
                message = "입력한 값은 생각한 숫자보다 큽니다.";
                session.setAttribute("count", count + 1);
            } else {
                message = "OK! " + ++count + "번째 맞췄습니다. 생각한 숫자는 " + number + "입니다.";
                session.removeAttribute("count");
                session.removeAttribute("randomNumber");
            }
        }

        model.addAttribute("message", message);

        return "guess";
    }
}

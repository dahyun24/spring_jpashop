package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    // model에다 데이터를 실어서 veiw에 넘길 수 있다.
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello"; // => resources/templates 에 hello.html을 만들어준다. 즉, 결과 화면 랜더링 할때 hello.html로 이동된다.
    }
}

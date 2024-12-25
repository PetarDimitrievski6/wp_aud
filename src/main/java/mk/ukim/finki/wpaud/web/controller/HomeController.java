package mk.ukim.finki.wpaud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {
    public String getHomePage(){
        return "home";
    }
    @GetMapping("access_denied")
    public String accessDenied(Model model){
        model.addAttribute("bodyContent", "access_denied");
        return "master-template";
    }
}

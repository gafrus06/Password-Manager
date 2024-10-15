package sgugit.ru.savepass.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import sgugit.ru.savepass.Model.Password;
import sgugit.ru.savepass.Model.User;
import sgugit.ru.savepass.Repository.UserRepo;
import sgugit.ru.savepass.Service.FaviconService;
import sgugit.ru.savepass.Service.HomeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/")
    public String passwords(Authentication authentication, Model model){
        String username = authentication.getName();
        model.addAttribute("login", username);
        model.addAttribute("passwords", homeService.getPasswords(username));
        return "mainPage";
    }
}

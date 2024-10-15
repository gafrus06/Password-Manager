package sgugit.ru.savepass.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgugit.ru.savepass.Model.User;
import sgugit.ru.savepass.Service.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/registration")
    public String createUser(User user, RedirectAttributes redirectAttributes) {
        if (!userService.createUser(user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с логином: " + user.getLogin() + " уже существует");
            return "redirect:/registration"; // редирект на страницу регистрации
        }
        return "login";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }


}

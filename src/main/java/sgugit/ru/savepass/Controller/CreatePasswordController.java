package sgugit.ru.savepass.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sgugit.ru.savepass.Model.Password;
import sgugit.ru.savepass.Service.PasswordService;

import java.security.Principal;

@Controller
@Slf4j
public class CreatePasswordController {
    private final PasswordService passwordService;

    public CreatePasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/create-password")
    public String createPassPage(){
        return "createPassPage";
    }

    @PostMapping("/save-password")
    public String savePassword(Password password, Principal authentication){
        log.info("save password");
        log.info(authentication.getName());
        try {
            passwordService.savePassword(authentication.getName(), password);
        } catch (Exception e) {
            log.error("CreatePasswordController + "+e.getMessage());
        }
        return "redirect:/";
    }
}

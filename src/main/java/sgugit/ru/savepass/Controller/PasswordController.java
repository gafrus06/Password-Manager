package sgugit.ru.savepass.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sgugit.ru.savepass.Model.Password;
import sgugit.ru.savepass.Service.PasswordService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping("/")
    public String passwords(Principal authentication, Model model){
        String username = authentication.getName();
        log.info(authentication.getName());
        model.addAttribute("login", username);
        model.addAttribute("passwords", passwordService.getPasswords(username));
        return "mainPage";
    }
    @GetMapping("/generate-password")
    public String generatePage(){
        return "generate";
    }
    @GetMapping("/password/{passId}")
    public String info(@PathVariable Long passId, Model model){
        Password password = passwordService.getPassword(passId);
        model.addAttribute("password", password);
        try {
            password.setWebLogin(passwordService.getDecryptedLogin(password));
            password.setWebPass(passwordService.getDecryptedPassword(password));
        } catch (Exception e) {
            log.error(e.getMessage());
        } return "passwordPage";
    }
    @PostMapping("/delete-password/{passId}")
    public String deletePass(@PathVariable Long passId){
        passwordService.deletePassword(passId);
        return "redirect:/";
    }
    @GetMapping("/edit-password/{passId}")
    public String editPage(@PathVariable Long passId, Model model) {
        Password password = passwordService.getPassword(passId);
        try {
            password.setWebLogin(passwordService.getDecryptedLogin(password));
            password.setWebPass(passwordService.getDecryptedPassword(password));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        model.addAttribute("password", password);
        return "editPassPage";
    }

    @PostMapping("/edit-password/{passId}")
    public String editPass(@PathVariable Long passId,
                           @ModelAttribute Password password,
                           Principal authentication) throws Exception {
        log.info(authentication.getName());
            passwordService.editPassword(authentication.getName(), password);

        return "redirect:/password/" + passId;
    }
}

package sgugit.ru.savepass.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sgugit.ru.savepass.Model.User;
import sgugit.ru.savepass.Repository.UserRepo;

import java.security.Principal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public boolean createUser(User user) {
        String login = user.getLogin();
        User existingUser = userRepo.findByLogin(login);

        if (existingUser != null) return false;

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLogin(login); // Присваиваем логин напрямую, так как пользователь еще не существует
        log.info("Save new User with login " + login);
        userRepo.save(user);
        return true;
    }


}

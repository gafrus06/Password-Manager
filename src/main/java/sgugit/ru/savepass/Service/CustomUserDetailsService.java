package sgugit.ru.savepass.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sgugit.ru.savepass.Repository.UserRepo;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("Attempting to load user by login: {}", login);
        log.info(userRepo.findAll().toString());
        UserDetails user = userRepo.findByLogin(login);
        if (user == null) {
            log.warn("User not found: {}", login);
            throw new UsernameNotFoundException("User not found: " + login);
        }
        return user;
    }
}

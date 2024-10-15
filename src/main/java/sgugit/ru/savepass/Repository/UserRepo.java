package sgugit.ru.savepass.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import sgugit.ru.savepass.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);

}

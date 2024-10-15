package sgugit.ru.savepass.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgugit.ru.savepass.Model.Password;

import java.util.List;

@Repository
public interface PasswordRepo extends JpaRepository<Password, Long> {
}

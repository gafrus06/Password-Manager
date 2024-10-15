package sgugit.ru.savepass.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sgugit.ru.savepass.Model.Password;
import sgugit.ru.savepass.Model.User;
import sgugit.ru.savepass.Repository.PasswordRepo;
import sgugit.ru.savepass.Repository.UserRepo;
import sgugit.ru.savepass.utils.EncryptionUtil;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class PasswordService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordRepo passwordRepo;
    @Autowired
    private PasswordChecker passwordChecker;

    private final SecretKey secretKey;

    @SneakyThrows
    public PasswordService() {
        secretKey = loadOrCreateKey();

    }
    @SneakyThrows
    private SecretKey loadOrCreateKey() {
        String keyString = "dhv+nHrx0AbDbxRMum11SA==";
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
    public void savePassword(String login, Password password) throws Exception {
        User user = userRepo.findByLogin(login);
        String url = password.getUrl();

        // Проверяем наличие слэша после протокола
        int endIndex = url.indexOf("/", 8);
        String newUrl = (endIndex != -1) ? url.substring(0, endIndex) : url;

        String encryptedPassword = EncryptionUtil.encrypt(password.getWebPass(), secretKey);
        String encryptedLogin = EncryptionUtil.encrypt(password.getWebLogin(), secretKey);

        Password newPass = Password.builder()
                .webName(password.getWebName())
                .url(newUrl)
                .webPass(encryptedPassword)
                .webLogin(encryptedLogin)
                .user(user)
                .condition(passwordChecker.isPasswordCompromised(String.valueOf(password.isCondition())))
                .iconUrl(newUrl + "/favicon.ico")
                .build();

        passwordRepo.save(newPass);
    }

    public String getDecryptedPassword(Password password) throws Exception {
        return EncryptionUtil.decrypt(password.getWebPass(), secretKey);
    }

    public String getDecryptedLogin(Password password) throws Exception {
        return EncryptionUtil.decrypt(password.getWebLogin(), secretKey);
    }

    public List<Password> getPasswords(String login){
        User user  = userRepo.findByLogin(login);

        return user.getPasswords();
    }
    public List<Password> getPasswords(Long userId){
        User user  = userRepo.findById(userId).orElseThrow();

        return user.getPasswords();
    }
    public Password getPassword(Long id) {

        return passwordRepo.findById(id).orElseThrow();
    }


    public void deletePassword(Long passId) {
        passwordRepo.delete(passwordRepo.findById(passId).orElseThrow());
    }

    public void editPassword(String name, Password password) throws Exception {
        User user = userRepo.findByLogin(name);
        String url = password.getUrl();

        // Проверяем наличие слэша после протокола
        int endIndex = url.indexOf("/", 8);
        String newUrl = (endIndex != -1) ? url.substring(0, endIndex) : url;

        String encryptedPassword = EncryptionUtil.encrypt(password.getWebPass(), secretKey);
        String encryptedLogin = EncryptionUtil.encrypt(password.getWebLogin(), secretKey);
        log.info(password.getWebPass());
        Password newPass = Password.builder()
                .passId(password.getPassId())
                .webName(password.getWebName())
                .url(newUrl)
                .webPass(encryptedPassword)
                .webLogin(encryptedLogin)
                .user(user)
                .condition(passwordChecker.isPasswordCompromised(password.getWebPass()))
                .createdOfPassword(LocalDateTime.now())
                .iconUrl(newUrl + "/favicon.ico")
                .build();


        passwordRepo.save(newPass);
    }
}

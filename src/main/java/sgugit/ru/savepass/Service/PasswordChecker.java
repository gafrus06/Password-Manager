package sgugit.ru.savepass.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class PasswordChecker {
    private static final String API_URL = "https://api.pwnedpasswords.com/range/";

    public boolean isPasswordCompromised(String password) throws Exception {
        String hash = getSHA1Hash(password);
        String prefix = hash.substring(0, 5);
        String suffix = hash.substring(5).toUpperCase();

        String response = sendRequest(prefix);
        return response.contains(suffix);
    }

    private String getSHA1Hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    private String sendRequest(String prefix) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_URL + prefix, String.class);
    }
}

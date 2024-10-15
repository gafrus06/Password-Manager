package sgugit.ru.savepass.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "passwords")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passId;
    private String webName;
    private String webLogin;
    private String webPass;
    private String url;
    private LocalDateTime createdOfPassword;
    @Column(name = "condition")

    private boolean condition;
    private String iconUrl;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void init(){
        createdOfPassword = LocalDateTime.now();
    }
}

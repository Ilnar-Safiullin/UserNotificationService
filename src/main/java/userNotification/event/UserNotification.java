package userNotification.event;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserNotification {
    private String email;
    private String message;
}

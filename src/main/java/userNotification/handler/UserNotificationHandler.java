package userNotification.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import userNotification.event.UserNotification;

@Component
@Slf4j
@RequiredArgsConstructor
@Service
public class UserNotificationHandler {

    @KafkaListener(topics = "${spring.kafka.topic.user.user-created-del-topic}", groupId = "userAPP")
    public void listen(UserNotification notification) {
        try {
            log.info("start send " + notification.getEmail());
            sendEmail(notification.getEmail(), notification.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void sendEmail(String to, String message) {
        System.out.println("Email отправлен");
    }
}

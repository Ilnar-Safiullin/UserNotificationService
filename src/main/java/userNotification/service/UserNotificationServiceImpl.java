package userNotification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserNotificationServiceImpl implements UserNotificationService {
    private final String CREATE = "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан";
    private final String DELETE = "Здравствуйте! Ваш аккаунт был удалён";


    @KafkaListener(topics = "${spring.kafka.topic.user.user-created-del-topic}", groupId = "userAPP")
    public void listen(String message) {
        log.info("Начинаем обрабатывать запрос с kafka " + message);
        sendNotification(message);
    }

    private void sendEmail(String to, String message) {
        System.out.println("Email отправлен на " + to + " с сообщением: " + message);
    }

    @Override
    public String sendNotification(String message) {
        String[] parts = message.split(":");
        if (parts.length != 2) {
            log.error("Неверный формат сообщения: {}", message);
            throw new IllegalArgumentException("Неверный формат сообщения");
        }

        String email = parts[0];
        String action = parts[1];

        try {
            if ("CREATE".equals(action)) {
                log.info("Начинаем отправку письма о создании профиля на {}", email);
                sendEmail(email, CREATE);
                return CREATE;
            } else if ("DELETE".equals(action)) {
                log.info("Начинаем отправку письма о удалении профиля на {}", email);
                sendEmail(email, DELETE);
                return DELETE;
            } else {
                log.error("Неизвестное действие: {}", action);
                throw new IllegalArgumentException("Неверный формат сообщения");
            }

        } catch (Exception e) {
            log.error("Ошибка при отправке email: {}", e.getMessage());
            return message;
        }
    }
}
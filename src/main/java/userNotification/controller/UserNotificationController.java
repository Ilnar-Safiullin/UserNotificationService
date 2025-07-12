package userNotification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import userNotification.service.UserNotificationService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/notification")
@Validated
@Slf4j
public class UserNotificationController {
    private final UserNotificationService userNotificationService;


    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody String message) {
        try {
            String response = userNotificationService.sendNotification(message);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Неверный формат сообщения: {}", message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Ошибка при отправке уведомления: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обработке уведомления");
        }
    }
}
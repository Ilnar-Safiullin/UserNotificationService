package userNotification;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import userNotification.controller.UserNotificationController;
import userNotification.service.UserNotificationService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserNotificationController.class)
public class UserUserNotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserNotificationService userNotificationService;

    @Test
    void sendNotification() throws Exception {
        String message = "test@mail.ru:CREATE";
        Mockito.when(userNotificationService.sendNotification(message)).thenReturn("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан");

        mockMvc.perform(post("/notification")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(message))
                .andExpect(status().isOk())
                .andExpect(content().string("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан"));

        verify(userNotificationService).sendNotification("test@mail.ru:CREATE");
    }

    @Test
    void sendNotification_invalidMessageFormat() throws Exception {
        String message = "test@mail.ru:BadRequest";
        doThrow(new IllegalArgumentException("Неверный формат сообщения")).when(userNotificationService).sendNotification(message);
        mockMvc.perform(post("/notification")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(message))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Неверный формат сообщения"));
    }
}

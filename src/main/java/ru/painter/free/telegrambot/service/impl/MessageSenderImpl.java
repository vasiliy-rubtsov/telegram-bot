package ru.painter.free.telegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.painter.free.telegrambot.model.NotificationTask;
import ru.painter.free.telegrambot.repository.NotificationTaskRepository;
import ru.painter.free.telegrambot.service.MessageSender;

import java.util.List;

@Service
public class MessageSenderImpl implements MessageSender {
    private final TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;

    public MessageSenderImpl(
            TelegramBot telegramBot,
            NotificationTaskRepository notificationTaskRepository
    ) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Override
    public void send(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        telegramBot.execute(sendMessage);
    }

    private List<NotificationTask> getActuallyNotificationTasks() {
        return notificationTaskRepository.findItemsForSending();
    }

    @Override
    public void sendActuallyNotificationTasks() {
        // Выбираем все сообщения, не помеченные как оправленные, для которых наступило время отправки
        List<NotificationTask> notificationTasks = getActuallyNotificationTasks();
        if (notificationTasks.isEmpty()) {
            // Если таких сообщений нет, то выходим
            return;
        }

        // Иначе рассылаем сообщения по чатам
        for (NotificationTask notificationTask : notificationTasks) {
            send(notificationTask.getChatId(), notificationTask.getMsg());

            // Помечаем сообщение как отосланное и сохраняем изменения в БД
            notificationTask.setSended(true);
            notificationTaskRepository.save(notificationTask);
        }
    }
}

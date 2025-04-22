package ru.painter.free.telegrambot.service;

import ru.painter.free.telegrambot.model.NotificationTask;

import java.util.List;

public interface MessageSender {
    void send(Long chatId, String messageText);
    void sendActuallyNotificationTasks();
}

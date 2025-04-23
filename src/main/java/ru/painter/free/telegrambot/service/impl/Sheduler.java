package ru.painter.free.telegrambot.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.painter.free.telegrambot.service.MessageSender;
import org.slf4j.Logger;

@Service
public class Sheduler {
    private final Logger logger = LoggerFactory.getLogger(Sheduler.class);
    private final MessageSender messageSender;
    public Sheduler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        logger.info("Sheduler started");
        messageSender.sendActuallyNotificationTasks();
    }
}

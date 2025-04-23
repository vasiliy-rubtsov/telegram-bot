package ru.painter.free.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.painter.free.telegrambot.model.NotificationTask;
import ru.painter.free.telegrambot.repository.NotificationTaskRepository;
import ru.painter.free.telegrambot.service.MessageParser;
import ru.painter.free.telegrambot.service.impl.MessageParserImpl;
import ru.painter.free.telegrambot.service.MessageSender;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final MessageSender messageSender;
    private final MessageParser messageParser;
    private final NotificationTaskRepository notificationTaskRepository;
    
    @Value("${telegram.bot.helloMessage}")
    private String helloMessage;

    public TelegramBotUpdatesListener(
            TelegramBot telegramBot,
            MessageSender messageSender,
            MessageParser messageParser,
            NotificationTaskRepository notificationTaskRepository
    ) {
        this.telegramBot = telegramBot;
        this.messageSender = messageSender;
        this.messageParser = messageParser;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            // Ищем сообщение
            Message message = update.message();
            if (message == null) {
                // или отредактированное
                message = update.editedMessage();
            }

            // Если сообщение оказалось пустым
            if (message == null) {
                return;
            }

            // получаем текст сообщения и идентификатор чата
            String cmd = message.text();
            Long chatId = message.chat().id();

            // обработка команды /start - вывод приветственного сообщения
            if (cmd.equals("/start")) {
                messageSender.send(chatId, helloMessage);
                return;
            }

            // Обработка всех прочих команд
            messageParser.parse(cmd);
            if (messageParser.isMatched()) {
                // если комада по формату, то создаем и сохраняем в БД сущность NotificationTask
                NotificationTask notificationTask = new NotificationTask();
                notificationTask.setChatId(chatId);
                notificationTask.setDateSending(messageParser.getDate());
                notificationTask.setMsg(messageParser.getMsg());
                notificationTaskRepository.save(notificationTask);
                logger.info("Registered notification task: {}", notificationTask);
            } else {
                // в противном случае выводим пользователю в чат предупрежднеие об ошибке
                messageSender.send(chatId, "Error! Incorrect date or time format");
                logger.error("Error! Incorrect date or time format: {}", cmd);
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}

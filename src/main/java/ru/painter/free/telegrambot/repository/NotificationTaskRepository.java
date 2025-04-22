package ru.painter.free.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.painter.free.telegrambot.model.NotificationTask;

import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    // поиск сообщений для отправки: берутсявсе сообщения, не помеченные как отправленные, для которых время отправки уже наступило
    @Query(nativeQuery = true, value = "SELECT * FROM notification_task WHERE NOT sended AND date_sending <= LOCALTIMESTAMP")
    List<NotificationTask> findItemsForSending();

}

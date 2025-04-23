package ru.painter.free.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "date_sending")
    private LocalDateTime dateSending;

    private String msg;
    private boolean sended;

    public NotificationTask() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getDateSending() {
        return dateSending;
    }

    public void setDateSending(LocalDateTime dateSending) {
        this.dateSending = dateSending;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }
}

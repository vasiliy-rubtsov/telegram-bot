package ru.painter.free.telegrambot.service;

import java.time.LocalDateTime;

public interface MessageParser {
    void parse(String message);
    LocalDateTime getDate();
    String getMsg();
    boolean isMatched();
}

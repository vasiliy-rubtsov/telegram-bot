package ru.painter.free.telegrambot.service.impl;

import org.springframework.stereotype.Service;
import ru.painter.free.telegrambot.service.MessageParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MessageParserImpl implements MessageParser {
    private String date;
    private String time;
    private String msg;
    private boolean isMatched;
    private final Pattern pattern;

    public MessageParserImpl() {
        String regEx = "\\s*(\\d{2}\\.\\d{2}\\.\\d{4})\\s+(\\d{2}:\\d{2})\\s+(.+)";
        this.pattern = Pattern.compile(regEx);
        date = time = msg = "";
        isMatched = false;
    }

    @Override
    public void parse(String message) {
        Matcher matcher = pattern.matcher(message);
        date = time = msg = "";
        isMatched = false;
        if (matcher.matches()) {
            isMatched = true;
            date = matcher.group(1);
            time = matcher.group(2);
            msg = matcher.group(3);
        }
    }

    @Override
    public LocalDateTime getDate() {
        if (isMatched) {
            String dateTime = date + " " + time;
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        } else {
            return null;
        }
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public boolean isMatched() {
        return isMatched;
    }
}

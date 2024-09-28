package itstep.learning.data.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class LogsEntity {
    private int id;
    private String url;
    private LocalDateTime dateTime;

    public LogsEntity(String url, LocalDateTime dateTime) {
        this.url = url;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}

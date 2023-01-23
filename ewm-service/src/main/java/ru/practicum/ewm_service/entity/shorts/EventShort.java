package ru.practicum.ewm_service.entity.shorts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.practicum.ewm_service.entity.Category;

import java.sql.Timestamp;

public interface EventShort {

    String getAnnotation();

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Category getCategory();

    Long getConfirmedRequests();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp getEventDate();

    Long getId();

    UserShort getInitiator();

    boolean isPaid();

    String getTitle();

    Long getViews();




}

package com.example.Live.stream.service.dto;

import com.example.Live.stream.domain.enums.StreamEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamEventDTO {
    private String id;
    private StreamEventType eventType;
    private LocalDateTime eventTime;

    private String livestreamId;
    private String livestreamTitle;

    // Computed fields
    public String getFormattedEventTime() {
        return eventTime != null ?
                eventTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public String getEventTypeDisplay() {
        return eventType != null ? eventType.name() : null;
    }
}
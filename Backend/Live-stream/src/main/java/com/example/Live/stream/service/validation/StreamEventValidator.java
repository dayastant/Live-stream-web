package com.example.Live.stream.service.validation;

import com.example.Live.stream.domain.entity.livestream.StreamEvent;
import com.example.Live.stream.domain.enums.StreamEventType;
import com.example.Live.stream.service.dto.StreamEventDTO;
import org.springframework.stereotype.Component;

@Component
public class StreamEventValidator {

    public void validateForCreation(StreamEventDTO eventDTO) {
        if (eventDTO == null) {
            throw new IllegalArgumentException("Stream event data cannot be null");
        }

        if (eventDTO.getEventType() == null) {
            throw new IllegalArgumentException("Event type is required");
        }
    }

    public void validateEventSequence(StreamEvent lastEvent, StreamEventType newEventType) {
        if (lastEvent == null) {
            // First event must be STARTED
            if (newEventType != StreamEventType.STARTED) {
                throw new IllegalStateException("First event must be STARTED");
            }
            return;
        }

        switch (lastEvent.getEventType()) {
            case STARTED:
                if (newEventType != StreamEventType.STOPPED) {
                    throw new IllegalStateException(
                            "After STARTED, only STOPPED event is allowed"
                    );
                }
                break;

            case STOPPED:
                throw new IllegalStateException("No events allowed after STOPPED");

            default:
                throw new IllegalStateException("Invalid event state");
        }
    }

    public void validateEventTime(java.time.LocalDateTime eventTime) {
        if (eventTime == null) {
            throw new IllegalArgumentException("Event time is required");
        }

        if (eventTime.isAfter(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Event time cannot be in the future");
        }

        if (eventTime.isBefore(java.time.LocalDateTime.now().minusDays(1))) {
            throw new IllegalArgumentException("Event time cannot be more than 1 day in the past");
        }
    }
}
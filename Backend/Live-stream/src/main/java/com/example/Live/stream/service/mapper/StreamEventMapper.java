package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.livestream.StreamEvent;
import com.example.Live.stream.service.dto.StreamEventDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StreamEventMapper {

    public StreamEventDTO toDto(StreamEvent event) {
        if (event == null) return null;

        StreamEventDTO.StreamEventDTOBuilder builder = StreamEventDTO.builder()
                .id(event.getId())
                .eventType(event.getEventType())
                .eventTime(event.getEventTime());

        if (event.getLivestream() != null) {
            builder.livestreamId(event.getLivestream().getId())
                    .livestreamTitle(event.getLivestream().getTitle());
        }

        return builder.build();
    }

    public StreamEvent toEntity(StreamEventDTO dto) {
        if (dto == null) return null;

        StreamEvent event = new StreamEvent();
        event.setId(dto.getId());
        event.setEventType(dto.getEventType());
        event.setEventTime(dto.getEventTime() != null ? dto.getEventTime() : LocalDateTime.now());

        return event;
    }

    public void updateEntity(StreamEventDTO dto, StreamEvent event) {
        if (dto == null || event == null) return;

        if (dto.getEventType() != null) event.setEventType(dto.getEventType());
        if (dto.getEventTime() != null) event.setEventTime(dto.getEventTime());
    }

    public StreamEventDTO toSimpleDto(StreamEvent event) {
        if (event == null) return null;

        return StreamEventDTO.builder()
                .id(event.getId())
                .eventType(event.getEventType())
                .eventTime(event.getEventTime())
                .build();
    }
}
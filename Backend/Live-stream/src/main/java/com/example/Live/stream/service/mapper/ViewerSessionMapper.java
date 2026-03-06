package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.viewer.ViewerSession;
import com.example.Live.stream.service.dto.ViewerSessionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ViewerSessionMapper {

    private final LivestreamAccessMapper livestreamAccessMapper;

    public ViewerSessionDTO toDto(ViewerSession session) {
        if (session == null) return null;

        ViewerSessionDTO.ViewerSessionDTOBuilder builder = ViewerSessionDTO.builder()
                .id(session.getId())
                .sessionToken(session.getSessionToken())
                .ipAddress(session.getIpAddress())
                .deviceInfo(session.getDeviceInfo())
                .startedAt(session.getStartedAt())
                .endedAt(session.getEndedAt());

        if (session.getAccesses() != null && !session.getAccesses().isEmpty()) {
            builder.accesses(session.getAccesses().stream()
                    .map(livestreamAccessMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }

    public ViewerSession toEntity(ViewerSessionDTO dto) {
        if (dto == null) return null;

        ViewerSession session = new ViewerSession();
        session.setId(dto.getId());
        session.setSessionToken(dto.getSessionToken() != null ?
                dto.getSessionToken() : generateSessionToken());
        session.setIpAddress(dto.getIpAddress());
        session.setDeviceInfo(dto.getDeviceInfo());
        session.setStartedAt(dto.getStartedAt() != null ? dto.getStartedAt() : LocalDateTime.now());
        session.setEndedAt(dto.getEndedAt());

        return session;
    }

    public void updateEntity(ViewerSessionDTO dto, ViewerSession session) {
        if (dto == null || session == null) return;

        if (dto.getIpAddress() != null) session.setIpAddress(dto.getIpAddress());
        if (dto.getDeviceInfo() != null) session.setDeviceInfo(dto.getDeviceInfo());
        if (dto.getEndedAt() != null) session.setEndedAt(dto.getEndedAt());
    }

    public ViewerSessionDTO toSimpleDto(ViewerSession session) {
        if (session == null) return null;

        return ViewerSessionDTO.builder()
                .id(session.getId())
                .sessionToken(maskToken(session.getSessionToken()))
                .deviceInfo(session.getDeviceInfo())
                .startedAt(session.getStartedAt())
                .endedAt(session.getEndedAt())
                .build();
    }

    private String generateSessionToken() {
        return "SESS-" + UUID.randomUUID().toString();
    }

    private String maskToken(String token) {
        if (token == null || token.length() < 10) return "****";
        return token.substring(0, 6) + "****";
    }
}
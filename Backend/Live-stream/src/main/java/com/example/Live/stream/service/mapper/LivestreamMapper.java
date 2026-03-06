package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.livestream.Livestream;
import com.example.Live.stream.domain.enums.LivestreamStatus;
import com.example.Live.stream.service.dto.LivestreamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LivestreamMapper {

    private final StreamConfigMapper streamConfigMapper;
    private final StreamEventMapper streamEventMapper;
    private final VideoMapper videoMapper;
    private final AnalyticsMapper analyticsMapper;

    public LivestreamDTO toDto(Livestream livestream) {
        if (livestream == null) return null;

        LivestreamDTO.LivestreamDTOBuilder builder = LivestreamDTO.builder()
                .id(livestream.getId())
                .title(livestream.getTitle())
                .description(livestream.getDescription())
                .status(livestream.getStatus())
                .scheduledAt(livestream.getScheduledAt())
                .startedAt(livestream.getStartedAt())
                .endedAt(livestream.getEndedAt())
                .maxViewers(livestream.getMaxViewers())
                .currentViewers(livestream.getAccesses() != null ?
                        (int) livestream.getAccesses().stream()
                                .filter(a -> a.getLeftAt() == null)
                                .count() : 0);

        if (livestream.getAdmin() != null) {
            builder.adminId(livestream.getAdmin().getId())
                    .adminUsername(livestream.getAdmin().getUsername());
        }

        if (livestream.getStreamConfigs() != null && !livestream.getStreamConfigs().isEmpty()) {
            builder.streamConfigs(livestream.getStreamConfigs().stream()
                    .map(streamConfigMapper::toDto)
                    .collect(Collectors.toList()));
        }

        if (livestream.getStreamEvents() != null && !livestream.getStreamEvents().isEmpty()) {
            builder.streamEvents(livestream.getStreamEvents().stream()
                    .map(streamEventMapper::toDto)
                    .collect(Collectors.toList()));
        }

        if (livestream.getVideos() != null && !livestream.getVideos().isEmpty()) {
            builder.videos(livestream.getVideos().stream()
                    .map(videoMapper::toDto)
                    .collect(Collectors.toList()));
        }

        if (livestream.getAnalytics() != null && !livestream.getAnalytics().isEmpty()) {
            builder.analytics(livestream.getAnalytics().stream()
                    .map(analyticsMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }

    public Livestream toEntity(LivestreamDTO dto) {
        if (dto == null) return null;

        Livestream livestream = new Livestream();
        livestream.setId(dto.getId());
        livestream.setTitle(dto.getTitle());
        livestream.setDescription(dto.getDescription());
        livestream.setStatus(dto.getStatus() != null ? dto.getStatus() : LivestreamStatus.SCHEDULED);
        livestream.setScheduledAt(dto.getScheduledAt());
        livestream.setStartedAt(dto.getStartedAt());
        livestream.setEndedAt(dto.getEndedAt());
        livestream.setMaxViewers(dto.getMaxViewers());

        return livestream;
    }

    public void updateEntity(LivestreamDTO dto, Livestream livestream) {
        if (dto == null || livestream == null) return;

        if (dto.getTitle() != null) livestream.setTitle(dto.getTitle());
        if (dto.getDescription() != null) livestream.setDescription(dto.getDescription());
        if (dto.getStatus() != null) livestream.setStatus(dto.getStatus());
        if (dto.getScheduledAt() != null) livestream.setScheduledAt(dto.getScheduledAt());
        if (dto.getStartedAt() != null) livestream.setStartedAt(dto.getStartedAt());
        if (dto.getEndedAt() != null) livestream.setEndedAt(dto.getEndedAt());
        if (dto.getMaxViewers() != null) livestream.setMaxViewers(dto.getMaxViewers());
    }

    public LivestreamDTO toSimpleDto(Livestream livestream) {
        if (livestream == null) return null;

        return LivestreamDTO.builder()
                .id(livestream.getId())
                .title(livestream.getTitle())
                .status(livestream.getStatus())
                .adminUsername(livestream.getAdmin() != null ?
                        livestream.getAdmin().getUsername() : null)
                .build();
    }
}
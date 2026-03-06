package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.viewer.LivestreamAccess;
import com.example.Live.stream.service.dto.LivestreamAccessDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LivestreamAccessMapper {

    public LivestreamAccessDTO toDto(LivestreamAccess access) {
        if (access == null) return null;

        LivestreamAccessDTO.LivestreamAccessDTOBuilder builder = LivestreamAccessDTO.builder()
                .id(access.getId())
                .joinedAt(access.getJoinedAt())
                .leftAt(access.getLeftAt());

        if (access.getLivestream() != null) {
            builder.livestreamId(access.getLivestream().getId())
                    .livestreamTitle(access.getLivestream().getTitle());
        }

        if (access.getViewerSession() != null) {
            builder.viewerSessionId(access.getViewerSession().getId());
        }

        return builder.build();
    }

    public LivestreamAccess toEntity(LivestreamAccessDTO dto) {
        if (dto == null) return null;

        LivestreamAccess access = new LivestreamAccess();
        access.setId(dto.getId());
        access.setJoinedAt(dto.getJoinedAt() != null ? dto.getJoinedAt() : LocalDateTime.now());
        access.setLeftAt(dto.getLeftAt());

        return access;
    }

    public void updateEntity(LivestreamAccessDTO dto, LivestreamAccess access) {
        if (dto == null || access == null) return;

        if (dto.getLeftAt() != null) access.setLeftAt(dto.getLeftAt());
    }

    public LivestreamAccessDTO toActiveDto(LivestreamAccess access) {
        if (access == null) return null;

        return LivestreamAccessDTO.builder()
                .id(access.getId())
                .livestreamId(access.getLivestream() != null ?
                        access.getLivestream().getId() : null)
                .joinedAt(access.getJoinedAt())
                .build();
    }

    public LivestreamAccessDTO toHistoryDto(LivestreamAccess access) {
        if (access == null) return null;

        return LivestreamAccessDTO.builder()
                .id(access.getId())
                .livestreamTitle(access.getLivestream() != null ?
                        access.getLivestream().getTitle() : null)
                .joinedAt(access.getJoinedAt())
                .leftAt(access.getLeftAt())
                .build();
    }
}
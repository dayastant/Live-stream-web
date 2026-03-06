package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.livestream.StreamConfig;
import com.example.Live.stream.service.dto.StreamConfigDTO;
import org.springframework.stereotype.Component;

@Component
public class StreamConfigMapper {

    public StreamConfigDTO toDto(StreamConfig config) {
        if (config == null) return null;

        StreamConfigDTO.StreamConfigDTOBuilder builder = StreamConfigDTO.builder()
                .id(config.getId())
                .rtmpUrl(config.getRtmpUrl())
                .streamKey(config.getStreamKey())
                .hlsUrl(config.getHlsUrl())
                .bitrate(config.getBitrate())
                .resolution(config.getResolution())
                .createdAt(config.getCreatedAt());

        if (config.getLivestream() != null) {
            builder.livestreamId(config.getLivestream().getId())
                    .livestreamTitle(config.getLivestream().getTitle());
        }

        return builder.build();
    }

    public StreamConfig toEntity(StreamConfigDTO dto) {
        if (dto == null) return null;

        StreamConfig config = new StreamConfig();
        config.setId(dto.getId());
        config.setRtmpUrl(dto.getRtmpUrl());
        config.setStreamKey(dto.getStreamKey());
        config.setHlsUrl(dto.getHlsUrl());
        config.setBitrate(dto.getBitrate());
        config.setResolution(dto.getResolution());
        config.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());

        return config;
    }

    public void updateEntity(StreamConfigDTO dto, StreamConfig config) {
        if (dto == null || config == null) return;

        if (dto.getRtmpUrl() != null) config.setRtmpUrl(dto.getRtmpUrl());
        if (dto.getStreamKey() != null) config.setStreamKey(dto.getStreamKey());
        if (dto.getHlsUrl() != null) config.setHlsUrl(dto.getHlsUrl());
        if (dto.getBitrate() != null) config.setBitrate(dto.getBitrate());
        if (dto.getResolution() != null) config.setResolution(dto.getResolution());
    }

    public StreamConfigDTO toDtoWithMaskedKey(StreamConfig config) {
        if (config == null) return null;

        String maskedKey = maskStreamKey(config.getStreamKey());

        return StreamConfigDTO.builder()
                .id(config.getId())
                .rtmpUrl(config.getRtmpUrl())
                .streamKey(maskedKey)
                .hlsUrl(config.getHlsUrl())
                .bitrate(config.getBitrate())
                .resolution(config.getResolution())
                .createdAt(config.getCreatedAt())
                .livestreamId(config.getLivestream() != null ?
                        config.getLivestream().getId() : null)
                .build();
    }

    private String maskStreamKey(String streamKey) {
        if (streamKey == null || streamKey.length() < 8) return "****";
        return streamKey.substring(0, 4) + "****" +
                streamKey.substring(streamKey.length() - 4);
    }
}
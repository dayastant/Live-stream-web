package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.livestream.Analytics;
import com.example.Live.stream.service.dto.AnalyticsDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AnalyticsMapper {

    public AnalyticsDTO toDto(Analytics analytics) {
        if (analytics == null) return null;

        AnalyticsDTO.AnalyticsDTOBuilder builder = AnalyticsDTO.builder()
                .id(analytics.getId())
                .concurrentViewers(analytics.getConcurrentViewers())
                .peakViewers(analytics.getPeakViewers())
                .recordedAt(analytics.getRecordedAt());

        if (analytics.getLivestream() != null) {
            builder.livestreamId(analytics.getLivestream().getId())
                    .livestreamTitle(analytics.getLivestream().getTitle());
        }

        return builder.build();
    }

    public Analytics toEntity(AnalyticsDTO dto) {
        if (dto == null) return null;

        Analytics analytics = new Analytics();
        analytics.setId(dto.getId());
        analytics.setConcurrentViewers(dto.getConcurrentViewers());
        analytics.setPeakViewers(dto.getPeakViewers());
        analytics.setRecordedAt(dto.getRecordedAt() != null ? dto.getRecordedAt() : LocalDateTime.now());

        return analytics;
    }

    public void updateEntity(AnalyticsDTO dto, Analytics analytics) {
        if (dto == null || analytics == null) return;

        if (dto.getConcurrentViewers() != null) analytics.setConcurrentViewers(dto.getConcurrentViewers());
        if (dto.getPeakViewers() != null) analytics.setPeakViewers(dto.getPeakViewers());
    }

    public AnalyticsDTO toSummaryDto(Analytics analytics) {
        if (analytics == null) return null;

        return AnalyticsDTO.builder()
                .id(analytics.getId())
                .concurrentViewers(analytics.getConcurrentViewers())
                .peakViewers(analytics.getPeakViewers())
                .recordedAt(analytics.getRecordedAt())
                .build();
    }
}
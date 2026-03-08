package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.livestream.Video;
import com.example.Live.stream.service.dto.VideoDTO;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class VideoMapper {

    public VideoDTO toDto(Video video) {
        if (video == null) return null;

        VideoDTO.Builder builder = VideoDTO.builder()
                .id(video.getId())
                .title(video.getTitle())
                .videoUrl(video.getVideoUrl())
                .duration(video.getDuration())
                .uploadedAt(video.getUploadedAt())
                .fileSize(video.getFileSize())
                .thumbnailUrl(video.getThumbnailUrl())
                .videoFormat(video.getVideoFormat())
                .resolution(video.getResolution());

        if (video.getLivestream() != null) {
            builder.livestreamId(video.getLivestream().getId())
                    .livestreamTitle(video.getLivestream().getTitle());
        }

        return builder.build();
    }

    public Video toEntity(VideoDTO dto) {
        if (dto == null) return null;

        Video video = new Video();
        video.setId(dto.getId());
        video.setTitle(dto.getTitle());
        video.setVideoUrl(dto.getVideoUrl());
        video.setDuration(dto.getDuration());
        video.setUploadedAt(dto.getUploadedAt() != null ? dto.getUploadedAt() : LocalDateTime.now());
        video.setFileSize(dto.getFileSize());
        video.setThumbnailUrl(dto.getThumbnailUrl());
        video.setVideoFormat(dto.getVideoFormat());
        video.setResolution(dto.getResolution());

        return video;
    }

    public void updateEntity(VideoDTO dto, Video video) {
        if (dto == null || video == null) return;

        if (dto.getTitle() != null) video.setTitle(dto.getTitle());
        if (dto.getVideoUrl() != null) video.setVideoUrl(dto.getVideoUrl());
        if (dto.getDuration() != null) video.setDuration(dto.getDuration());
        if (dto.getFileSize() != null) video.setFileSize(dto.getFileSize());
        if (dto.getThumbnailUrl() != null) video.setThumbnailUrl(dto.getThumbnailUrl());
        if (dto.getVideoFormat() != null) video.setVideoFormat(dto.getVideoFormat());
        if (dto.getResolution() != null) video.setResolution(dto.getResolution());
    }

    public VideoDTO toSimpleDto(Video video) {
        if (video == null) return null;

        return VideoDTO.builder()
                .id(video.getId())
                .title(video.getTitle())
                .duration(video.getDuration())
                .thumbnailUrl(video.getThumbnailUrl())
                .build();
    }
}
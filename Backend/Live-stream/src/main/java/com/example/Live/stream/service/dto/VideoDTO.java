package com.example.Live.stream.service.dto;

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
public class VideoDTO {
    private String id;
    private String title;
    private String videoUrl;
    private Integer duration;
    private LocalDateTime uploadedAt;
    private Long fileSize;
    private String thumbnailUrl;
    private String videoFormat;
    private String resolution;

    private String livestreamId;
    private String livestreamTitle;

    // Computed fields
    public String getFormattedDuration() {
        if (duration == null) return "00:00";
        int hours = duration / 3600;
        int minutes = (duration % 3600) / 60;
        int seconds = duration % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }

    public String getFileSizeInMB() {
        if (fileSize == null) return "0 MB";
        double mb = fileSize / (1024.0 * 1024.0);
        return String.format("%.2f MB", mb);
    }

    public String getUploadedAtFormatted() {
        return uploadedAt != null ?
                uploadedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}
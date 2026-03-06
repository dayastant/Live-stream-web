package com.example.Live.stream.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamConfigDTO {
    private String id;
    private String rtmpUrl;
    private String streamKey;
    private String hlsUrl;
    private Integer bitrate;
    private String resolution;
    private LocalDateTime createdAt;

    private String livestreamId;
    private String livestreamTitle;

    // Computed fields
    public String getFullRtmpUrl() {
        return rtmpUrl + "/" + streamKey;
    }

    public String getBitrateDisplay() {
        return bitrate != null ? bitrate + " kbps" : null;
    }
}
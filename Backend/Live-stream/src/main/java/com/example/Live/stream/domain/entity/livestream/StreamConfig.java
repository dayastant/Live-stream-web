package com.example.Live.stream.domain.entity.livestream;


import com.example.Live.stream.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "stream_configs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = "livestream")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class StreamConfig extends BaseEntity {

    @Column(name = "rtmp_url", nullable = false)
    private String rtmpUrl;

    @Column(name = "stream_key", nullable = false)
    private String streamKey;

    @Column(name = "hls_url")
    private String hlsUrl;

    private Integer bitrate;

    private String resolution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id", nullable = false)
    private Livestream livestream;

    // Business methods
    public String getFullRtmpUrl() {
        return rtmpUrl + "/" + streamKey;
    }

    public void updateStreamConfig(String rtmpUrl, String streamKey, String hlsUrl) {
        this.rtmpUrl = rtmpUrl;
        this.streamKey = streamKey;
        this.hlsUrl = hlsUrl;
    }
}
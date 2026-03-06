package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.livestream.StreamConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StreamConfigRepository extends JpaRepository<StreamConfig, String> {

    Optional<StreamConfig> findByLivestreamId(String livestreamId);

    @Query("SELECT sc FROM StreamConfig sc WHERE sc.streamKey = :streamKey")
    Optional<StreamConfig> findByStreamKey(@Param("streamKey") String streamKey);

    List<StreamConfig> findByBitrateGreaterThan(Integer bitrate);

    List<StreamConfig> findByResolution(String resolution);

    @Query("SELECT sc.livestream.id, sc FROM StreamConfig sc")
    List<Object[]> findAllWithLivestreamId();

    @Query("SELECT COUNT(sc) FROM StreamConfig sc WHERE sc.bitrate > :bitrate")
    long countHighBitrateStreams(@Param("bitrate") Integer bitrate);

    boolean existsByStreamKey(String streamKey);

    @Query("SELECT sc.resolution, COUNT(sc) FROM StreamConfig sc GROUP BY sc.resolution")
    List<Object[]> countByResolution();
}
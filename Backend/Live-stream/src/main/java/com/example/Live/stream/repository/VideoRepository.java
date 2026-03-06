package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.livestream.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    // Basic queries
    List<Video> findByLivestreamId(String livestreamId);

    List<Video> findByUploadedAtAfter(LocalDateTime date);

    Page<Video> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<Video> findByDurationGreaterThan(Integer seconds);

    List<Video> findByDurationLessThan(Integer seconds);

    // Join queries
    @Query("SELECT v FROM Video v JOIN FETCH v.livestream WHERE v.id = :id")
    Video findVideoWithLivestream(@Param("id") String id);

    // Statistics queries
    @Query("SELECT v.livestream.id, COUNT(v) FROM Video v GROUP BY v.livestream.id")
    List<Object[]> countVideosPerLivestream();

    @Query("SELECT AVG(v.duration) FROM Video v WHERE v.livestream.id = :livestreamId")
    Double getAverageVideoDuration(@Param("livestreamId") String livestreamId);

    @Query("SELECT SUM(v.duration) FROM Video v WHERE v.livestream.id = :livestreamId")
    Long getTotalVideoDuration(@Param("livestreamId") String livestreamId);

    // Recent videos
    @Query("SELECT v FROM Video v ORDER BY v.uploadedAt DESC")
    List<Video> findRecentVideos(Pageable pageable);

    // Batch queries
    @Query("SELECT v FROM Video v WHERE v.livestream.id IN :livestreamIds")
    List<Video> findByLivestreamIds(@Param("livestreamIds") List<String> livestreamIds);

    // Existence checks
    boolean existsByVideoUrl(String videoUrl);

    // Count queries
    long countByLivestreamId(String livestreamId);
}
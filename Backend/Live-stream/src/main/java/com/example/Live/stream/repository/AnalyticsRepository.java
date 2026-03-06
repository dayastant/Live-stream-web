package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.livestream.Analytics;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, String> {

    List<Analytics> findByLivestreamIdOrderByRecordedAtDesc(String livestreamId);

    List<Analytics> findByRecordedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Analytics a WHERE a.livestream.id = :livestreamId AND a.recordedAt BETWEEN :start AND :end")
    List<Analytics> findAnalyticsForLivestreamInTimeRange(@Param("livestreamId") String livestreamId,
                                                          @Param("start") LocalDateTime start,
                                                          @Param("end") LocalDateTime end);

    @Query("SELECT MAX(a.concurrentViewers) FROM Analytics a WHERE a.livestream.id = :livestreamId")
    Integer getPeakViewersForLivestream(@Param("livestreamId") String livestreamId);

    @Query("SELECT AVG(a.concurrentViewers) FROM Analytics a WHERE a.livestream.id = :livestreamId")
    Double getAverageViewersForLivestream(@Param("livestreamId") String livestreamId);

    @Query("SELECT a FROM Analytics a WHERE a.concurrentViewers > :threshold")
    List<Analytics> findHighTrafficPeriods(@Param("threshold") Integer threshold);

    @Query("SELECT a.livestream.id, MAX(a.concurrentViewers) FROM Analytics a GROUP BY a.livestream.id")
    List<Object[]> findPeakViewersForAllLivestreams();

    @Query("SELECT a FROM Analytics a ORDER BY a.concurrentViewers DESC")
    List<Analytics> findTopViewedMoments(Pageable pageable);

    @Query("SELECT HOUR(a.recordedAt), AVG(a.concurrentViewers) FROM Analytics a " +
            "WHERE a.livestream.id = :livestreamId GROUP BY HOUR(a.recordedAt)")
    List<Object[]> getHourlyViewerAverages(@Param("livestreamId") String livestreamId);
}
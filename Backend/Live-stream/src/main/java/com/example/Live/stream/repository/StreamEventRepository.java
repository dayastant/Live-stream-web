package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.livestream.StreamEvent;
import com.example.Live.stream.domain.enums.StreamEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StreamEventRepository extends JpaRepository<StreamEvent, String> {

    List<StreamEvent> findByLivestreamIdOrderByEventTimeDesc(String livestreamId);

    List<StreamEvent> findByEventType(StreamEventType eventType);

    List<StreamEvent> findByEventTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT se FROM StreamEvent se WHERE se.livestream.id = :livestreamId ORDER BY se.eventTime DESC")
    List<StreamEvent> findLatestEvent(@Param("livestreamId") String livestreamId);

    @Query("SELECT COUNT(se) FROM StreamEvent se WHERE se.livestream.id = :livestreamId AND se.eventType = :eventType")
    long countEventsByType(@Param("livestreamId") String livestreamId, @Param("eventType") StreamEventType eventType);

    @Query("SELECT se.eventTime, se.eventType FROM StreamEvent se WHERE se.livestream.id = :livestreamId ORDER BY se.eventTime")
    List<Object[]> getEventTimeline(@Param("livestreamId") String livestreamId);

    @Query("SELECT se.livestream.id, COUNT(se) FROM StreamEvent se GROUP BY se.livestream.id")
    List<Object[]> countEventsByLivestream();

    @Query("SELECT se FROM StreamEvent se WHERE se.eventTime > :time AND se.eventType = :type")
    List<StreamEvent> findRecentEventsByType(@Param("time") LocalDateTime time,
                                             @Param("type") StreamEventType type);

    @Query("SELECT DATE(se.eventTime), COUNT(se) FROM StreamEvent se " +
            "WHERE se.eventType = :type GROUP BY DATE(se.eventTime)")
    List<Object[]> getDailyEventStats(@Param("type") StreamEventType type);
}
package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.viewer.ViewerSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ViewerSessionRepository extends JpaRepository<ViewerSession, String> {

    // Basic queries
    Optional<ViewerSession> findBySessionToken(String sessionToken);

    List<ViewerSession> findByEndedAtIsNull();

    List<ViewerSession> findByStartedAtAfter(LocalDateTime date);

    // Active sessions
    @Query("SELECT vs FROM ViewerSession vs WHERE vs.endedAt IS NULL AND vs.startedAt < :timeout")
    List<ViewerSession> findActiveSessionsOlderThan(@Param("timeout") LocalDateTime timeout);

    @Query("SELECT COUNT(vs) FROM ViewerSession vs WHERE vs.endedAt IS NULL")
    long countActiveSessions();

    // IP-based queries
    @Query("SELECT vs FROM ViewerSession vs WHERE vs.ipAddress = :ipAddress ORDER BY vs.startedAt DESC")
    List<ViewerSession> findByIpAddress(@Param("ipAddress") String ipAddress);

    // Statistics queries
    @Query("SELECT vs.deviceInfo, COUNT(vs) FROM ViewerSession vs GROUP BY vs.deviceInfo")
    List<Object[]> countByDeviceType();

    @Query("SELECT HOUR(vs.startedAt), COUNT(vs) FROM ViewerSession vs " +
            "WHERE vs.startedAt BETWEEN :start AND :end GROUP BY HOUR(vs.startedAt)")
    List<Object[]> getHourlySessionStats(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    @Query("SELECT DATE(vs.startedAt), COUNT(vs) FROM ViewerSession vs " +
            "WHERE vs.startedAt BETWEEN :start AND :end GROUP BY DATE(vs.startedAt)")
    List<Object[]> getDailySessionStats(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

    // Recent sessions
    @Query("SELECT vs FROM ViewerSession vs ORDER BY vs.startedAt DESC")
    List<ViewerSession> findRecentSessions(Pageable pageable);

    // Update queries
    @Modifying
    @Query("UPDATE ViewerSession vs SET vs.endedAt = :endedAt WHERE vs.sessionToken = :sessionToken")
    int endSession(@Param("sessionToken") String sessionToken, @Param("endedAt") LocalDateTime endedAt);
}
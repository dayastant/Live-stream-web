package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.viewer.LivestreamAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LivestreamAccessRepository extends JpaRepository<LivestreamAccess, String> {

    // --- Basic queries ---
    List<LivestreamAccess> findByLivestreamId(String livestreamId);

    List<LivestreamAccess> findByViewerSessionId(String viewerSessionId);

    // --- Current viewers ---
    @Query("SELECT la FROM LivestreamAccess la WHERE la.livestream.id = :livestreamId AND la.leftAt IS NULL")
    List<LivestreamAccess> findCurrentViewers(@Param("livestreamId") String livestreamId);

    @Query("SELECT COUNT(la) FROM LivestreamAccess la WHERE la.livestream.id = :livestreamId AND la.leftAt IS NULL")
    long countCurrentViewers(@Param("livestreamId") String livestreamId);

    // --- Time-based queries ---
    @Query("SELECT la FROM LivestreamAccess la WHERE la.joinedAt BETWEEN :start AND :end")
    List<LivestreamAccess> findByJoinTimeBetween(@Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end);

    @Query("SELECT la FROM LivestreamAccess la WHERE la.leftAt BETWEEN :start AND :end")
    List<LivestreamAccess> findByLeaveTimeBetween(@Param("start") LocalDateTime start,
                                                  @Param("end") LocalDateTime end);

    // --- Statistics queries ---
    @Query("SELECT la.livestream.id, COUNT(la) FROM LivestreamAccess la " +
            "WHERE la.joinedAt BETWEEN :start AND :end GROUP BY la.livestream.id")
    List<Object[]> countAccessesByLivestream(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    // --- Corrected average watch duration ---
    @Query(value = "SELECT AVG(TIMESTAMPDIFF(SECOND, joined_at, COALESCE(left_at, :now))) " +
            "FROM livestream_accesses " +
            "WHERE livestream_id = :livestreamId", nativeQuery = true)
    Double getAverageWatchDuration(@Param("livestreamId") String livestreamId,
                                   @Param("now") LocalDateTime now);

    @Query("SELECT la FROM LivestreamAccess la WHERE la.leftAt IS NULL AND la.joinedAt < :time")
    List<LivestreamAccess> findLongRunningSessions(@Param("time") LocalDateTime time);

    // --- Viewer analytics ---
    @Query("SELECT la.viewerSession.id, COUNT(la) FROM LivestreamAccess la " +
            "GROUP BY la.viewerSession.id HAVING COUNT(la) > :threshold")
    List<Object[]> findFrequentViewers(@Param("threshold") long threshold);

    @Query("SELECT la.livestream.id, COUNT(DISTINCT la.viewerSession.id) " +
            "FROM LivestreamAccess la GROUP BY la.livestream.id")
    List<Object[]> countUniqueViewersPerLivestream();

    // --- Peak viewers (native query to fix JPQL limitation) ---
    @Query(value = "SELECT livestream_id, MAX(cnt) AS peak_count FROM (" +
            "   SELECT livestream_id, HOUR(joined_at) AS hr, COUNT(*) AS cnt " +
            "   FROM livestream_accesses " +
            "   WHERE joined_at BETWEEN :start AND :end " +
            "   GROUP BY livestream_id, HOUR(joined_at)" +
            ") AS hourly " +
            "GROUP BY livestream_id", nativeQuery = true)
    List<Object[]> findPeakViewingHours(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);
}
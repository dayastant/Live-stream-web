package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.livestream.Livestream;
import com.example.Live.stream.domain.enums.LivestreamStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LivestreamRepository extends JpaRepository<Livestream, String> {

    // Basic queries
    List<Livestream> findByStatus(LivestreamStatus status);

    @Query("SELECT l FROM Livestream l WHERE l.status = 'LIVE'")
    List<Livestream> findAllLiveStreams();

    Page<Livestream> findByAdminId(String adminId, Pageable pageable);

    List<Livestream> findByScheduledAtBetween(LocalDateTime start, LocalDateTime end);

    // Search queries
    @Query("SELECT l FROM Livestream l WHERE " +
            "(:status IS NULL OR l.status = :status) AND " +
            "(:title IS NULL OR l.title LIKE %:title%) AND " +
            "(:adminId IS NULL OR l.admin.id = :adminId)")
    Page<Livestream> searchStreams(@Param("status") LivestreamStatus status,
                                   @Param("title") String title,
                                   @Param("adminId") String adminId,
                                   Pageable pageable);

    // Statistics queries
    @Query("SELECT COUNT(l) FROM Livestream l WHERE l.status = 'LIVE'")
    long countActiveStreams();

    @Query("SELECT l.status, COUNT(l) FROM Livestream l GROUP BY l.status")
    List<Object[]> countByStatus();

    @Query("SELECT l FROM Livestream l WHERE l.startedAt BETWEEN :start AND :end")
    List<Livestream> findStreamsStartedBetween(@Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end);

    @Query("SELECT l.admin.id, COUNT(l) FROM Livestream l GROUP BY l.admin.id")
    List<Object[]> countByAdmin();

    @Query("SELECT l FROM Livestream l ORDER BY SIZE(l.accesses) DESC")
    List<Livestream> findTopStreamsByViewers(Pageable pageable);

    // Update queries
    @Modifying
    @Query("UPDATE Livestream l SET l.status = :status WHERE l.id = :id")
    int updateStreamStatus(@Param("id") String id, @Param("status") LivestreamStatus status);

    @Modifying
    @Query("UPDATE Livestream l SET l.endedAt = :endedAt WHERE l.id = :id")
    int endLivestream(@Param("id") String id, @Param("endedAt") LocalDateTime endedAt);
}
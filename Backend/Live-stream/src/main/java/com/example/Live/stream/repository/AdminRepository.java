package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.admin.Admin;
import com.example.Live.stream.domain.enums.AdminRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    // Basic queries
    Optional<Admin> findByUsername(String username);

    boolean existsByUsername(String username);

    List<Admin> findByRole(AdminRole role);

    List<Admin> findByIsActiveTrue();

    // Custom queries
    @Query("SELECT a FROM Admin a WHERE a.lastLogin < :date OR a.lastLogin IS NULL")
    List<Admin> findInactiveAdminsSince(@Param("date") LocalDateTime date);

    @Query("SELECT a FROM Admin a WHERE " +
            "(:username IS NULL OR a.username LIKE %:username%) AND " +
            "(:role IS NULL OR a.role = :role) AND " +
            "(:isActive IS NULL OR a.isActive = :isActive)")
    Page<Admin> searchAdmins(@Param("username") String username,
                             @Param("role") AdminRole role,
                             @Param("isActive") Boolean isActive,
                             Pageable pageable);

    // Statistics queries
    @Query("SELECT a.role, COUNT(a) FROM Admin a GROUP BY a.role")
    List<Object[]> countByRole();

    @Query("SELECT a, COUNT(l) as streamCount FROM Admin a LEFT JOIN a.livestreams l " +
            "GROUP BY a ORDER BY streamCount DESC")
    List<Object[]> findTopAdminsByLivestreamCount(Pageable pageable);

    @Query("SELECT COUNT(a) FROM Admin a WHERE a.createdAt BETWEEN :start AND :end")
    long countNewAdminsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
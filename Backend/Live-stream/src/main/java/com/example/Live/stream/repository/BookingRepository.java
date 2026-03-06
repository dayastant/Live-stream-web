package com.example.Live.stream.repository;

import com.example.Live.stream.domain.entity.booking.Booking;
import com.example.Live.stream.domain.enums.BookingStatus;
import com.example.Live.stream.domain.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    // Basic queries
    List<Booking> findByBookingStatus(BookingStatus status);

    List<Booking> findByPaymentStatus(PaymentStatus status);

    List<Booking> findByEventDateBetween(LocalDateTime start, LocalDateTime end);

    List<Booking> findByEmail(String email);

    List<Booking> findByPhone(String phone);

    // Date-based queries
    @Query("SELECT b FROM Booking b WHERE b.eventDate > :now AND b.bookingStatus = 'CONFIRMED' ORDER BY b.eventDate")
    List<Booking> findUpcomingBookings(@Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.paymentStatus = 'UNPAID' AND b.createdAt < :deadline")
    List<Booking> findUnpaidBookingsOlderThan(@Param("deadline") LocalDateTime deadline);

    // Advanced search
    @Query("SELECT b FROM Booking b WHERE " +
            "(:name IS NULL OR b.name LIKE %:name%) AND " +
            "(:email IS NULL OR b.email = :email) AND " +
            "(:phone IS NULL OR b.phone = :phone) AND " +
            "(:bookingStatus IS NULL OR b.bookingStatus = :bookingStatus) AND " +
            "(:paymentStatus IS NULL OR b.paymentStatus = :paymentStatus) AND " +
            "(:fromDate IS NULL OR b.eventDate >= :fromDate) AND " +
            "(:toDate IS NULL OR b.eventDate <= :toDate)")
    Page<Booking> searchBookings(@Param("name") String name,
                                 @Param("email") String email,
                                 @Param("phone") String phone,
                                 @Param("bookingStatus") BookingStatus bookingStatus,
                                 @Param("paymentStatus") PaymentStatus paymentStatus,
                                 @Param("fromDate") LocalDateTime fromDate,
                                 @Param("toDate") LocalDateTime toDate,
                                 Pageable pageable);

    // Financial queries
    @Query("SELECT SUM(b.price) FROM Booking b WHERE b.paymentStatus = 'PAID' AND b.eventDate BETWEEN :start AND :end")
    BigDecimal getTotalRevenueBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT SUM(b.price) FROM Booking b WHERE b.paymentStatus = 'PAID'")
    BigDecimal getTotalRevenue();

    @Query("SELECT AVG(b.price) FROM Booking b WHERE b.bookingStatus = 'CONFIRMED'")
    BigDecimal getAverageBookingValue();

    // Statistics queries
    @Query("SELECT b.bookingStatus, COUNT(b) FROM Booking b GROUP BY b.bookingStatus")
    List<Object[]> countByBookingStatus();

    @Query("SELECT b.paymentStatus, COUNT(b) FROM Booking b GROUP BY b.paymentStatus")
    List<Object[]> countByPaymentStatus();

    @Query("SELECT YEAR(b.eventDate) as year, MONTH(b.eventDate) as month, COUNT(b) as count " +
            "FROM Booking b WHERE b.eventDate BETWEEN :start AND :end " +
            "GROUP BY YEAR(b.eventDate), MONTH(b.eventDate) ORDER BY year, month")
    List<Object[]> getMonthlyBookingStats(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    @Query("SELECT b.serviceType, COUNT(b) FROM Booking b GROUP BY b.serviceType")
    List<Object[]> countByServiceType();

    // Count queries
    long countByBookingStatus(BookingStatus status);

    long countByPaymentStatus(PaymentStatus status);

    // Existence checks
    boolean existsByEmailAndEventDate(String email, LocalDateTime eventDate);

    // Update queries
    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = :status WHERE b.id = :id")
    int updateBookingStatus(@Param("id") String id, @Param("status") BookingStatus status);

    @Modifying
    @Query("UPDATE Booking b SET b.paymentStatus = :status WHERE b.id = :id")
    int updatePaymentStatus(@Param("id") String id, @Param("status") PaymentStatus status);
}
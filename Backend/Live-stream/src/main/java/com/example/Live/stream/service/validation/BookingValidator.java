package com.example.Live.stream.service.validation;

import com.example.Live.stream.domain.entity.booking.Booking;
import com.example.Live.stream.domain.enums.BookingStatus;
import com.example.Live.stream.domain.enums.PaymentStatus;
import com.example.Live.stream.service.dto.BookingDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Component
public class BookingValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{10,15}$");

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[a-zA-Z\\s]{2,100}$");

    public void validateForCreation(BookingDTO bookingDTO) {
        if (bookingDTO == null) {
            throw new IllegalArgumentException("Booking data cannot be null");
        }

        // Validate name
        if (!StringUtils.hasText(bookingDTO.getName())) {
            throw new IllegalArgumentException("Name is required");
        }

        if (!NAME_PATTERN.matcher(bookingDTO.getName()).matches()) {
            throw new IllegalArgumentException("Name must contain only letters and spaces (2-100 characters)");
        }

        // Validate contact information (at least one of phone or email)
        if (!StringUtils.hasText(bookingDTO.getPhone()) &&
                !StringUtils.hasText(bookingDTO.getEmail())) {
            throw new IllegalArgumentException("Either phone or email is required");
        }

        // Validate email if provided
        if (StringUtils.hasText(bookingDTO.getEmail())) {
            if (!EMAIL_PATTERN.matcher(bookingDTO.getEmail()).matches()) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }

        // Validate phone if provided
        if (StringUtils.hasText(bookingDTO.getPhone())) {
            if (!PHONE_PATTERN.matcher(bookingDTO.getPhone()).matches()) {
                throw new IllegalArgumentException("Phone number must be 10-15 digits, optionally starting with +");
            }
        }

        // Validate event date
        if (bookingDTO.getEventDate() == null) {
            throw new IllegalArgumentException("Event date is required");
        }

        if (bookingDTO.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date cannot be in the past");
        }

        // Validate event title
        if (!StringUtils.hasText(bookingDTO.getEventTitle())) {
            throw new IllegalArgumentException("Event title is required");
        }

        // Validate price
        if (bookingDTO.getPrice() != null) {
            if (bookingDTO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            if (bookingDTO.getPrice().compareTo(new BigDecimal("1000000")) > 0) {
                throw new IllegalArgumentException("Price cannot exceed 1,000,000");
            }
        }

        // Validate location
        if (!StringUtils.hasText(bookingDTO.getLocation())) {
            throw new IllegalArgumentException("Location is required");
        }
    }

    public void validateForUpdate(BookingDTO bookingDTO, Booking existing) {
        if (bookingDTO == null) return;

        // Cannot modify cancelled bookings
        if (existing.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Cannot update a cancelled booking");
        }

        // Cannot change certain fields after confirmation
        if (existing.getBookingStatus() == BookingStatus.CONFIRMED) {
            if (bookingDTO.getEventDate() != null &&
                    !bookingDTO.getEventDate().equals(existing.getEventDate())) {
                throw new IllegalStateException("Cannot change event date after confirmation");
            }

            if (bookingDTO.getPrice() != null &&
                    bookingDTO.getPrice().compareTo(existing.getPrice()) != 0) {
                throw new IllegalStateException("Cannot change price after confirmation");
            }

            if (bookingDTO.getServiceType() != null &&
                    !bookingDTO.getServiceType().equals(existing.getServiceType())) {
                throw new IllegalStateException("Cannot change service type after confirmation");
            }
        }
    }

    public void validateStatusChange(Booking booking, BookingStatus newStatus) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        if (newStatus == null) {
            throw new IllegalArgumentException("New status cannot be null");
        }

        switch (booking.getBookingStatus()) {
            case PENDING:
                if (newStatus == BookingStatus.CANCELLED ||
                        newStatus == BookingStatus.CONFIRMED) {
                    // Valid transitions
                } else {
                    throw new IllegalStateException(
                            "Cannot change from PENDING to " + newStatus
                    );
                }
                break;

            case CONFIRMED:
                if (newStatus != BookingStatus.CANCELLED) {
                    throw new IllegalStateException(
                            "Confirmed bookings can only be cancelled"
                    );
                }
                break;

            case CANCELLED:
                throw new IllegalStateException("Cannot change a cancelled booking");
        }
    }

    public void validatePayment(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Payment can only be processed for confirmed bookings. Current status: " +
                            booking.getBookingStatus()
            );
        }

        if (booking.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("Booking is already paid");
        }

        if (booking.getPrice() == null ||
                booking.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Invalid booking price");
        }
    }

    public void validateCancellation(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }

        // Check if event is too close to cancel
        if (booking.getEventDate() != null &&
                booking.getEventDate().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new IllegalStateException("Cannot cancel booking within 24 hours of event");
        }
    }

    public void validateDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        if (start.isBefore(LocalDateTime.now().minusYears(1))) {
            throw new IllegalArgumentException("Cannot search more than 1 year back");
        }

        if (end.isAfter(LocalDateTime.now().plusYears(1))) {
            throw new IllegalArgumentException("Cannot search more than 1 year forward");
        }
    }
}
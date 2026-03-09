package com.example.Live.stream.service.impl;

import com.example.Live.stream.domain.entity.booking.Booking;
import com.example.Live.stream.domain.enums.BookingStatus;
import com.example.Live.stream.domain.enums.PaymentStatus;
import com.example.Live.stream.repository.BookingRepository;
import com.example.Live.stream.service.BookingService;
import com.example.Live.stream.service.MailService;
import com.example.Live.stream.service.dto.BookingDTO;
import com.example.Live.stream.service.mapper.BookingMapper;
import com.example.Live.stream.service.validation.BookingValidator;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final BookingValidator bookingValidator;
    private final MailService mailService;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            BookingMapper bookingMapper,
            BookingValidator bookingValidator,
            MailService mailService
    ) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.bookingValidator = bookingValidator;
        this.mailService = mailService;
    }

    // USER CREATE BOOKING
    @Override
    public BookingDTO createBooking(BookingDTO dto) {

        bookingValidator.validateForCreation(dto);

        Booking booking = bookingMapper.toEntity(dto);

        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(PaymentStatus.UNPAID);

        Booking saved = bookingRepository.save(booking);

        if (saved.getEmail() != null) {
            mailService.sendPendingMail(
                    saved.getEmail(),
                    saved.getName(),
                    saved.getEventTitle()
            );
        }

        return bookingMapper.toDto(saved);
    }

    // ADMIN CONFIRM BOOKING
    @Override
    public BookingDTO confirmBooking(String id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setBookingStatus(BookingStatus.CONFIRMED);

        Booking saved = bookingRepository.save(booking);

        if (saved.getEmail() != null) {
            mailService.sendConfirmedMail(
                    saved.getEmail(),
                    saved.getName(),
                    saved.getEventTitle()
            );
        }

        return bookingMapper.toDto(saved);
    }

    // ADMIN UPDATE PRICE + PAYMENT STATUS
    @Override
    public BookingDTO updatePaymentAndPrice(String id, BookingDTO dto) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (dto.getPrice() != null) {
            booking.setPrice(dto.getPrice());
        }

        if (dto.getPaymentStatus() != null) {
            booking.setPaymentStatus(dto.getPaymentStatus());
        }

        Booking updated = bookingRepository.save(booking);

        return bookingMapper.toDto(updated);
    }

    // GET ALL BOOKINGS
    @Override
    public List<BookingDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    // GET FULL BOOKING
    @Override
    public BookingDTO getBooking(String id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return bookingMapper.toDto(booking);
    }

    // GET BOOKER INFORMATION ONLY
    @Override
    public BookingDTO getBookerInfo(String id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingDTO dto = new BookingDTO.Builder()
                .id(booking.getId())
                .name(booking.getName())
                .phone(booking.getPhone())
                .email(booking.getEmail())
                .eventTitle(booking.getEventTitle())
                .eventDate(booking.getEventDate())
                .serviceType(booking.getServiceType())
                .location(booking.getLocation())
                .build();

        return dto;
    }
}
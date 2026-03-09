package com.example.Live.stream.controller;

import com.example.Live.stream.service.BookingService;
import com.example.Live.stream.service.dto.BookingDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService)
    {
        this.bookingService = bookingService;
    }

    // USER CREATE BOOKING
    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO dto) {
        return bookingService.createBooking(dto);
    }

    // ADMIN CONFIRM BOOKING
    @PostMapping("/{id}/confirm")
    public BookingDTO confirmBooking(@PathVariable String id) {
        return bookingService.confirmBooking(id);
    }

    // ADMIN UPDATE PRICE + PAYMENT STATUS
    @PutMapping("/{id}/payment")
    public BookingDTO updatePayment(
            @PathVariable String id,
            @RequestBody BookingDTO dto) {

        return bookingService.updatePaymentAndPrice(id, dto);
    }

    // GET ALL BOOKINGS
    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // GET FULL BOOKING
    @GetMapping("/{id}")
    public BookingDTO getBooking(@PathVariable String id) {
        return bookingService.getBooking(id);
    }

    // GET BOOKER INFO ONLY
    @GetMapping("/{id}/info")
    public BookingDTO getBookerInfo(@PathVariable String id) {
        return bookingService.getBookerInfo(id);
    }
}
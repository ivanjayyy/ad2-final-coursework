package lk.ijse.paymentservice.controller;

import jakarta.validation.Valid;
import lk.ijse.paymentservice.dto.PaymentRequest;
import lk.ijse.paymentservice.exception.PaymentDeclinedException;
import lk.ijse.paymentservice.exception.ResourceNotFoundException;
import lk.ijse.paymentservice.model.Payment;
import lk.ijse.paymentservice.model.PaymentStatus;
import lk.ijse.paymentservice.repository.PaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentRepo paymentRepo;
    private final Random random = new Random();

    @Value("${spms.payment.success-rate:0.9}")
    private double successRate;

    @PostMapping("/process")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment process(@Valid @RequestBody PaymentRequest paymentRequest) {
        boolean approved;

        if (paymentRequest.getCardNumber().equals("4111111111111111")) {
            approved = true;

        } else if (paymentRequest.getCardNumber().equals("0000000000000000")) {
            approved = false;

        } else {
            approved = random.nextDouble() < successRate;
        }

        String masked = maskCard(paymentRequest.getCardNumber());

        if (!approved) {
            Payment failed = new Payment(UUID.randomUUID().toString(),
                    paymentRequest.getUserId(), paymentRequest.getVehicleId(),
                    paymentRequest.getParkingSpaceId(),
                    paymentRequest.getAmount(), masked,
                    PaymentStatus.FAILED,
                    null,
                    Instant.now());

            paymentRepo.save(failed);
            throw new PaymentDeclinedException("Payment was declined by the mock payment gateway");
        }

        Payment payment = new Payment(
                null,
                paymentRequest.getUserId(),
                paymentRequest.getVehicleId(),
                paymentRequest.getParkingSpaceId(),
                paymentRequest.getAmount(),
                masked,
                PaymentStatus.SUCCESS,
                "RCPT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                Instant.now()
        );

        return paymentRepo.save(payment);
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable String id) {
        return paymentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentRepo.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getByUser(@PathVariable String userId) {
        return paymentRepo.findByUserId(userId);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service is running");
    }

    private String maskCard(String cardNumber) {
        int len = cardNumber.length();
        String last4 = cardNumber.substring(len - 4);

        return "**** **** **** " + last4;
    }
}

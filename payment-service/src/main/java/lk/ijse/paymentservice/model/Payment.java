package lk.ijse.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
    private String id;
    private String userId;
    private String vehicleId;
    private String parkingSpaceId;
    private double amount;
    private String maskedCardNumber;
    private PaymentStatus status;
    private String receiptId;
    private Instant timestamp;
}

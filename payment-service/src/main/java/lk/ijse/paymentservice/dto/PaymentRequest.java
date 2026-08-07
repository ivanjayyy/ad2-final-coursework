package lk.ijse.paymentservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    @NotNull(message = "User is required")
    private String userId;

    @NotNull(message = "Vehicle is required")
    private String vehicleId;

    @NotNull(message = "Parking Space is required")
    private String parkingSpaceId;

    @DecimalMin(value = "0.01", message = "Must be greater than 0")
    private double amount;

    @NotBlank(message = "Card Number is required")
    @Pattern(regexp = "\\d{12,19}", message = "Card Number must be 12-19 digits")
    private String cardNumber;

    @NotBlank(message = "Expiry Date is required")
    private String expiryDate;

    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be 3-4 digits")
    private String cvv;
}

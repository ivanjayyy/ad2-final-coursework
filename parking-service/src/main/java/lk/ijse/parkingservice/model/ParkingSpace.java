package lk.ijse.parkingservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingSpace {
    private String id;

    @NotBlank(message = "Please enter the number of the parking space")
    private String spaceNumber;

    @NotBlank(message = "Please enter the city")
    private String city;

    @NotBlank(message = "Please enter the zone")
    private String zone;

    @NotNull(message = "Please enter the Owner")
    private String ownerId;

    @Positive(message = "Value must be positive")
    private double pricePerHour;

    private ParkingSpaceStatus status = ParkingSpaceStatus.AVAILABLE;
}

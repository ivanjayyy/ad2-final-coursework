package lk.ijse.vehicleservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicle {
    private String id;

    @NotBlank(message = "Plate number is Required")
    private String plateNumber;

    @NotBlank(message = "Type is Required")
    private String type;

    @NotNull(message = "Owner is Required")
    private String ownerUserId;

    private String currentParkingSpaceId;

    private VehicleStatus status = VehicleStatus.OUTSIDE;
}

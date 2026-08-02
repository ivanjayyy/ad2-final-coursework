package lk.ijse.vehicleservice.controller;

import jakarta.validation.Valid;
import lk.ijse.vehicleservice.exception.ResourceNotFoundException;
import lk.ijse.vehicleservice.model.Vehicle;
import lk.ijse.vehicleservice.model.VehicleStatus;
import lk.ijse.vehicleservice.repository.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleRepo vehicleRepo;

    @GetMapping
    public List<Vehicle> getAll(@RequestParam(required = false) String ownerUserId) {
        if (ownerUserId == null) {
            return vehicleRepo.findAll();
        }
        return vehicleRepo.findAll().stream()
                .filter(v -> v.getOwnerUserId().equals(ownerUserId))
                .toList();
    }

    @GetMapping("/{id}")
    public Vehicle getById(@PathVariable String id) {
        return vehicleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle create(@Valid @RequestBody Vehicle vehicle) {
        vehicle.setId(null);
        return vehicleRepo.save(vehicle);
    }

    @PutMapping("/{id}")
    public Vehicle update(@PathVariable String id, @Valid @RequestBody Vehicle updatedVehicle) {
        Vehicle existingVehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        updatedVehicle.setId(existingVehicle.getId());
        return vehicleRepo.save(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        if (!vehicleRepo.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepo.deleteById(id);
    }

    @PostMapping("/{id}/entry")
    public Vehicle registerEntry(@PathVariable String id, @RequestParam String parkingSpaceId) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        if (vehicle.getStatus() == VehicleStatus.PARKED) {
            throw new IllegalArgumentException("Vehicle is already Parked");
        }
        vehicle.setStatus(VehicleStatus.PARKED);
        vehicle.setCurrentParkingSpaceId(parkingSpaceId);
        return vehicleRepo.save(vehicle);
    }

    @PostMapping("/{id}/exit")
    public Vehicle registerExit(@PathVariable String id) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        if (vehicle.getStatus() == VehicleStatus.OUTSIDE) {
            throw new IllegalArgumentException("Vehicle is already out of service");
        }
        vehicle.setStatus(VehicleStatus.OUTSIDE);
        vehicle.setCurrentParkingSpaceId(null);
        return vehicleRepo.save(vehicle);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Vehicle Service is Running");
    }
}

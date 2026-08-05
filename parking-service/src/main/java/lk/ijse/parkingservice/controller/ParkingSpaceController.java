package lk.ijse.parkingservice.controller;

import jakarta.validation.Valid;
import lk.ijse.parkingservice.exception.ResourceNotFoundException;
import lk.ijse.parkingservice.model.ParkingSpace;
import lk.ijse.parkingservice.model.ParkingSpaceStatus;
import lk.ijse.parkingservice.repository.ParkingSpaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
public class ParkingSpaceController {
    private final ParkingSpaceRepo parkingSpaceRepo;

    @GetMapping
    public List<ParkingSpace> getAll(@RequestParam(required = false) String city,
                                     @RequestParam(required = false) String zone,
                                     @RequestParam(required = false)ParkingSpaceStatus status) {
        return parkingSpaceRepo.findAll().stream()
                .filter(s -> city == null || s.getCity().equalsIgnoreCase(city))
                .filter(s -> zone == null || s.getZone().equalsIgnoreCase(zone))
                .filter(s -> status == null || s.getStatus() == status)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ParkingSpace getById(@PathVariable String id) {
        return parkingSpaceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSpace create(@Valid @RequestBody ParkingSpace parkingSpace) {
        parkingSpace.setId(null);
        return parkingSpaceRepo.save(parkingSpace);
    }

    @PutMapping("/{id}")
    public ParkingSpace update(@PathVariable String id, @Valid @RequestBody ParkingSpace updatedParkingSpace) {
        ParkingSpace existingParkingSpace = parkingSpaceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with id: " + id));

        updatedParkingSpace.setId(existingParkingSpace.getId());
        return parkingSpaceRepo.save(updatedParkingSpace);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        if (!parkingSpaceRepo.existsById(id)) {
            throw new ResourceNotFoundException("Parking space not found with id: " + id);
        }
        parkingSpaceRepo.deleteById(id);
    }

    @PatchMapping("/{id}/status")
    public ParkingSpace updateStatus(@PathVariable String id, @RequestParam ParkingSpaceStatus parkingSpaceStatus) {
        ParkingSpace parkingSpace = parkingSpaceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with id: " + id));

        parkingSpace.setStatus(parkingSpaceStatus);
        return parkingSpaceRepo.save(parkingSpace);
    }

    @PostMapping("/{id}/reserve")
    public ParkingSpace reserve(@PathVariable String id) {
        ParkingSpace parkingSpace = parkingSpaceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with id: " + id));

        if (parkingSpace.getStatus() != ParkingSpaceStatus.AVAILABLE) {
            throw new IllegalArgumentException("Parking space is not available for reservation");
        }

        parkingSpace.setStatus(ParkingSpaceStatus.RESERVED);
        return parkingSpaceRepo.save(parkingSpace);
    }

    @PostMapping("/{id}/release")
    public ParkingSpace release(@PathVariable String id) {
        ParkingSpace parkingSpace = parkingSpaceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with id: " + id));

        parkingSpace.setStatus(ParkingSpaceStatus.AVAILABLE);
        return parkingSpaceRepo.save(parkingSpace);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Parking Service is running");
    }
}

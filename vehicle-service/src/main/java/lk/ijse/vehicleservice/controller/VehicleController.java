package lk.ijse.vehicleservice.controller;

import lk.ijse.vehicleservice.model.Vehicle;
import lk.ijse.vehicleservice.repository.VehicleRepo;
import lombok.RequiredArgsConstructor;
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
}

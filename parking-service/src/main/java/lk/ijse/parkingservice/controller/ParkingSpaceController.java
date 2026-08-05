package lk.ijse.parkingservice.controller;

import lk.ijse.parkingservice.model.ParkingSpace;
import lk.ijse.parkingservice.model.ParkingSpaceStatus;
import lk.ijse.parkingservice.repository.ParkingSpaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

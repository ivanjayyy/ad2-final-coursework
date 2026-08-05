package lk.ijse.parkingservice.repository;

import jakarta.annotation.PostConstruct;
import lk.ijse.parkingservice.model.ParkingSpace;
import lk.ijse.parkingservice.model.ParkingSpaceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ParkingSpaceRepo {
    private final Map<String, ParkingSpace> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void seed() {
        save(new ParkingSpace(null, "A-101", "Colombo", "Zone-A", "owner-1", 2.5, ParkingSpaceStatus.AVAILABLE));
        save(new ParkingSpace(null, "A-102", "Colombo", "Zone-A", "owner-1", 2.5, ParkingSpaceStatus.AVAILABLE));
        save(new ParkingSpace(null, "B-201", "Negombo", "Zone-B", "owner-2", 1.8, ParkingSpaceStatus.OCCUPIED));
    }

    public ParkingSpace save(ParkingSpace parkingSpace) {
        if (parkingSpace.getId() == null) {
            parkingSpace.setId(UUID.randomUUID().toString());
        }

        store.put(parkingSpace.getId(), parkingSpace);
        return parkingSpace;
    }

    public List<ParkingSpace> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<ParkingSpace> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public void deleteById(String id) {
        store.remove(id);
    }

    public boolean existsById(String id) {
        return store.containsKey(id);
    }
}

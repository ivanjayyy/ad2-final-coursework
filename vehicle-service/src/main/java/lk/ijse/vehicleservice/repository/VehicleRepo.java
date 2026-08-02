package lk.ijse.vehicleservice.repository;

import jakarta.annotation.PostConstruct;
import lk.ijse.vehicleservice.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VehicleRepo {
    private final Map<String, Vehicle> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void seed() {
        save(new Vehicle(null, "WP-CAB-1234", "CAR", "user-1", null, VehicleStatus.OUTSIDE));
        save(new Vehicle(null, "WP-XYZ-9988", "VAN", "user-2", null, VehicleStatus.OUTSIDE));
    }

    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            vehicle.setId(UUID.randomUUID().toString());
        }
        store.put(vehicle.getId(), vehicle);
        return vehicle;
    }

    public List<Vehicle> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Vehicle> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public void deleteById(String id) {
        store.remove(id);
    }

    public boolean existsById(String id) {
        return store.containsKey(id);
    }
}

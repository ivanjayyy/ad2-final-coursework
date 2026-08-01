package lk.ijse.userservice.repository;

import jakarta.annotation.PostConstruct;
import lk.ijse.userservice.model.User;
import lk.ijse.userservice.model.UserRole;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepo {
    // DB
    private final Map<String, User> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void seed() {
        save(new User(null, "Kasun Vindana", "kasun@gmail.com", "kasun123", UserRole.DRIVER));
        save(new User(null, "Dishan Amarathunga", "dishan@gmail.com", "dishan123", UserRole.OWNER));
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        store.put(user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return store.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public void deleteById(String id) {
        store.remove(id);
    }

    public boolean existsById(String id) {
        return store.containsKey(id);
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
}

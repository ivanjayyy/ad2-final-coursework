package lk.ijse.userservice.repository;

import jakarta.annotation.PostConstruct;
import lk.ijse.userservice.model.User;
import lk.ijse.userservice.model.UserRole;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
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
}

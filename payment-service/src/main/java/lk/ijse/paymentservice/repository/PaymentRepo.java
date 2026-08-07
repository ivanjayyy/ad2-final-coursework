package lk.ijse.paymentservice.repository;

import lk.ijse.paymentservice.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PaymentRepo {
    private final Map<String, Payment> store = new ConcurrentHashMap<>();

    public Payment save(Payment payment) {
        if (payment.getId() == null) {
            payment.setId(UUID.randomUUID().toString());
        }
        store.put(payment.getId(), payment);
        return payment;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Payment> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Payment> findByUserId(String userId) {
        return store.values().stream().filter(p -> p.getUserId().equals(userId)).toList();
    }

}

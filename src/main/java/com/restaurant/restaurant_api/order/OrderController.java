package com.restaurant.restaurant_api.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Simple controller for orders. In a microservices architecture this could be a
 * separate service; here it's in the same app to simplify the demo. Add notes
 * in the presentation that a real microservice would run in its own JVM/container
 * and talk over HTTP or messaging.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository repo;

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order o) {
        // Calculate total for convenience
        double total = o.getItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        o.setTotal(total);
        return ResponseEntity.ok(repo.save(o));
    }

    @GetMapping
    public ResponseEntity<List<Order>> list() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}

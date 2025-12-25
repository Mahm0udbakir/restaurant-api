package com.restaurant.restaurant_api.restaurant;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic for restaurant operations. Marked as @Service so Spring
 * creates a bean and we can inject it into controllers. This demonstrates
 * dependency injection: controllers depend on services, services depend on
 * repositories.
 */
@Service
public class RestaurantService {

    private final RestaurantRepository repo;

    public RestaurantService(RestaurantRepository repo) {
        this.repo = repo;
    }

    public Restaurant create(Restaurant r) {
        return repo.save(r);
    }

    public Restaurant update(Long id, Restaurant r) {
        r.setId(id);
        return repo.save(r);
    }

    public void delete(Long id) { repo.deleteById(id); }

    public Restaurant findById(Long id) { return repo.findById(id).orElse(null); }

    public List<Restaurant> list() { return repo.findAll(); }
}

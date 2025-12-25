package com.restaurant.restaurant_api.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * User entity stores application users. Uses JPA annotations so Spring Data JPA
 * can persist users to the database.
 *
 * Comment: This class is a JPA entity — that's how Spring Data JPA knows to map
 * it to a database table. Fields are columns. The collection of roles is
 * stored as a comma-separated string for simplicity.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // stored hashed in a real app

    // We'll keep it simple: this stores roles as strings
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}

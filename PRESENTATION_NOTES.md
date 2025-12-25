# Presentation Notes — Restaurant API

This file collects short, copyable notes you can use in a presentation to explain how the project implements each requested concept.

1) Beans & Dependency Injection (DI)
- Files: `UserService`, `RestaurantService`, `AuthController`, `RestaurantController`, annotated with `@Service` and `@RestController`.
- Talking points:
  - A "bean" is an object that Spring instantiates and manages.
  - DI is when Spring automatically provides beans to constructors instead of you manually creating them.
  - Example: `RestaurantController` receives a `RestaurantService` in its constructor. Spring injects the service.

2) Spring Data JPA
- Files: `User`, `Restaurant`, `MenuItem`, `Order`, `OrderItem` entities and repositories like `UserRepository` and `RestaurantRepository`.
- Talking points:
  - Entities are POJOs annotated with `@Entity`. JPA maps them to database tables.
  - Repositories extend `JpaRepository` to get CRUD methods for free (save, findById, findAll, deleteById).

3) REST CRUD API
- Files: `RestaurantController`, `OrderController`, `AuthController`.
- Talking points:
  - Controllers expose endpoints such as `POST /api/restaurants` to create resources.
  - Use HTTP status codes and request/response bodies.

4) Spring Security & JWT Auth
- Files: `SecurityConfig`, `JwtFilter`, `JwtUtil`, `AuthController`.
- Talking points:
  - `AuthController.login` authenticates credentials via `AuthenticationManager` and returns a signed JWT.
  - `JwtFilter` extracts the token from the `Authorization` header, validates it, and sets the `SecurityContext` so secured endpoints know which user is calling.
  - JWTs are signed tokens that carry user identity and roles; services can verify tokens without contacting a central session store.

5) Microservices (conceptual in this demo)
- Note: For simplicity this demo runs all services in one Spring Boot application. To convert to microservices, split packages into separate Spring Boot projects (auth, restaurant, order), run them on different ports, and secure inter-service calls using the same JWTs.

6) Code locations & functions to highlight during a demo
- `AuthController.login` — show login flow and token creation. Explain lines where token is created using `JwtUtil.generateToken`.
- `JwtFilter.doFilterInternal` — point out how the Authorization header is read and `UsernamePasswordAuthenticationToken` is built.
- `RestaurantService.create/update/list` — simple CRUD logic. Show dependency injection by pointing at controller constructors.

7) Java-specific notes (what a Flutter developer might not know)
- Annotations like `@Service`, `@Repository`, `@Entity`, `@RestController` are metadata that tells Spring what role the class plays.
- Lombok annotations (`@Getter`, `@Setter`, `@Builder`) auto-generate boilerplate (getters/setters/constructors). These do not exist in Flutter/Dart; they are compile-time helpers.
- Records (e.g., `AuthRequest`) are immutable DTOs introduced in Java 16+ to concisely model simple data carriers.

8) Where to look in the code for comments
- I added inline comments near classes and methods that are likely to be discussed. Use those as speaking notes.

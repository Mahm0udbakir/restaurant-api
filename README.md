# Restaurant API (Demo)

This repository is a focused demo Spring Boot application showcasing:

- Beans & Dependency Injection
- Spring Data JPA (H2 in-memory DB)
- REST CRUD API for restaurants and orders
- Spring Security with JWT-based authentication
- Comments and presentation notes explaining important concepts

How to run (Windows PowerShell):

```powershell
cd c:\Users\Bakir\Desktop\restaurant-system\restaurant-api
.\gradlew.bat bootRun
```

Open the H2 console at http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:restaurantdb`).

Sample requests (register, login, then call protected API):

# Register
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "{\"username\":\"user1\",\"password\":\"pass\"}"

# Login -> returns token
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"username\":\"user1\",\"password\":\"pass\"}"

# Use the token for protected endpoints (replace TOKEN below)
curl -H "Authorization: Bearer TOKEN" http://localhost:8080/api/restaurants

See `PRESENTATION_NOTES.md` for line-by-line explanations and which files to use in your presentation.

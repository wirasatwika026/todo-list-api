# Todo List REST API

API RESTful sederhana untuk mengelola daftar tugas (Todo List) menggunakan Spring Boot.

## 📋 Fitur

- ✅ CRUD operations untuk todo items
- � User authentication dengan JWT
- 🔐 Registration dan login system
- 🔒 User-specific todos (setiap user hanya bisa melihat todos miliknya)
- �🔍 Search todo berdasarkan judul
- 📊 Filter berdasarkan status dan prioritas
- 📈 Statistik todo (completed, pending, total)
- ⏰ Manajemen due date dan overdue todos
- �️ Validasi input dan error handling
- 📝 Logging dan monitoring
- 🧪 Unit tests dan integration tests
- 📚 API documentation dengan Swagger

## 🛠️ Teknologi yang Digunakan

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **Spring Security** (Authentication & Authorization)
- **JWT (JSON Web Token)** (Token-based Authentication)
- **PostgreSQL** (Production Database)
- **H2 Database** (Testing Database)
- **Flyway** (Database Migration)
- **Swagger/OpenAPI 3** (API Documentation)
- **Lombok** (Boilerplate Code Reduction)
- **JUnit 5** (Unit Testing)
- **Mockito** (Mocking Framework)

## 📁 Struktur Proyek

```
src/
├── main/
│   ├── java/com/example/todo_list_api/
│   │   ├── TodoListApiApplication.java          # Main Application Class
│   │   ├── config/                              # Configuration Classes
│   │   │   ├── SecurityConfig.java             # Security Configuration
│   │   │   ├── SwaggerConfig.java             # Swagger/OpenAPI Configuration
│   │   │   └── WebConfig.java                  # Web Configuration (CORS)
│   │   ├── controller/                          # REST Controllers
│   │   │   ├── AuthController.java             # Authentication Endpoints
│   │   │   └── TodoController.java             # Todo REST Endpoints
│   │   ├── dto/                                # Data Transfer Objects
│   │   │   ├── request/                        # Request DTOs
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── TodoCreateRequest.java
│   │   │   │   └── TodoUpdateRequest.java
│   │   │   └── response/                       # Response DTOs
│   │   │       ├── ApiResponse.java
│   │   │       ├── AuthResponse.java
│   │   │       ├── TodoResponse.java
│   │   │       └── UserResponse.java
│   │   ├── exception/                          # Exception Handling
│   │   │   ├── GlobalExceptionHandler.java    # Global Exception Handler
│   │   │   ├── ResourceNotFoundException.java # Custom Exception
│   │   │   └── ErrorResponse.java             # Error Response DTO
│   │   ├── model/                              # JPA Entities
│   │   │   ├── Todo.java                      # Todo Entity
│   │   │   └── User.java                      # User Entity
│   │   ├── repository/                         # Data Access Layer
│   │   │   ├── TodoRepository.java            # Todo Repository
│   │   │   └── UserRepository.java            # User Repository
│   │   ├── security/                          # Security Components
│   │   │   ├── JwtAuthenticationFilter.java   # JWT Filter
│   │   │   └── JwtTokenProvider.java          # JWT Utility
│   │   └── service/                           # Business Logic Layer
│   │       ├── AuthService.java               # Auth Service Interface
│   │       └── impl/
│   │           ├── AuthServiceImpl.java       # Auth Service Implementation
│   │           ├── CustomUserDetailsService.java # UserDetails Service
│   │           └── TodoServiceImpl.java       # Todo Service Implementation
│   └── resources/
│       ├── application.properties              # Application Configuration
│       └── db/migration/                      # Database Migrations
│           ├── V1__Create_todos_table.sql
│           └── V2__Create_users_table_and_update_todos.sql
└── test/                                      # Test Classes
    ├── java/com/example/todo_list_api/
    │   ├── controller/
    │   │   └── TodoControllerIntegrationTest.java
    │   └── service/
    │       └── TodoServiceTest.java
    └── resources/
        └── application-test.properties         # Test Configuration
```

## 🚀 Cara Menjalankan

### Prerequisites

- Java 17 atau lebih tinggi
- Maven 3.6+
- PostgreSQL (untuk production)

### 1. Clone Repository

```bash
git clone <repository-url>
cd todo-list-api
```

### 2. Setup Database

Pastikan PostgreSQL sudah berjalan dan sesuaikan konfigurasi di `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_list
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build dan Run

```bash
# Install dependencies dan build
mvn clean install

# Run aplikasi
mvn spring-boot:run
```

Aplikasi akan berjalan di `http://localhost:8080`

### 4. Default Admin User

Setelah aplikasi berjalan, Anda dapat menggunakan admin user default untuk testing:

- **Username**: `admin`
- **Email**: `admin@todoapp.com`
- **Password**: `admin123`
- **Role**: `ADMIN`

Atau Anda bisa mendaftar user baru melalui endpoint `/api/auth/register`.

### API Documentation URLs

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs

### 5. Run Tests

```bash
# Run semua tests
mvn test

# Run tests dengan coverage
mvn test jacoco:report
```

## 📖 Swagger Documentation

Setelah aplikasi berjalan, Anda dapat mengakses dokumentasi API interaktif melalui Swagger UI di:

**http://localhost:8080/swagger-ui.html**

Swagger UI menyediakan:

- 📋 Daftar semua endpoint API
- 📝 Dokumentasi detail setiap endpoint
- 🧪 Interface untuk testing API secara langsung
- 📊 Schema definition untuk request/response
- 💡 Contoh request dan response

### Fitur Swagger yang Tersedia:

- **Try it out**: Test API langsung dari browser
- **Schema documentation**: Detail struktur data
- **Response examples**: Contoh response untuk setiap status code
- **Parameter documentation**: Penjelasan setiap parameter

## 📚 API Documentation

### Authentication

API ini menggunakan JWT (JSON Web Token) untuk authentication. Setelah login, Anda akan mendapatkan access token yang harus disertakan dalam header `Authorization` untuk setiap request ke endpoint yang memerlukan authentication.

#### Authentication Endpoints

##### 1. Register User

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

##### 2. Login User

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

Response:

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer",
    "user": {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "role": "USER",
      "enabled": true,
      "createdAt": "2023-07-08T10:30:00"
    }
  }
}
```

##### 3. Get Current User

```http
GET /api/auth/me
Authorization: Bearer {your-jwt-token}
```

### Todo Endpoints

**Note**: Semua endpoint todo memerlukan authentication. Sertakan JWT token dalam header:

```
Authorization: Bearer {your-jwt-token}
```

### Base URL

```
http://localhost:8080/api
```

### Endpoints

#### 1. Create Todo

```http
POST /api/todos
Content-Type: application/json

{
  "title": "Belajar Spring Boot",
  "description": "Mempelajari konsep REST API dengan Spring Boot",
  "priority": "HIGH",
  "dueDate": "2023-12-31T23:59:59"
}
```

#### 2. Get All Todos

```http
GET /api/todos
```

Query Parameters:

- `completed` (boolean): Filter by completion status
- `priority` (string): Filter by priority (LOW, MEDIUM, HIGH)
- `search` (string): Search by title

#### 3. Get Todo by ID

```http
GET /api/todos/{id}
```

#### 4. Update Todo

```http
PUT /api/todos/{id}
Content-Type: application/json

{
  "title": "Updated Title",
  "description": "Updated Description",
  "completed": true,
  "priority": "MEDIUM"
}
```

#### 5. Delete Todo

```http
DELETE /api/todos/{id}
```

#### 6. Mark as Completed

```http
PATCH /api/todos/{id}/complete
```

#### 7. Mark as Incomplete

```http
PATCH /api/todos/{id}/incomplete
```

#### 8. Get Overdue Todos

```http
GET /api/todos/overdue
```

#### 9. Get Todos Due Between Dates

```http
GET /api/todos/due-between?start=2023-01-01T00:00:00&end=2023-12-31T23:59:59
```

#### 10. Get Statistics

```http
GET /api/todos/statistics
```

### Response Format

Semua response menggunakan format yang konsisten:

```json
{
  "success": true,
  "message": "Success",
  "data": {...},
  "timestamp": "2023-07-08T10:30:00"
}
```

Error Response:

```json
{
  "success": false,
  "message": "Error message",
  "error": "ERROR_CODE",
  "status": 400,
  "timestamp": "2023-07-08T10:30:00",
  "path": "/api/todos",
  "validationErrors": [
    {
      "field": "title",
      "message": "Title is required"
    }
  ]
}
```

## 🔧 Configuration

### Database

#### Production (PostgreSQL)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_list
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
```

#### Testing (H2)

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.flyway.enabled=false
```

### Security

Saat ini security dikonfigurasi untuk development dengan akses terbuka ke semua endpoints. Untuk production, perlu implementasi authentication dan authorization.

## 🧪 Testing

### Unit Tests

- Service layer tests dengan Mockito
- Repository layer tests dengan @DataJpaTest
- Controller layer tests dengan @WebMvcTest

### Integration Tests

- End-to-end testing dengan TestRestTemplate
- Database integration testing dengan @SpringBootTest

## 📝 Best Practices yang Diimplementasikan

1. **Layered Architecture**: Pemisahan concern yang jelas antara Controller, Service, dan Repository
2. **DTO Pattern**: Pemisahan antara Entity dan Data Transfer Objects
3. **Exception Handling**: Global exception handler untuk error handling yang konsisten
4. **Validation**: Input validation menggunakan Bean Validation
5. **Transaction Management**: Proper transaction boundaries
6. **Logging**: Structured logging untuk monitoring dan debugging
7. **Testing**: Comprehensive test coverage
8. **Database Migration**: Flyway untuk version control database schema
9. **Configuration Management**: Profile-based configuration
10. **API Documentation**: Clear and consistent API documentation

## 🚀 Production Deployment

### Environment Variables

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://prod-db:5432/todo_list
export SPRING_DATASOURCE_USERNAME=prod_user
export SPRING_DATASOURCE_PASSWORD=prod_password
export SPRING_PROFILES_ACTIVE=production
```

### Docker

```dockerfile
FROM openjdk:17-jre-slim
COPY target/todo-list-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

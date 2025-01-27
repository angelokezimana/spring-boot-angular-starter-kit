# Posta

Welcome to **Posta**, a blog application designed to learn and demonstrate the integration of Spring Boot for backend services with Angular for the front-end. This project aims to provide a platform for creating, reading, updating, and deleting (CRUD) blog posts, comments, and user authentication.

## Table of Contents

- [About](#about)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## About

**Posta** serves as an educational tool for understanding how to build a full-stack application using:

- **Backend**: Spring Boot with JPA for database operations, Spring Security for authentication, and JWT for token management.
- **Frontend**: Angular for creating a responsive and interactive user interface.

The application features:
- User registration and authentication
- Post creation, editing, and deletion
- Comment system for posts
- Image handling: Upload images for posts, Display images on posts and user profiles, Delete images from the filesystem
- Role-based access control
- etc.

## Setup

### Prerequisites

- Java 21
- Node.js (v22.12.0)
- npm (10.9.0)
- Angular CLI: (19.0.3)
- Angular (18.2.12)
- MySQL
- Apache Tomcat (11.0.2)

### Backend (Spring Boot)

1. **Clone the repository:**

   ```sh
   git clone https://github.com/angelokezimana/posta.git
   cd posta
   ```

2. **Set up the database:**

Create a new database named `posta_db` in MySQL.

Update `application.properties` or environment-specific properties files with your database credentials.

Build and run:

```sh
./mvnw spring-boot:run
```

### Frontend (Angular)

1. **Navigate to the frontend folder:**

```sh
cd src/main/webapp
```

2. **Install dependencies:**

```sh
npm install
```

3. **Run the Angular app:**

```sh
ng serve
```

Your Angular application will be accessible at http://localhost:4200/.

## Running the Application

### Development

Use the commands above for running both backend and frontend in development mode.

### Production

1. **Backend:** `mvn clean install` to build a WAR file, then deploy on a server like Tomcat.

2. **Frontend:** `npm run build` to generate a production build, then deploy to any static file server or use the backend as a reverse proxy.

## Project Structure

- Backend (src/main/java/com/angelokezimana/posta/):
  - config/: Configuration classes
  - controller/: REST controllers
  - service/: Business logic
  - entity/: Database models
  - repository/: JPA repositories
- Frontend (src/main/webapp/src/):
  - app/: Angular modules, components, services
  - assets/: Static files like images, SCSS
  - environments/: Environment configurations

## API Documentation

Access the OpenAPI specification at:

Development: http://localhost:8085/swagger-ui/index.html

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository: `git clone https://github.com/angelokezimana/posta.git`

2. Create a new branch (`git checkout -b feature/YourFeature`)

3. Make your changes

4. Commit your changes (`git commit -am 'Add some feature'`)

5. Push to the branch (`git push origin feature/YourFeature`)

6. Create a new Pull Request

## License

This project is open source and available under the MIT License (LICENSE).

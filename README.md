# Spring Boot & Angular Starter Kit 🚀

Welcome to the Spring Boot & Angular Starter Kit! This repository provides a full-stack template for building modern web applications using Spring Boot (backend) and Angular (frontend). It’s designed to help developers quickly set up a robust, scalable, and production-ready project with minimal configuration.

Whether you're a beginner learning full-stack development or an experienced developer starting a new project, this starter kit has you covered.

## Table of Contents

- [Spring Boot \& Angular Starter Kit 🚀](#spring-boot--angular-starter-kit-)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
    - [Backend:](#backend)
    - [Frontend:](#frontend)
    - [Pre-configured:](#pre-configured)
  - [Technologies Used](#technologies-used)
  - [Setup](#setup)
    - [Prerequisites](#prerequisites)
    - [Backend (Spring Boot)](#backend-spring-boot)
    - [Frontend (Angular)](#frontend-angular)
  - [Running the Application](#running-the-application)
    - [Development Mode](#development-mode)
    - [Production Mode](#production-mode)
  - [API Documentation](#api-documentation)
  - [Contributing](#contributing)
  - [License](#license)

## Features

### Backend:

1. RESTful APIs with Spring Boot.

2. JWT-based authentication with Spring Security.

3. Database integration using Spring Data JPA (supports MySQL).

4. Exception handling and validation.

### Frontend:

1. Modular Angular application with a clean structure.

2. Responsive UI components.

3. Authentication and role-based access control.

### Pre-configured:

1. Ready-to-use project structure.

2. Sample code for CRUD operations.

3. Step-by-step setup guide.

## Technologies Used

- **Backend:** Spring Boot, Spring Security, JWT, Spring Data JPA, MySQL.

- **Frontend:** Angular, TypeScript, Angular Material.

- **Tools:** Maven, npm, Docker (optional).

## Setup

### Prerequisites

- Java 21

- Node.js (v22.12.0 or higher)

- npm (10.9.0 or higher)

- Angular CLI (19.0.3 or higher)

- MySQL

- Apache Tomcat (for production deployment)

### Backend (Spring Boot)

1. Clone the repository:

```sh
git clone https://github.com/your-username/spring-boot-angular-starter-kit.git
cd spring-boot-angular-starter-kit
```

2. Set up the database:

- Create a new database (e.g., `starter_db`) in MySQL.

- Update the `application-<dev|prod>.properties` file with your database credentials.

3. Build and run the backend:

```sh
./mvnw spring-boot:run
```

The backend will be accessible at `http://localhost:8085`.

### Frontend (Angular)

1. Navigate to the frontend folder:

```sh
cd src/main/webapp
```

2. Install dependencies:

```sh
npm install
```

3. Run the Angular app:

```sh
ng serve
```

The frontend will be accessible at `http://localhost:4200`.

## Running the Application

### Development Mode

- Run the backend and frontend separately using the commands above.

- The backend serves REST APIs, while the frontend connects to them.

### Production Mode

- `Backend:` Build a WAR file using `mvn clean install` and deploy it to a server like Tomcat.

- `Frontend:` Generate a production build using `npm run build` and deploy the static files to a web server or use the backend as a reverse proxy.

## API Documentation

The backend API is documented using Swagger. Access the OpenAPI specification at:

- Development: `http://localhost:8085/swagger-ui/index.html`

## Contributing

Contributions are welcome! Here’s how you can help:

1. Fork the repository.

2. Create a new branch (`git checkout -b feature/YourFeature`).

3. Make your changes.

4. Commit your changes (`git commit -am 'Add some feature'`).

5. Push to the branch (`git push origin feature/YourFeature`).

6. Create a new Pull Request.

## License

This project is open source and available under the MIT License. See the [LICENSE](https://github.com/angelokezimana/spring-boot-angular-starter-kit/blob/main/LICENSE) file for details.

# StudyForce — Developer Learning & Debugging Platform (Backend)

## Project Description

StudyForce is a full-stack learning platform designed to help developers study technical concepts and debug applications during training. The system provides a flashcard-based learning experience combined with tools to assist in understanding errors and stack traces.

This repository contains the backend services built using Spring Boot, exposing RESTful APIs to support user authentication, study content management, and application functionality.

---

## Features

- Flashcard-based study system with question/answer functionality  
- Difficulty rating system for learning content  
- Answer tracking and response management  
- RESTful API design for frontend-backend communication  
- AWS-based authentication using Cognito  
- API routing through AWS API Gateway  
- CI/CD pipeline integration using AWS CodeBuild and CodePipeline  
- Unit and integration testing using JUnit and MockMVC  

---

## Tech Stack

### Backend
- Java  
- Spring Boot  
- Spring MVC  
- Spring Data JPA  

### Database
- PostgreSQL  

### Cloud & DevOps
- AWS Cognito (authentication)  
- AWS API Gateway (routing)  
- AWS S3  
- AWS CodeBuild & CodePipeline (CI/CD)  
- AWS CloudFormation (infrastructure as code)  
- Docker  

### Testing & Tools
- JUnit  
- MockMVC  
- Swagger  
- Maven  
- Git  

---

## Architecture Overview

The application follows a layered architecture:

- Controllers — Handle HTTP requests and responses  
- Services — Contain business logic  
- Repositories — Manage database interactions using JPA  
- DTOs — Handle request/response data transfer  

The backend communicates with the frontend via REST APIs and integrates AWS services for authentication and deployment.

---

## Getting Started

```bash
git clone https://github.com/RevatureRobert/2102-Study-Force-Backend.git
cd 2102-Study-Force-Backend
mvn clean install
mvn spring-boot:run

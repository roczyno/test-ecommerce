# Spring Boot E-commerce API

A comprehensive RESTful API for e-commerce applications built with Spring Boot. This API provides all the necessary endpoints to build a full-featured e-commerce platform including user authentication, product management, shopping cart functionality, order processing, payment integration, and more.

## Features

- **User Management**
  - Registration and authentication using JWT
  - Role-based access control
  - Password reset functionality

- **Product Management**
  - Create, read, update, and delete products
  - Product categorization
  - Product search and filtering

- **Shopping Cart**
  - Add/remove items to cart
  - Update quantities
  - Cart persistence

- **Order Processing**
  - Create and manage orders
  - Order history
  - Order status tracking

- **Payment Integration**
  - Integration with PayStack payment gateway
  - Secure payment processing

- **Reviews and Ratings**
  - Product reviews
  - Star ratings
  - User feedback management

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.2**
- **Spring Security** - For authentication and authorization
- **Spring Data JPA** - For database operations
- **PostgreSQL** - Database
- **JWT (JSON Web Token)** - For secure authentication
- **Spring Mail** - For email notifications
- **Thymeleaf** - For email templates
- **Lombok** - To reduce boilerplate code
- **Maven** - For dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL database

## Getting Started

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/spring-boot-ecommerce-api.git
   cd spring-boot-ecommerce-api
   ```

2. Configure the application:

   Create a `.env` file in the root directory with the following properties:
   ```properties
   ACTIVE_PROFILE=dev
   POSTGRESQL_HOST=localhost
   POSTGRESQL_PORT=5432
   POSTGRESQL_DATABASE=ecommerce
   POSTGRESQL_USERNAME=your_username
   POSTGRESQL_PASSWORD=your_password
   EMAIL_HOST=smtp.example.com
   EMAIL_PORT=587
   EMAIL_USERNAME=your_email@example.com
   EMAIL_PASSWORD=your_email_password
   FRONTEND_URL=http://localhost:3000
   ```

3. Build the application:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8081/api/v1/`

## API Documentation

### Authentication Endpoints

- `POST /auth/register` - Register a new user
- `POST /auth/login` - Authenticate a user and get JWT token
- `POST /auth/forgot-password` - Request password reset
- `POST /auth/reset-password` - Reset password with token

### Product Endpoints

- `GET /products` - Get all products
- `GET /products/{id}` - Get a specific product
- `POST /products` - Create a new product (Admin only)
- `PUT /products/{id}` - Update a product (Admin only)
- `DELETE /products/{id}` - Delete a product (Admin only)

### Category Endpoints

- `GET /categories` - Get all categories
- `GET /categories/{id}` - Get a specific category
- `POST /categories` - Create a new category (Admin only)
- `PUT /categories/{id}` - Update a category (Admin only)
- `DELETE /categories/{id}` - Delete a category (Admin only)

### Cart Endpoints

- `GET /cart` - Get current user's cart
- `POST /cart/items` - Add item to cart
- `PUT /cart/items/{id}` - Update cart item
- `DELETE /cart/items/{id}` - Remove item from cart

### Order Endpoints

- `GET /orders` - Get current user's orders
- `GET /orders/{id}` - Get a specific order
- `POST /orders` - Create a new order
- `PUT /orders/{id}/status` - Update order status (Admin only)

### Payment Endpoints

- `POST /payments/initialize` - Initialize payment
- `POST /payments/verify` - Verify payment

### Review Endpoints

- `GET /products/{id}/reviews` - Get reviews for a product
- `POST /products/{id}/reviews` - Add a review to a product
- `PUT /reviews/{id}` - Update a review
- `DELETE /reviews/{id}` - Delete a review

### Rating Endpoints

- `GET /products/{id}/ratings` - Get ratings for a product
- `POST /products/{id}/ratings` - Rate a product
- `PUT /ratings/{id}` - Update a rating
- `DELETE /ratings/{id}` - Delete a rating

## Security

The API uses JWT (JSON Web Token) for authentication. To access protected endpoints, you need to include the JWT token in the Authorization header:

```
Authorization: Bearer your_jwt_token
```

## Environment Profiles

The application supports multiple environment profiles:

- `dev` - Development environment
- `prod` - Production environment

You can set the active profile using the `ACTIVE_PROFILE` environment variable.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot and the Spring community
- All contributors to this project
# test-ecommerce
# test-ecommerce

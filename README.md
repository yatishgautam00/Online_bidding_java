# Online Bidding System

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Schema](#database-schema)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [Future Enhancements](#future-enhancements)
- [Contributors](#contributors)

---

## Overview
The **Online Bidding System** is a Java-based application that enables users to act as both buyers and sellers. Sellers can list products for bidding, specifying details like base price and time limits, while buyers can place bids on listed products. The system ensures fair bidding by validating wallet balances and automatically assigning the product to the highest bidder once the bidding period ends.

---

## Features
1. **User Management**:
   - Registration and Login.
   - Single user can act as both a buyer and a seller.
   - Wallet management for users.

2. **Product Management**:
   - Sellers can add, edit, and delete products.
   - Products include attributes such as name, description, base price, image URL, and time limit.

3. **Bidding System**:
   - Buyers can place bids on available products.
   - Bids are validated based on wallet balance and product base price.
   - Automatic determination of the highest bidder after the time limit expires.

4. **Admin Panel** (Future Enhancement):
   - Manage users, products, and bids.
   - Monitor system activities.

---

## Technologies Used
- **Java**: Core application logic.
- **JDBC**: Database connectivity.
- **MySQL**: Database for user and product management.
- **IntelliJ IDEA**: Development environment.

---

## Database Schema

### Users Table
```sql
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    wallet_balance DECIMAL(10, 2) DEFAULT 0.00
);
```

### Products Table
```sql
CREATE TABLE Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL, -- Seller's user ID
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    base_price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(255),
    time_limit DATETIME NOT NULL,
    status ENUM('Available', 'Sold') DEFAULT 'Available',
    final_price DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
```

### Bids Table (Optional for Future Enhancements)
```sql
CREATE TABLE Bids (
    bid_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    user_id INT NOT NULL, -- Buyer's user ID
    bid_amount DECIMAL(10, 2) NOT NULL,
    bid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES Products(product_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
```

---

## Setup Instructions

### Prerequisites
- Install **Java 8+**.
- Install **MySQL**.
- Install **IntelliJ IDEA** (or any Java IDE).
- Add MySQL Connector to your project's dependencies.

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/online-bidding-system.git
   cd online-bidding-system
   ```
2. Create the database:
   - Open MySQL Workbench or any MySQL client.
   - Execute the provided `Users` and `Products` table scripts.

3. Update database credentials:
   - Open the `DBConnection` class.
   - Update the `url`, `username`, and `password` fields to match your MySQL setup:
     ```java
     String url = "jdbc:mysql://localhost:3306/online_bidding_system";
     String username = "root";
     String password = "your_password";
     ```

4. Build and run the project:
   - Open the project in IntelliJ IDEA.
   - Build and run the `Main` class.

---

## Usage
1. Register a new user (e.g., "JohnDoe").
2. Login using the created credentials.
3. As a seller:
   - Add products for bidding.
   - View, edit, or delete your products.
4. As a buyer:
   - View available products.
   - Place bids on products.
5. Watch the system determine the winner based on the highest bid after the product's time limit expires.

---

## Future Enhancements
1. **Admin Panel**:
   - Role-based access for administrators to manage users and products.
2. **Notifications**:
   - Notify sellers and buyers of bids and auction results.
3. **Enhanced Bidding**:
   - Live bidding updates.
4. **Image Upload**:
   - Allow users to upload product images directly.
5. **Transaction History**:
   - Track all transactions for audit purposes.

---

## Contributors
- Yatish Gautam - Developer

---

Feel free to fork this repository and contribute! Suggestions and improvements are always welcome. 😊


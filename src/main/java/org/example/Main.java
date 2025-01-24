package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        ProductDAO productDAO = new ProductDAO();
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to the Online Bidding System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1: // Register a new user
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    System.out.print("Enter initial wallet balance: ");
                    double walletBalance = scanner.nextDouble();

                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setWalletBalance(walletBalance);

                    if (userDAO.registerUser(newUser)) {
                        System.out.println("Registration successful! You can now log in.");
                    } else {
                        System.out.println("Registration failed! Username might already exist.");
                    }
                    break;

                case 2: // Login an existing user
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    User loggedInUser = userDAO.loginUser(loginUsername, loginPassword);

                    if (loggedInUser != null) {
                        System.out.println("Login successful!");
                        System.out.println("Welcome, " + loggedInUser.getUsername());
                        System.out.println("Your wallet balance is: $" + loggedInUser.getWalletBalance());

                        boolean userSessionActive = true;
                        while (userSessionActive) {
                            // Display options for logged-in users
                            System.out.println("\nWhat would you like to do?");
                            System.out.println("1. Add Product (Seller Only)");
                            System.out.println("2. Edit Product");
                            System.out.println("3. Delete Product");
                            System.out.println("4. Log out");
                            System.out.print("Choose an option: ");
                            int userChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character

                            switch (userChoice) {
                                case 1: // Add Product
                                    System.out.println("\n-- Add a New Product --");
                                    System.out.print("Enter product name: ");
                                    String productName = scanner.nextLine();

                                    System.out.print("Enter description: ");
                                    String description = scanner.nextLine();

                                    System.out.print("Enter base price: ");
                                    double basePrice = scanner.nextDouble();

                                    System.out.print("Enter time limit (in minutes): ");
                                    int timeLimit = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline character

                                    Product product = new Product();
                                    product.setSellerId(loggedInUser.getUserId());  // Use the logged-in user ID as the seller
                                    product.setProductName(productName);
                                    product.setDescription(description);
                                    product.setBasePrice(basePrice);
                                    product.setTimeLimit(timeLimit);

                                    if (productDAO.addProduct(product)) {
                                        System.out.println("Product added successfully!");
                                    } else {
                                        System.out.println("Failed to add product.");
                                    }
                                    break;

                                case 2: // Edit Product
                                    System.out.println("\n-- Edit a Product --");
                                    System.out.print("Enter product ID to edit: ");
                                    int productIdToEdit = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline character

                                    Product productToEdit = productDAO.getProductById(productIdToEdit);
                                    if (productToEdit != null) {
                                        System.out.println("Product details: ");
                                        System.out.println("Name: " + productToEdit.getProductName());
                                        System.out.println("Description: " + productToEdit.getDescription());
                                        System.out.println("Base Price: $" + productToEdit.getBasePrice());
                                        System.out.println("Time Limit: " + productToEdit.getTimeLimit() + " minutes");

                                        System.out.print("Enter new name (or press Enter to keep the same): ");
                                        String newName = scanner.nextLine();
                                        if (!newName.isEmpty()) {
                                            productToEdit.setProductName(newName);
                                        }

                                        System.out.print("Enter new description (or press Enter to keep the same): ");
                                        String newDescription = scanner.nextLine();
                                        if (!newDescription.isEmpty()) {
                                            productToEdit.setDescription(newDescription);
                                        }

                                        System.out.print("Enter new base price (or press Enter to keep the same): ");
                                        String newBasePriceStr = scanner.nextLine();
                                        if (!newBasePriceStr.isEmpty()) {
                                            double newBasePrice = Double.parseDouble(newBasePriceStr);
                                            productToEdit.setBasePrice(newBasePrice);
                                        }

                                        System.out.print("Enter new time limit in minutes (or press Enter to keep the same): ");
                                        String newTimeLimitStr = scanner.nextLine();
                                        if (!newTimeLimitStr.isEmpty()) {
                                            int newTimeLimit = Integer.parseInt(newTimeLimitStr);
                                            productToEdit.setTimeLimit(newTimeLimit);
                                        }

                                        if (productDAO.editProduct(productToEdit)) {
                                            System.out.println("Product updated successfully!");
                                        } else {
                                            System.out.println("Failed to update product.");
                                        }
                                    } else {
                                        System.out.println("Product not found!");
                                    }
                                    break;

                                case 3: // Delete Product
                                    System.out.println("\n-- Delete a Product --");
                                    System.out.print("Enter product ID to delete: ");
                                    int productIdToDelete = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline character

                                    if (productDAO.deleteProduct(productIdToDelete)) {
                                        System.out.println("Product deleted successfully!");
                                    } else {
                                        System.out.println("Failed to delete product.");
                                    }
                                    break;

                                case 4: // Log out
                                    System.out.println("Logging out...");
                                    userSessionActive = false;
                                    break;

                                default:
                                    System.out.println("Invalid option! Please try again.");
                                    break;
                            }
                        }

                    } else {
                        System.out.println("Invalid username or password. Please try again.");
                    }
                    break;

                case 3: // Exit
                    System.out.println("Thank you for using the Online Bidding System. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        scanner.close();
    }
}

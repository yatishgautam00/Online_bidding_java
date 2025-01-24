package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO() {
        connection = DBConnection.getConnection();
    }

    // Add a product
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO Products (user_id, product_name, description, base_price, image_url, time_limit) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, product.getUserId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getBasePrice());
            stmt.setString(5, product.getImageUrl());
            stmt.setTimestamp(6, Timestamp.valueOf(product.getTimeLimit()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Edit a product
    public boolean editProduct(Product product) {
        String sql = "UPDATE Products SET product_name = ?, description = ?, base_price = ?, image_url = ?, time_limit = ? WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getBasePrice());
            stmt.setString(4, product.getImageUrl());
            stmt.setTimestamp(5, Timestamp.valueOf(product.getTimeLimit()));
            stmt.setInt(6, product.getProductId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a product
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM Products WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all products of a seller
    public List<Product> getProductsByUser(int userId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setUserId(rs.getInt("user_id"));
                product.setProductName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setBasePrice(rs.getDouble("base_price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setTimeLimit(rs.getTimestamp("time_limit").toLocalDateTime());
                product.setStatus(rs.getString("status"));
                product.setFinalPrice(rs.getDouble("final_price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

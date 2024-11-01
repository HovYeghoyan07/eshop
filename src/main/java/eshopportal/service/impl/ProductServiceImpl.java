package eshopportal.service.impl;

import eshopportal.db.DbConnectionProvider;
import eshopportal.model.Category;
import eshopportal.model.Product;
import eshopportal.service.ProductService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final Connection connection = DbConnectionProvider.getInstance().getConnection();

    public void addProduct(Product product) {
        try {
            String sql = "INSERT INTO product(name,description,price,quantity,category_id) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQty());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM Category WHERE id =  "+id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQty(resultSet.getInt("quantity"));
                Category category = new Category();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getProductMaxPrice() {
        Product product = null;
        try {
            String sql = "SELECT MAX(price) FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getProductMinPrice() {
        Product product = null;
        try {
            String sql = "SELECT MIN(price) FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public double getAveragePrice() {
        try {
            String sql = "SELECT AVG(price) FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteProductById(int id) {

        if (getProductById(id) == null) {
            System.out.println("Product does not exist");
        }

        String sql = "DELETE FROM product WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Product was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSumOfProducts() {
        try {
            String sql = "SELECT SUM(quantity) FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQty(resultSet.getInt("quantity"));
                Category category = new Category();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
                product.setCategory(category);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public void editProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQty());
            stmt.setInt(5, product.getCategory().getId()); // Предполагаем, что Category имеет метод getId()
            stmt.setInt(6, product.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the product.");
        }
    }
}


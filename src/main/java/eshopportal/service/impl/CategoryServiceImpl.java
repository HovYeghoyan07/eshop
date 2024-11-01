package eshopportal.service.impl;

import eshopportal.db.DbConnectionProvider;
import eshopportal.model.Category;
import eshopportal.model.Product;
import eshopportal.service.CategoryService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private Connection connection = DbConnectionProvider.getInstance().getConnection();

    public void add(Category category) {
        try {
            String sql = "INSERT INTO category (name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    }


    public Category getCategoryById(int id) {
        try(Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM Category WHERE id = "+id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return new Category(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public void deleteCategoryById(int id) {
        if (getCategoryById(id)==null){
            System.out.println("Category does not exist");
        }

        String sql = "DELETE FROM category WHERE id = "+id;
        try {
            Statement statement =connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Category was deleted");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Category";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void editCategory(Category category) {
        String sql = "UPDATE category SET name = ?  WHERE id = " + category.getId();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, category.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the product.");
        }
    }
}

package eshopportal.service;

import eshopportal.model.Product;

import java.util.List;

public interface ProductService {

    void addProduct(Product product);

    Product getProductById(int id);

List<Product> getAllProducts();

    double getProductMaxPrice();

    double getProductMinPrice();

    double getAveragePrice();

    void deleteProductById(int id);

    int getSumOfProducts();

    void editProduct(Product product);









}

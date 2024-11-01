package eshopportal;


import eshopportal.model.Category;
import eshopportal.model.Product;
import eshopportal.service.CategoryService;
import eshopportal.service.ProductService;
import eshopportal.service.impl.CategoryServiceImpl;
import eshopportal.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EshopPortal implements EshopCommands {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryService categoryService = new CategoryServiceImpl();
    private static final ProductService productService = new ProductServiceImpl();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            EshopCommands.printCommands();
            String command = scanner.nextLine();
            switch (command) {
                case "0":
                    isRun = false;
                    break;
                case "1":
                    addCategory();
                    break;
                case "2":
                    editCategoryById();
                    break;
                case "3":
                    deleteCategoryById();
                    break;
                case "4":
                    addProduct();
                    break;
                case "5":
                    editProductById();
                    break;
                case "6":
                    deleteProductById();
                    break;
                case "7":
                    printSumOfProducts();
                    break;
                case "8":
                    printMaxOfPrice();
                    break;
                case "9":
                    printMinOfPrice();
                    break;
                case "10":
                    printAvgOfPrice();
                    break;
                default:
                    System.out.println("Wrong command");


            }
        }

    }


    private static void printAvgOfPrice() {
        System.out.println(productService.getAveragePrice());
    }

    private static void printMinOfPrice() {
        System.out.println(productService.getProductMinPrice());
    }

    private static void printMaxOfPrice() {
        System.out.println(productService.getProductMaxPrice());
    }

    private static void printSumOfProducts() {
        System.out.println(productService.getSumOfProducts());
    }

    private static void deleteProductById() {
        List<Product> products = productService.getAllProducts();
        System.out.println("Please Input Product Id");
        String productId = scanner.nextLine();
        if (productId != null) {
            productService.deleteProductById(Integer.parseInt(productId));
        }
    }

    private static void deleteCategoryById() {
        printAllCategories();
        System.out.println("Please Input Category Id");
        String categoryId = scanner.nextLine();
        if (categoryId != null) {
            categoryService.deleteCategoryById(Integer.parseInt(categoryId));

        }
    }

    private static void addCategory() {
        System.out.println("Please input category name");
        String categoryName = scanner.nextLine();
        if (categoryName != null) {
            Category category = new Category();
            category.setName(categoryName);
            categoryService.add(category);
        }
    }


    private static void addProduct() {
        System.out.println("Please input category id for product:");
        categoryService.getAllCategories();
        String categoryId = scanner.nextLine();


        if (categoryId != null && !categoryId.isEmpty()) {
            Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));

            if (category != null) {
                System.out.println("Please input product name, description, price, quantity ");
                String[] productStr = scanner.nextLine().split(",");
                if (productStr.length == 4) {

                    Product product = Product.builder()
                            .name(productStr[0].trim())
                            .description(productStr[1].trim())
                            .price(Double.parseDouble(productStr[2].trim()))
                            .qty(Integer.parseInt(productStr[3].trim()))
                            .category(category)
                            .build();

                    productService.addProduct(product);
                    System.out.println("Product added successfully.");

                }
            }
        } else {
            System.out.println("Category not found.");
        }
    }

    private static void printAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            System.out.println(category.getId() + " " + category.getName());
        }
    }

    private static void editProductById() {
        try {
            System.out.println("Please input product id:");
            printAllProducts();
            String productId = scanner.nextLine();
            if (!productId.isEmpty()) {
                Product product = productService.getProductById(Integer.parseInt(productId));
                printAllCategories();
                System.out.println("Please input new category id:");
                String categoryId = scanner.nextLine();
                if (!categoryId.isEmpty()) {
                    Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));
                    if (category != null) {
                        product.setCategory(category);
                        System.out.println("Category updated successfully.");
                    } else {
                        System.out.println("Category not found.");
                    }
                }

                System.out.println("Please input product name, description, price, quantity ");
                String[] productStr = scanner.nextLine().split(",");

                if (productStr.length == 4) {
                    String newName = productStr[0];
                    String newDescription = productStr[1];
                    double newPrice;
                    int newQuantity;
                    try {
                        newPrice = Double.parseDouble(productStr[2]);
                        newQuantity = Integer.parseInt(productStr[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for price or quantity. Please input valid numbers.");
                        return;
                    }
                    product.setName(newName);
                    product.setDescription(newDescription);
                    product.setPrice(newPrice);
                    product.setQty(newQuantity);
                    productService.editProduct(product);

                    System.out.println("Product updated successfully.");
                } else {
                    System.out.println("Invalid input format. Please input exactly 4 values.");
                }


            } else {
                System.out.println("Product ID cannot be empty.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid symbol. Please input only numbers.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void printAllProducts() {
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }


    private static void editCategoryById() {
        printAllCategories();
        System.out.println("Please input category id");
        String categoryId = scanner.nextLine();
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));
            System.out.println("Please input category name");
            String categoryName = scanner.nextLine();
            category.setName(categoryName);
            categoryService.editCategory(category);
        } else {
            System.out.println("Wrong category id");
        }
    }
}













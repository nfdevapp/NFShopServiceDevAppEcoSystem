package repository;

import model.Product;

import java.util.*;

public class ProductRepo {
    private List<Product> products;

    private final Map<String, Product> mapProducts = new HashMap<>();

    public ProductRepo() {
        products = new ArrayList<>();
        products.add(new Product("1", "Apfel"));
    }

    public List<Product> getProducts() {
        return products;
    }

    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(mapProducts.get(id));
    }

    public Product addProduct(Product newProduct) {
        products.add(newProduct);
        return newProduct;
    }

    public void removeProduct(String id) {
        for (Product product : products) {
           if (product.id().equals(id)) {
               products.remove(product);
               return;
           }
        }
    }
}

package service;

import enums.OrderStatus;
import exception.ProductNotFoundException;
import model.Order;
import model.Product;
import repository.OrderMapRepo;
import repository.OrderRepo;
import repository.ProductRepo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();

        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }
    // Gibt alle Bestellungen mit bestimmtem Status zurück
    public List<Order> getOrdersByStatus(OrderStatus status) {
        List<Order> collect = orderRepo.getOrdersByStatus(status).stream()
                .filter(order -> order.status().equals(status))
                .collect(Collectors.toList());
        return collect;
    }

    // Aktualisiert den Bestellstatus (Lombok @With)
    public Order updateOrder(String orderId, OrderStatus status) {
        Order order = orderRepo.getOrderById(orderId);
        Order newOrder = order.withStatus(status);
        orderRepo.removeOrder(order.id());
        return orderRepo.addOrder(newOrder);
    }
}

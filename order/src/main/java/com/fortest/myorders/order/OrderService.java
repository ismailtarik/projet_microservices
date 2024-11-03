package com.fortest.myorders.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Create a new order
    public Order createOrder(OrderRequest orderRequest) {
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream().map(orderItemRequest ->
                OrderItem.builder()
                        .order(null)
                        .productId(orderItemRequest.getProductId())
                        .quantity(orderItemRequest.getQuantity())
                        .build()
        ).collect(Collectors.toList());

        Order order =  Order.builder().customerId(orderRequest.getCustomerId()).orderItems(orderItems).build();
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        return orderRepository.save(order);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get a specific order by ID
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    // Update an existing order
    public Optional<Order> updateOrder(Integer id, OrderRequest orderRequest) {
        return orderRepository.findById(id).map(order -> {
            // Update orderItems
            List<OrderItem> orderItems = orderRequest.getOrderItems().stream().map(orderItemRequest ->
                    OrderItem.builder()
                            .order(order)
                            .productId(orderItemRequest.getProductId())
                            .quantity(orderItemRequest.getQuantity())
                            .build()
            ).collect(Collectors.toList());
            order.setOrderItems(orderItems);
            order.setCustomerId(orderRequest.getCustomerId());
            return orderRepository.save(order);
        });
    }

    // Delete an order
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}

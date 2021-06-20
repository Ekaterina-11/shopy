package services;

import entity.Order;
import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.OrderRepository;
import repository.ProductRepository;
import repository.UserRepository;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    public Order findById(Long id){ return orderRepository.findById(id).get();}

    public List<Order> findAll(){ return orderRepository.findAll();}

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public List<Order> findByBuyerId(Long buyerId){ return orderRepository.findByBuyerId(buyerId); }
}

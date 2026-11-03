package com.hcl.hclassessment2.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.hclassessment2.entity.Cart;
import com.hcl.hclassessment2.entity.CartItem;
import com.hcl.hclassessment2.entity.Customer;
import com.hcl.hclassessment2.entity.CustomerOrder;
import com.hcl.hclassessment2.entity.OrderItem;
import com.hcl.hclassessment2.entity.Product;
import com.hcl.hclassessment2.repository.CartRepository;
import com.hcl.hclassessment2.repository.CustomerOrderRepository;
import com.hcl.hclassessment2.repository.CustomerRepository;
import com.hcl.hclassessment2.repository.ProductRepository;

@RestController
public class ShopController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @GetMapping("/")
    public String home() {
        return "Online Shopping API is running";
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/customers")
    @Transactional
    public Customer addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        Cart cart = new Cart();
        cart.setCustomer(savedCustomer);
        cartRepository.save(cart);

        return savedCustomer;
    }

    @GetMapping("/cart/{customerId}")
    public Cart getCart(@PathVariable Long customerId) {
        return cartRepository.findByCustomer_Id(customerId).orElseThrow();
    }

    @PostMapping("/cart/add")
    @Transactional
    public Cart addToCart(@RequestParam Long customerId,
                          @RequestParam Long productId,
                          @RequestParam(defaultValue = "1") int quantity) {
        Cart cart = cartRepository.findByCustomer_Id(customerId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getItems().add(cartItem);

        return cartRepository.save(cart);
    }

    @PostMapping("/orders/{customerId}")
    @Transactional
    public CustomerOrder placeOrder(@PathVariable Long customerId) {
        Cart cart = cartRepository.findByCustomer_Id(customerId).orElseThrow();

        CustomerOrder order = new CustomerOrder();
        order.setCustomer(cart.getCustomer());
        order.setOrderDate(LocalDateTime.now());

        double total = 0;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);

            total = total + (cartItem.getProduct().getPrice() * cartItem.getQuantity());
        }

        order.setTotalAmount(total);
        CustomerOrder savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    @GetMapping("/orders/{customerId}")
    public List<CustomerOrder> getOrders(@PathVariable Long customerId) {
        return orderRepository.findByCustomer_IdOrderByOrderDateDesc(customerId);
    }
}

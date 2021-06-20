package controllers;

import entity.Order;
import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.AuthService;
import services.OrderService;
import services.ProductService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/admProduct")
    public String admProduct(Model model) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return "admProduct";
    }

    @PostMapping("/admProduct")
    public String admProductPost(@Valid Product product, Model model){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", authService.findByUsername(currentUser.getName()));
        model.addAttribute("allUsers", authService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allOrders", orderService.findAll());
        productService.saveProduct(product);
        return "redirect:/administrator";
    }

    @PostMapping("/admDelProduct")
    public String admDelProduct(@RequestParam Long id, Model model){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", authService.findByUsername(currentUser.getName()));
        model.addAttribute("allUsers", authService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allOrders", orderService.findAll());
        productService.deleteProduct(id);
        return "redirect:/administrator";
    }

    @PostMapping("/cartConfirm")
    public String cartConfirm(@RequestParam String cart,@RequestParam String date, Model model) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", authService.findByUsername(currentUser.getName()));
        float currentSum = 0;
        String currentArticles = "";
        Order order = new Order();
        order.setBuyerId(authService.findByUsername(currentUser.getName()).getId());
        order.setBuyerUsername(authService.findByUsername(currentUser.getName()).getUsername());

        for(String s : cart.split(",")){
            order.addCart(productService.findById(Long.parseLong(s)));
            currentSum += productService.findById(Long.parseLong(s)).getCost();
        }

        order.setDate(date);
        order.setSum(currentSum);
        order.setArticles(cart);
        order.setStatus("Передан в обработку");
        order.setBuyerAddress(authService.findByUsername(currentUser.getName()).getAddress());

        orderService.saveOrder(order);
        return "redirect:/profile";
    }
}

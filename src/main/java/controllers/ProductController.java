package controllers;

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
import services.ProductService;

import javax.validation.Valid;

@Controller
public class ProductController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProductService productService;

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
        System.out.println(product);
        //productService.saveProduct(product);
        return "administrator";
    }
}

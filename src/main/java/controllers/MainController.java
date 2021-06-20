package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import services.AuthService;
import services.ProductService;

@Controller
public class MainController {

    @Autowired
    private AuthService authService;
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(!currentUser.getName().equals("anonymousUser")){
            model.addAttribute("allProducts", productService.findAll());
            model.addAttribute("currentUser", authService.findByUsername(currentUser.getName()));
        }
        return "index";
    }
}

package controllers;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.AuthService;
import services.OrderService;
import services.ProductService;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@Valid User user, Model model){

        if (authService.findByUsername(user.getUsername()) != null){
            model.addAttribute("errors", "Пользователь с данным логином уже существует");
            return "registration";
        }
        if (authService.findByEmail(user.getEmail()) != null){
            model.addAttribute("errors", "Пользователь с данной почтой уже существует");
            return "registration";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())){
            model.addAttribute("errors", "Ошибка при повторении пароля");
            return "registration";
        }

        authService.registration(user);
        return "login";
    }

    @GetMapping("/activationProfile/{randomCode}")
    public String activationProfile(@PathVariable String randomCode, Model model){
        if(authService.activateMe(randomCode)){
            model.addAttribute("errors", "Аккаунт активирован");
            return "redirect:/login";
        }else{
            model.addAttribute("errors", "Ошибка активации");
            return "redirect:/login";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("myOrders", orderService.findByBuyerId(authService.findByUsername(currentUser.getName()).getId()));
        if(!currentUser.getName().equals("anonymousUser")){
            model.addAttribute("currentUser", authService.findByUsername(currentUser.getName()));
        }
        return "profile";
    }

    @GetMapping("/administrator")
    public String administrator(Model model) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", authService.findByUsername(currentUser.getName()));
        model.addAttribute("allUsers", authService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allOrders", orderService.findAll());
        return "administrator";
    }

    @PostMapping("/uploadAvatarPhoto")
    public String uploadAvatarPhoto(@RequestParam String avatarPhoto,Model model){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", authService.findByUsername(currentUser.getName()));
        model.addAttribute("allUsers", authService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allOrders", orderService.findAll());
        authService.uploadPhoto(authService.findByUsername(currentUser.getName()), avatarPhoto);
        return "profile";
    }

    @PostMapping("/admCustomer")
    public String admCustomer(@RequestParam Long id, Model model){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", authService.findByUsername(currentUser.getName()));
        model.addAttribute("allUsers", authService.findAll());
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allOrders", orderService.findAll());
        authService.deleteUser(id);
        return "redirect:/administrator";
    }
}

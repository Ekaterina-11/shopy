package controllers;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import services.AuthService;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

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
}

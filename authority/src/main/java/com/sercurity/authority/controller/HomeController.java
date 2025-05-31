package com.sercurity.authority.controller;

import com.sercurity.authority.model.User;
import com.sercurity.authority.repository.UserRepository;
import com.sercurity.authority.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public HomeController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @GetMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/profile")
    public String profile(){
        return "ptit";
    }

    @GetMapping("/data")
    public String getProtectedData() {
        return "Đây là dữ liệu được bảo vệ! Bạn đã xác thực thành công.";
    }

//    @GetMapping("/profile")
//    public String profile(Model model, @AuthenticationPrincipal OidcUser principal) {
//        model.addAttribute("user", principal);
//        return "profile";
//    }
}

package org.bisha.ecommercefinal.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.bisha.ecommercefinal.dtos.UserDto;
import org.bisha.ecommercefinal.dtos.UserLoginDto;
import org.bisha.ecommercefinal.dtos.UserRegisterDto;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.exceptions.WrongPasswordException;
import org.bisha.ecommercefinal.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "signin";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute UserLoginDto userLoginDto,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userLoginDto", userLoginDto);
            return "signin";
        }

        try {
            UserDto responseDto = userService.login(userLoginDto);

            // Store username in session
            HttpSession session = request.getSession();
            session.setAttribute("user", responseDto);

            return "redirect:/";
        } catch (ResourceNotFoundException e) {
            logger.error("User not found", e);
            bindingResult.rejectValue("username", "error.username", "User not found");
        } catch (WrongPasswordException e) {
            logger.error("Wrong password", e);
            bindingResult.rejectValue("password", "error.password", "Wrong password");
        } catch (Exception e) {
            logger.error("Login failed", e);
            bindingResult.reject("login", "Login failed due to an unexpected error");
        }

        model.addAttribute("userLoginDto", userLoginDto);
        return "signin";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute UserRegisterDto userRegisterDto,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userRegisterDto", userRegisterDto);
            return "register";
        }

        try {
            UserDto responseDto = userService.register(userRegisterDto);

            // Store username in session
            HttpSession session = request.getSession();
            session.setAttribute("user", responseDto);

            return "redirect:/";
        } catch (Exception e) {
            logger.error("Registration failed", e);
            bindingResult.reject("register", "Registration failed: " + e.getMessage());
            model.addAttribute("userRegisterDto", userRegisterDto);
            return "register";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         RedirectAttributes redirectAttributes) {
        // Invalidate the session
        request.getSession().invalidate();

        // Add flash attribute for the message (will survive redirect)
        redirectAttributes.addFlashAttribute("message", "Logged out successfully");

        return "redirect:/";
    }
}

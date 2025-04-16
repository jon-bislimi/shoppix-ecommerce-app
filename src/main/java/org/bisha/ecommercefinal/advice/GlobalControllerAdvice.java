//package org.bisha.ecommercefinal.advice;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.bisha.ecommercefinal.dtos.UserDto;
//import org.bisha.ecommercefinal.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//
//    private final UserService userService;
//
//    @Autowired
//    public GlobalControllerAdvice(UserService userService) {
//        this.userService = userService;
//    }
//
//    @ModelAttribute("user")
//    public void addUserToModel(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            String username = (String) session.getAttribute("username");
//            if (username != null) {
//                try {
//                    UserDto userDto = userService.getUserByUsername(username);
//                    model.addAttribute("user", userDto);
//                } catch (Exception e) {
//                    model.addAttribute("user", null);
//                }
//            } else {
//                model.addAttribute("user", null);
//            }
//        } else {
//            model.addAttribute("user", null);
//        }
//    }
//
//}
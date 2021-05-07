package ru.luxoft.client.controller;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.luxoft.client.model.User;
import ru.luxoft.client.property.QueueProperty;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class LoginController {

    private final JmsTemplate jmsTemplate;

    private final QueueProperty queueProperty;

    public LoginController(JmsTemplate jmsTemplate, QueueProperty queueProperty) {
        this.jmsTemplate = jmsTemplate;
        this.queueProperty = queueProperty;
    }

    @GetMapping("/login")
    public String getLoginPage(Authentication authentication, ModelMap model, HttpServletRequest request) {
        if (authentication != null) {
            return "redirect: /user";
        }
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @GetMapping("/user")
    public String getIndexPage(Authentication authentication, ModelMap model) {
        jmsTemplate.convertAndSend(queueProperty.getReadUserByLogin(), authentication.getName());
        User user = Objects.requireNonNull((User) jmsTemplate.receiveAndConvert(queueProperty.getReadUserByLoginAnswer()));
        String name = user.getName();
        String role = user.getRoles().iterator().next().getAuthority();
        if (role.equals("ROLE_ADMIN")) {
            model.addAttribute("isAdmin", true);
        }

        model.addAttribute("user", name);
        return "welcome";
    }

    @GetMapping("/")
    public String getLoginPage(Authentication authentication, Model model,HttpServletRequest request) {
        if (authentication != null) {
            model.addAttribute("user", request.getParameter("name"));
            return "redirect:/welcome";
        }
        return "login";
    }

    @GetMapping("/welcome")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String getWelcome() {
        return "welcome";
    }

}

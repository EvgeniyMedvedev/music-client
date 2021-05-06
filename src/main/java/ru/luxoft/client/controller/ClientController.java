package ru.luxoft.client.controller;

import lombok.Data;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ClientController.
 *
 * @author Evgeniy_Medvedev
 */
@Controller
@Data
public class ClientController {

    private final JmsTemplate jmsTemplate;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("user", "user");
        return "hello";
    }

    @GetMapping("/crud")
    public String crud() {
        return "users";
    }

    //todo: In progress
    @GetMapping("/admin")
    public String admin() {
        return "redirect:/";
    }


}
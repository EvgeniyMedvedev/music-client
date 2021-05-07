package ru.luxoft.client.controller;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.luxoft.client.model.User;
import ru.luxoft.client.property.QueueProperty;

@Controller
public class UserController {

    private final JmsTemplate jmsTemplate;

    private final QueueProperty queueProperty;

    public UserController(JmsTemplate jmsTemplate, QueueProperty queueProperty) {
        this.jmsTemplate = jmsTemplate;
        this.queueProperty = queueProperty;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        jmsTemplate.convertAndSend(queueProperty.getCreateUser(), userForm);

        return "redirect:/user";
    }

}

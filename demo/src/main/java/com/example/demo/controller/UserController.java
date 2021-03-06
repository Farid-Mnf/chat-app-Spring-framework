package com.example.demo.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.service.ConversationService;
import com.example.demo.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/findContact/{id}") // loop through all users and compare their name with input value
	public String findContact(@PathVariable("id") long id, @RequestParam("contactName") String contactName, Model model) {
		Iterable<User> users = userService.getAllContacts(id);
		Set<User> contacts = new HashSet<>();
		for(User user : users) {
			if(user.getName().toLowerCase().contains(contactName.toLowerCase())) {
				contacts.add(user);
			}
		}
		// store contacts and user in model for HTML rendering
		model.addAttribute("contacts", contacts);
		model.addAttribute("user", userService.getUser(id).get());
		return "contact-result";
	}
	
	@RequestMapping("/search-contact/{id}")
	public String searchContact(@PathVariable("id") long id, Model model) {
		User user = userService.getUser(id).get();
		model.addAttribute("user", user);
		return "contact-finder";
	}


}

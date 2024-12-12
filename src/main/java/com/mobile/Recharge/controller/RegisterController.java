package com.mobile.Recharge.controller;

import com.mobile.Recharge.dto.User;
import com.mobile.Recharge.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

	@Autowired
	private UserService service;

	// Display registration form
	@GetMapping("/")
	public String showForm(ModelMap map) {
		map.put("user", new User()); // Create an empty user object for form binding
		return "register.html";
	}

	// Handle registration form submission
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap map) {
		if (result.hasErrors()) {
			return "register.html"; // Return the form with errors if validation fails
		}
		return service.register(user, result, map); // Delegate to service for further processing
	}

	// Show the payment form (redirect to payment.html)
	@GetMapping("/payment")
	public String payment() {
		return "payment.html";
	}

	// Handle payment submission
	@PostMapping("/payment")
	public String payment(@RequestParam int amount, ModelMap map) throws Exception {
		return service.payment(amount, map); // Handle Razorpay integration and payment logic
	}

	// Show success page after payment
	@PostMapping("/success")
	public String success(@ModelAttribute User user, ModelMap map) {
		map.put("name", user.getName()); // Display user's name
		// map.put("amount", user.getAmount()); // Display the entered amount
		return "paymentSuccess.html"; // Redirect to the success page
	}

}

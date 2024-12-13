package com.mobile.Recharge.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.mobile.Recharge.dto.User;
import com.mobile.Recharge.helper.MailHelper;
import com.mobile.Recharge.repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	MailHelper mailHelper;

	public String register(User user, BindingResult result, ModelMap map) {
		if (!user.getPassword().equals(user.getConfirmPassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword",
					"Password and Confirm Password does not match");
		if (repository.existsByEmail(user.getEmail()))
			result.rejectValue("email", "error.email", "Email already exists");

		if (result.hasErrors())
			return "register.html";

		else {
			repository.save(user);
			map.put("success", "Registered Successfully, Now Enter the Amount to recharge " + +user.getAmount());
			mailHelper.sendEmail(user);
			return "payment.html";
		}
	}

	public String payment(int amount, ModelMap map) throws RazorpayException {

		RazorpayClient razorpay = new RazorpayClient("rzp_test_LjeUdOv6BXQ9uvy", "u8Q7IpDTKcedhm0wa14Ud4Muy");

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount * 100);
		orderRequest.put("currency", "INR");

		Order order = razorpay.orders.create(orderRequest);
		map.put("key", "rzp_test_LjeUdOv6BXQ9uv");
		map.put("amount", amount * 100);
		map.put("orderId", order.get("id"));
		return "razorpay.html";
	}

}

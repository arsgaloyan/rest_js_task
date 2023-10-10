package com.example.secyrity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

	@GetMapping("/")
	public String startPage() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String getLoginPage(@RequestParam(value = "error", required = false) String error,
							   @RequestParam(value = "logout", required = false) String logout,
							   Model model) {
		model.addAttribute("error", error != null);
		model.addAttribute("logout", logout != null);
		return "login";
	}
}
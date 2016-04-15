package com.interviewmanagement.main.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interviewmanagement.main.model.User;
import com.interviewmanagement.main.service.CandidateService;

@Controller
public class AdminController {
	
	private CandidateService candidateService;
	
	private @Autowired ApplicationContext context;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired(required=true)
	@Qualifier(value="candidateService")
	public void setCandidateService(CandidateService ps){
		this.candidateService = ps;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listUsers", this.candidateService.listUsers());
		return "adminPage";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String openCandidatePage(Model model, Principal principal, HttpServletRequest request) {
		if(request.getParameter("id") != null) {
			Integer id = Integer.valueOf(request.getParameter("id"));
			User result = candidateService.getUserById(id);
			model.addAttribute("user", result);
			model.addAttribute("title", result.getName());

		} else {
			model.addAttribute("user", new User());
			model.addAttribute("title", "Novo");
		}

		return "userPage";
	}
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveCandidate(@ModelAttribute("user") @Validated User user, 
			BindingResult result, Model model) {
		Integer id = null;
		if(user.getUserId() != null) {
			id = this.candidateService.createOdUpdateUser(user);
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String pass = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(13) + 8);
			user.setPassword(passwordEncoder.encode(pass));
			id = this.candidateService.createOdUpdateUser(user);
			
			SimpleMailMessage email = new SimpleMailMessage();
	        email.setTo(user.getEmail());
	        email.setSubject(context.getMessage("mail.subject", null, Locale.getDefault()) + " " + user.getUsername());
	        String text = context.getMessage("mail.text", null, Locale.getDefault());
	        
	        email.setText(text + " " + pass);
	        
	        mailSender.send(email);
			
		}
		return "redirect:/user?id=" + id;
	}
	
	@RequestMapping("/removeUser/{id}")
	public String removeCandidate(Model model, @PathVariable("id") int id, HttpServletRequest request){

		this.candidateService.removeUser(id);
		request.getSession().setAttribute("isFromRemove", true);

		return "redirect:/admin";
	}
}

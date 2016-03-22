package com.interviewmanagement.main.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.interviewmanagement.main.model.Candidate;
import com.interviewmanagement.main.model.Language;
import com.interviewmanagement.main.model.Locality;
import com.interviewmanagement.main.model.RefData;
import com.interviewmanagement.main.model.User;
import com.interviewmanagement.main.service.CandidateService;
import com.interviewmanagement.main.utils.LanguageEditor;
import com.interviewmanagement.main.utils.LocalityEditor;
import com.interviewmanagement.main.utils.UserEditor;

@Controller
public class CandidateController {

	private @Autowired LocalityEditor localityEditor;
	private @Autowired LanguageEditor languageEditor;
	private @Autowired UserEditor userEditor;
	private CandidateService candidateService;

	@Autowired(required=true)
	@Qualifier(value="candidateService")
	public void setCandidateService(CandidateService ps){
		this.candidateService = ps;
	}
	
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String openCandidatePage(Model model, Principal principal, HttpServletRequest request) {
		if(request.getParameter("id") != null) {
			Integer id = Integer.valueOf(request.getParameter("id"));
			Candidate result = candidateService.getCandidateById(id);
			model.addAttribute("candidate", result);
			model.addAttribute("title", result.getName());

		} else {
			model.addAttribute("candidate", new Candidate());
			model.addAttribute("title", "Novo");
		}

		List<RefData> listOfSituations = candidateService.listRefData("SITUATION");
		model.addAttribute("listSituations", listOfSituations);

		List<RefData> listOfLevels = candidateService.listRefData("LEVEL");
		model.addAttribute("listLevels", listOfLevels);

		List<Locality> listOfLocals = candidateService.lislLocalities();
		model.addAttribute("listOfILocals", listOfLocals);

		List<Language> listOfLanguages = candidateService.lisLanguages();
		model.addAttribute("listOfILanguages", listOfLanguages);

		List<User> listOfUsers = candidateService.listUsers();
		model.addAttribute("listOfUsers", listOfUsers);

		return "candidatePage";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCandidate(@ModelAttribute("candidate") @Validated Candidate c, 
			BindingResult result, Model model) {

			Integer id = this.candidateService.createOdUpdateCandidate(c);

			return "redirect:/candidate?id=" + id;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		binder.registerCustomEditor(Locality.class, this.localityEditor);
		binder.registerCustomEditor(Language.class, this.languageEditor);
		binder.registerCustomEditor(User.class, this.userEditor);
	}
}

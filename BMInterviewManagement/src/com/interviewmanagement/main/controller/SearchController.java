package com.interviewmanagement.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
public class SearchController {

	private @Autowired LocalityEditor localityEditor;
	private @Autowired LanguageEditor languageEditor;
	private @Autowired UserEditor userEditor;
	private @Autowired ApplicationContext context;

	private CandidateService candidateService;
	@Value("${repository}")
	private String repository;

	private static final int BUFFER_SIZE = 4096;

	@Autowired(required=true)
	@Qualifier(value="candidateService")
	public void setCandidateService(CandidateService ps){
		this.candidateService = ps;
	}
	@RequestMapping(value = "/common", method = RequestMethod.GET)
	public String common(HttpServletRequest req, HttpServletResponse resp) {
		String url = null;
		if(req.isUserInRole("ROLE_USER")) {
			url = "redirect:/manageCV";
		}else if(req.isUserInRole("ROLE_ADMIN")) {
			url = "redirect:/admin";
		}
		return url;
	}

	@RequestMapping(value = "/manageCV", method = RequestMethod.GET)
	public String loginPage(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response) {
		Candidate candidate = new Candidate();

		if(request.getSession().getAttribute("loggedUser") == null) {
			String username = principal.getName();
			User user = new User();
			user.setUsername(username);
			request.getSession().setAttribute("loggedUser", candidateService.getUserFullName(user));
		}

		if(request.getSession().getAttribute("isFromRemove") != null &&
				((Boolean)request.getSession().getAttribute("isFromRemove"))) {
			request.getSession().setAttribute("isFromRemove", false);
			List<Candidate> result = candidateService.listCandidate(model.asMap().get("candidate") == null ? candidate : (Candidate) model.asMap().get("candidate"));
			model.addAttribute("result", result);
		}
		List<Candidate> result = null;
		if(request.getSession().getAttribute("isFromSearch") != null &&
				((Boolean)request.getSession().getAttribute("isFromSearch"))) {
			request.getSession().setAttribute("isFromSearch", false);
			result = (List<Candidate>) request.getSession().getAttribute("searchResult");
			//			request.getSession().setAttribute("searchResult", null);
			model.addAttribute("result", result);
		}

		model.addAttribute("title", "Manage CV");
		model.addAttribute("candidate", request.getSession().getAttribute("candidate") == null ? candidate : request.getSession().getAttribute("candidate"));
		model.addAttribute("result", model.asMap().get("result") == null ? new ArrayList<Candidate>() : model.asMap().get("result"));
		List<RefData> listOfLevels = candidateService.listRefData("LEVEL");
		model.addAttribute("listLevels", listOfLevels);

		List<Locality> listOfLocals = candidateService.lislLocalities();
		model.addAttribute("listOfILocals", listOfLocals);

		List<Language> listOfLanguages = candidateService.lisLanguages();
		model.addAttribute("listOfILanguages", listOfLanguages);

		List<User> listOfUsers = candidateService.listUsers();
		List<User> users = new ArrayList<User>();
		for (User user : listOfUsers) {
			if(user.getRole().equals("ROLE_ADMIN")) {
				continue;
			}
			users.add(user);
		}
		model.addAttribute("listOfUsers", users);

		request.getSession().removeAttribute("param");
		return "manageCVPage";
	}

	@RequestMapping(value = "/manageCV", method = RequestMethod.POST, params = { "search" })
	public String executeSearch(Model model, @RequestParam String search, @ModelAttribute("candidate") Candidate c, HttpServletRequest request) {
		request.getSession().setAttribute("isFromSearch", true);
		List<Candidate> result = candidateService.listCandidate(c);
		model.addAttribute("candidate", c);
		request.getSession().setAttribute("searchResult", result);
		request.getSession().setAttribute("candidate", c);
		request.getSession().setAttribute("param", search);
		return "redirect:/manageCV";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

		binder.registerCustomEditor(Locality.class, this.localityEditor);
		binder.registerCustomEditor(Language.class, this.languageEditor);
		binder.registerCustomEditor(User.class, this.userEditor);
	}

	@RequestMapping("/remove/{id}")
	public String removeCandidate(Model model, @PathVariable("id") int id, HttpServletRequest request){

		Candidate c = this.candidateService.getCandidateById(id);
		if(c.getFilename() != null) {
			File file = new File(repository + System.getProperty("file.separator") + c.getFilename());
			file.delete();
		}


		this.candidateService.removeCandidate(id);
		request.getSession().setAttribute("isFromRemove", true);

		return "redirect:/manageCV";
	}

	@RequestMapping(value="/export", method = RequestMethod.GET)
	public void doDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Candidate> result = (List<Candidate>) request.getSession().getAttribute("searchResult");
		if(result != null) {
			// get absolute path of the application
			File exportFile = File.createTempFile("export_"+new Date().getTime(), ".xls");
			FileOutputStream outputStream = new FileOutputStream(exportFile);

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");  

			HSSFRow rowhead = sheet.createRow((short)0);
			rowhead.createCell(0).setCellValue(context.getMessage("candidate.name", null, Locale.getDefault()));
			rowhead.createCell(1).setCellValue(context.getMessage("candidate.level", null, Locale.getDefault()));
			rowhead.createCell(2).setCellValue(context.getMessage("candidate.phone", null, Locale.getDefault()));
			rowhead.createCell(3).setCellValue(context.getMessage("candidate.email", null, Locale.getDefault()));
			rowhead.createCell(4).setCellValue(context.getMessage("candidate.birthDate", null, Locale.getDefault()));
			rowhead.createCell(5).setCellValue(context.getMessage("candidate.local", null, Locale.getDefault()));
			rowhead.createCell(6).setCellValue(context.getMessage("candidate.language", null, Locale.getDefault()));
			rowhead.createCell(7).setCellValue(context.getMessage("candidate.manager", null, Locale.getDefault()));
			rowhead.createCell(8).setCellValue(context.getMessage("candidate.year", null, Locale.getDefault()));
			rowhead.createCell(9).setCellValue(context.getMessage("candidate.situation", null, Locale.getDefault()));
			rowhead.createCell(10).setCellValue(context.getMessage("candidate.remuneration", null, Locale.getDefault()));

			short rowNumb = 1;
			for (Candidate c : result) {
				HSSFRow row = sheet.createRow((short)rowNumb);
				row.createCell(0).setCellValue(c.getName());
				row.createCell(1).setCellValue(c.getLevel());
				row.createCell(2).setCellValue(c.getPhone());
				row.createCell(3).setCellValue(c.getEmail());
				SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyy");
				row.createCell(4).setCellValue(dt1.format(c.getBirthDate()));
				row.createCell(5).setCellValue(c.getLocal() != null ? c.getLocal().getName() : "");
				row.createCell(6).setCellValue(c.getLanguage() != null ? c.getLanguage().getName() : "");
				row.createCell(7).setCellValue(c.getUser() != null ? c.getUser().getFullName() : "");
				if(c.getProfessionalInfo() != null) {
					row.createCell(8).setCellValue(c.getProfessionalInfo().getYear() != null ? c.getProfessionalInfo().getYear() + "" : "");
					row.createCell(9).setCellValue(c.getProfessionalInfo().getSituation() != null ? c.getProfessionalInfo().getSituation() + "" : "");
					row.createCell(10).setCellValue(c.getProfessionalInfo().getRemuneration() != null ? c.getProfessionalInfo().getRemuneration() : "");
				}
				rowNumb++;
			}


			workbook.write(outputStream);
			outputStream.close();
			FileInputStream inputStream = new FileInputStream(exportFile);
			// get MIME type of the file
			String mimeType =  "application/octet-stream";
			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) exportFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					exportFile.getName());
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		}
	}
	
}

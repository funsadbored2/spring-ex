package com.emaillist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emaillist.dao.EmaillistDao;
import com.emaillist.vo.EmaillistVo;
@Controller
public class EmaillistController {
	

	
	@Autowired
	private EmaillistDao dao;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		List<EmaillistVo> list = dao.getList();
		System.out.println(list.toString());
		model.addAttribute("list",list);
		return "/WEB-INF/views/list.jsp";
	}
	
	@RequestMapping("/add")
	public String add(EmaillistVo vo) {
		
		dao.insert(vo);
		
		return "redirect:/list";
	}
	
	@RequestMapping("/form")
	public String form() {
		
		return "/WEB-INF/views/form.jsp";
	}
	
	
	
}

package com.bookStore01.admin.member.controller;

import java.util.Map;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface AdminMemberController {
	public ModelAndView adminGoodsMain(@RequestParam Map<String, String> dateMap, HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView memberDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public void modifyMemberInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView deleteMember(HttpServletRequest request, HttpServletResponse response)  throws Exception;
}

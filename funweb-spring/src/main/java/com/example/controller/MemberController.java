package com.example.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.MemberVo;
import com.example.service.MemberService;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
//	@Autowired
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}
//	이렇게도 사용이 가능하지만 코드가 길어지니까 위에걸로 사용


	//	@RequestMapping(value = "/join", method = RequestMethod.GET)
	@GetMapping("/join")
	public void join() {
		log.info("GET - join() �몄���");
//		return "member/join";   // 硫����� 由ы�댄������ String�� 寃쎌��
	}
	
	
	@PostMapping("/join")
	public String join(MemberVo memberVo) {
		log.info("POST - join() �몄���");
		
		// ����媛��� ��吏� �ㅼ��
		memberVo.setRegDate(new Timestamp(System.currentTimeMillis()));
		log.info("memberVo : " + memberVo);
		
		// ����媛��� 泥�由�
		memberService.addMember(memberVo);
		
		return "redirect:/member/login";
	}
	
	
	@GetMapping("/joinIdDupCheck")
	public String joinIdDupCheck(String id, Model model) {
		log.info("id : " + id);
		
		int count = memberService.getCountById(id);
		
		// Model ���� 媛�泥댁�� 酉�(JSP)���� �ъ�⑺�� �곗�댄�곕�� ���ν��湲�
		model.addAttribute("id", id);
		model.addAttribute("count", count);
		
		return "member/joinIdDupCheck";
	} // joinIdDupCheck
	
	
	@GetMapping(value = "/ajax/joinIdDupChk", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody // 由ы�� 媛�泥대�� JSON 臾몄���대� 蹂����댁�� ���듭�� 以�
	public Map<String, Boolean> ajaxJoinIdDupChk(String id) {
		
		int count = memberService.getCountById(id);

		Map<String, Boolean> map = new HashMap<>();
		if (count == 0) {
			map.put("isIdDup", false);
		} else { // count == 1
			map.put("isIdDup", true);
		}
		
		return map;
	}
	
	
	@GetMapping("/login")
	public void login() {
//		return "member/login";
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(String id, String passwd, 
			@RequestParam(defaultValue = "false") boolean keepLogin,
			HttpSession session,
			HttpServletResponse response) {
		
		int check = memberService.userCheck(id, passwd);
		
		// 濡�洹몄�� �ㅽ�⑥��
		if (check != 1) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");
			
			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("  alert('���대�� ���� �⑥�ㅼ����媛� �쇱���吏� ���듬����.');");
			sb.append("  history.back();");
			sb.append("</script>");
			
			return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
		}
		
		// 濡�洹몄�� �깃났��
		// �몄���� ���대�� ����(濡�洹몄�� �몄�)
		session.setAttribute("id", id);
		
		if (keepLogin) { // keepLogin == true
			Cookie cookie = new Cookie("id", id);
			cookie.setMaxAge(60 * 10);  // 荑��� ���⑥��媛� 10遺�
			cookie.setPath("/");

			response.addCookie(cookie);
		}
		
//		return "redirect:/";
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/"); // 由щ�ㅼ�대���� 寃쎈�瑜� Location�쇰� �ㅼ��
		// 由щ�ㅼ�대���몄�� 寃쎌�곕�� HttpStatus.FOUND 瑜� 吏����댁�� ��
		return new ResponseEntity<String>(headers, HttpStatus.FOUND);
	} // login
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		// �몄�� 珥�湲고��
		session.invalidate();
		
		// 濡�洹몄�� ������吏��� 荑��ㅺ� 議댁�ы��硫� ����
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("id")) {
					cookie.setMaxAge(0); // ���⑥��媛� 0
					cookie.setPath("/"); // 寃쎈��� ���깊������ ���쇳��寃� �ㅼ���댁�� ������
					
					response.addCookie(cookie); // ������ 荑��ㅼ��蹂대�� 異�媛�
				}
			}
		}
		
		return "redirect:/";
	} // logout
	
}










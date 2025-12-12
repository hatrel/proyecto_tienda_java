package com.sergio.clienteservidor.controllers.loginAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sergio.clienteservidor.controllers.admin.PrendasController;

import jakarta.servlet.http.HttpServletRequest;

//controlador que se encarga de identificar a un admin

@Controller
public class LoginAdminController {

	@Autowired
	private PrendasController prendasController;
	private MessageSource messageSource;
	
	@RequestMapping("loginAdmin")
	public String loginAdmin() {
		return "admin/loginAdmin";
	}
	
	@RequestMapping("logoutAdmin")
	public String logoutAdmin(HttpServletRequest request){
		request.getSession().invalidate();
		String idiomaActual = messageSource.getMessage("idioma", null, LocaleContextHolder.getLocale());
		request.getSession().invalidate();
		System.out.println("idiomaActual: " + idiomaActual);					
		return "tienda_" + idiomaActual;
	}
	
}

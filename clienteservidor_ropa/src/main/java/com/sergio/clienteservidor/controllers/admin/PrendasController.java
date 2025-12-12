package com.sergio.clienteservidor.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sergio.clienteservidor.model.Prenda;
import com.sergio.clienteservidor.servicios.ServicioCategorias;
import com.sergio.clienteservidor.servicios.ServicioPrendas;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/")
public class PrendasController {

	@Autowired
	private ServicioPrendas servicioPrendas;
	
	@Autowired
	private ServicioCategorias servicioCategorias;
	
	
	@RequestMapping("editarPrenda")
	public String editarPrenda( @RequestParam("id") Long id, Model model) {
		Prenda prenda= servicioPrendas.obtenerPrendaPorId(id);
		prenda.setIdCategoria(prenda.getCategoria().getId());
		model.addAttribute("prendaEditar",prenda);
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
		return "admin/prendas_editar";
	}
	
	@RequestMapping("guardarCambiosPrenda")
	public String guardarCambiosPrenda(@ModelAttribute("prendaEditar")Prenda prendaEditar, Model model) {
		servicioPrendas.actualizarPrenda(prendaEditar);
		return obtenerPrendas(model);
	}
	
	
	@RequestMapping("registrarPrenda")
	public String registrarPrenda(Model model) {
		Prenda p = new Prenda();
		p.setPrecio(1);
		model.addAttribute("xxx", p);
		//Vamos a meter las categorias en model para que le lleguen a la vista
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
		
		
		return "admin/prendas_registro";
	}
	
	@RequestMapping("guardarPrenda")
	public String guardarPrenda(@ModelAttribute("xxx") @Valid Prenda nuevoPrenda, BindingResult resultadoValidaciones, Model model ) {
		
		if(resultadoValidaciones.hasErrors()) {
			return "admin/prendas_registro";
		}
		
		servicioPrendas.registrarPrenda(nuevoPrenda);
		return obtenerPrendas(model);
	}
	
	@RequestMapping("obtenerPrendas")
	public String obtenerPrendas(Model model) {		
		model.addAttribute("prendas", servicioPrendas.obtenerPrendas());
		return "admin/prendas";
	}
	
	@RequestMapping("borrarPrenda")
	public String borrarPrenda( @RequestParam("id") Long id, Model model ) {
		servicioPrendas.borrarPrenda(id);
		return obtenerPrendas(model);
	}
	
}







package com.sergio.clienteservidor.RESTcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.clienteservidor.model.Prenda;
import com.sergio.clienteservidor.servicios.ServicioPrendas;

@RestController
@RequestMapping("prendasREST/")
public class PrendasREST {
	
	@Autowired
	private ServicioPrendas servicioPrendas;

	@RequestMapping("obtener")
	public List<Map<String, Object>> obtenerPrendas() {		
		return servicioPrendas.obtenerPrendasParaFormarJSON();
//		return servicioLibros.obtenerLibros();
	}
	
	@RequestMapping("MostrarDetalles")
	public Map<String, Object> MostrarDetalles (@RequestParam Long id) {
		return servicioPrendas.obtenerPrendaParaFormarJSONPorId(id);
	}
	
}

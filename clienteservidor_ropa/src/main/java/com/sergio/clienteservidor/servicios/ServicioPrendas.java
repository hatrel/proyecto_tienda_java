package com.sergio.clienteservidor.servicios;

import java.util.List;
import java.util.Map;

import com.sergio.clienteservidor.model.Prenda;


public interface ServicioPrendas {
	
	void registrarPrenda(Prenda prenda);
	
	List<Prenda> obtenerPrendas();
	
	void borrarPrenda(long id);
	
	void actualizarPrenda(Prenda prenda);

	Prenda obtenerPrendaPorId(long long1);
	
	// para la parte publica, servicios REST
	
	List< Map<String, Object> > obtenerPrendasParaFormarJSON ();
	
	Map<String, Object> obtenerPrendaParaFormarJSONPorId (long long1);

	
}








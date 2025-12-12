package com.sergio.clienteservidor.servicios;

import java.util.List;

import com.sergio.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.sergio.clienteservidor.model.Pedido;

public interface ServicioPedidos {
	
	//Gestion en administracion
	List<Pedido> obtenerPedidos();
	Pedido obtenerPedidoporId(int idPedido);
	void actualizarPedido(int idPedido, String estado);
	
	//operaciones ajax
	void procesarPaso1(String nombre, String direccion, String provincia, int codigo, int edad, String genero, int idUsuario);
	void procesarPaso2(String tarjeta, String numero, String titular, int idUsuario);
	ResumenPedido procesarPaso3(String conocer, int idUsuario);
	void confirmarPedido(int idUsuario);

}

package com.sergio.clienteservidor.serviciosJPAImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sergio.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.sergio.clienteservidor.constantes.Estados;
import com.sergio.clienteservidor.constantesSQL.ConstantesSQL;
import com.sergio.clienteservidor.model.Carrito;
import com.sergio.clienteservidor.model.Pedido;
import com.sergio.clienteservidor.model.ProductoPedido;
import com.sergio.clienteservidor.model.Usuario;
import com.sergio.clienteservidor.servicios.ServicioCarrito;
import com.sergio.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioPedidosIMPL implements ServicioPedidos{
	
	@Autowired
	private ServicioCarrito servicioCarrito;
	
	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public List<Pedido> obtenerPedidos() {
		return entityManager.createQuery("select p from Pedido p order by p.id desc").getResultList();
	}

	@Override
	public Pedido obtenerPedidoporId(int idPedido) {
		return (Pedido)entityManager.find(Pedido.class, idPedido);
	}

	@Override
	public void actualizarPedido(int idPedido, String estado) {
		Pedido p = entityManager.find(Pedido.class, idPedido);
		p.setEstado(estado);
		entityManager.merge(p);
	}
	
	@Override
	public void confirmarPedido(int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		//obtener los productos en el carrito para meterlos en productoPedido
		Usuario u = entityManager.find(Usuario.class, idUsuario);
		List<Carrito> c = entityManager.createQuery(
				"select c from Carrito c where c.usuario.id = :usuario_id").setParameter("usuario_id", idUsuario).getResultList();
		System.out.println("productos en el carrito a procesar: " + c.size());
		for( Carrito productoCarrito : c ) {
			ProductoPedido pp = new ProductoPedido();
			pp.setCantidad(productoCarrito.getCantidad());
			pp.setPrenda(productoCarrito.getPrenda());
			pp.setPedido(p);
			entityManager.persist(pp);
		}
		p.setEstado(Estados.TERMINADO.name());
		entityManager.merge(p);
		//eliminar los productos del carrito
		entityManager.createNativeQuery(ConstantesSQL.SQL_VACIAR_CARRITO).setParameter("usuario_id", idUsuario).executeUpdate();
	}

	@Override
	public void procesarPaso1(String nombre, String direccion, String provincia, int codigo, int edad, String genero, int idUsuario) {
		Pedido p = new Pedido();
		p.setNombreCompleto(nombre);
		p.setDireccion(direccion);
		p.setProvincia(provincia);
		p.setCodigoPostal(codigo);
		p.setEdad(edad);
		p.setGenero(genero);
		p.setEstado(Estados.INCOMPLETO.name());
		p.setUsuario(entityManager.find(Usuario.class, idUsuario));
		entityManager.merge(p);
	}
	
	@Override
	public void procesarPaso2(String tarjeta, String numero, String titular, int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setTipoTarjeta(tarjeta);
		p.setNumeroTarjeta(numero);
		p.setTitularTarjeta(titular);
		entityManager.merge(p);
	}
	
	@Override
	public ResumenPedido procesarPaso3(String conocer, int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setPregunta(conocer);
		entityManager.merge(p);
		//Preparamos el ResumenPedido a devolver
		return obtenerResumenDelPedido(idUsuario);
	}
	
	private ResumenPedido obtenerResumenDelPedido (int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		ResumenPedido resumen = new ResumenPedido();
		resumen.setNombreCompleto(p.getNombreCompleto());
		resumen.setDireccion(p.getDireccion());
		resumen.setProvincia(p.getProvincia());
		resumen.setTitularTarjeta(p.getTitularTarjeta());
		resumen.setNumeroTarjeta(p.getNumeroTarjeta());
		resumen.setTipoTarjeta(p.getTipoTarjeta());
		resumen.setPrendas(servicioCarrito.obtenerProductosCarrito(idUsuario));
		return resumen;
	}
	
	//este metodo devuelve el pedido incompleto actual del usuario, si no existe lo creamos
	private Pedido obtenerPedidoIncompletoActual(int idUsuario) {
		Usuario usuario = entityManager.find(Usuario.class, idUsuario);
		Object pedidoIncompleto = null;
		List<Pedido> pedidos = 
				entityManager.createQuery(
						"select p from Pedido p where p.estado = :estado and p.usuario.id = :usuario_id").setParameter("estado", Estados.INCOMPLETO.name()).setParameter("usuario_id", idUsuario).getResultList();
		if(pedidos.size ()== 1) {
			pedidoIncompleto = pedidos.get(0);
		}
		Pedido pedido = null;
		if(pedidoIncompleto != null) {
			pedido = (Pedido) pedidoIncompleto;
		}else {
			pedido = new Pedido();
			pedido.setEstado(Estados.INCOMPLETO.name());
			pedido.setUsuario(usuario);
		}
		return pedido;
	}


}

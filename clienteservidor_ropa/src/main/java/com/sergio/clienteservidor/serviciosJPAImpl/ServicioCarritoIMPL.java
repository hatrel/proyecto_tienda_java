package com.sergio.clienteservidor.serviciosJPAImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sergio.clienteservidor.constantesSQL.ConstantesSQL;
import com.sergio.clienteservidor.model.Carrito;
import com.sergio.clienteservidor.model.Prenda;
import com.sergio.clienteservidor.model.Usuario;
import com.sergio.clienteservidor.servicios.ServicioCarrito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioCarritoIMPL implements ServicioCarrito{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void agregarProducto(int idUsuario, long idProducto, int cantidad) {
		//Aunque sería mas eficiente lanzar una sql
		Usuario usuario = (Usuario) entityManager.find(Usuario.class, idUsuario);
		List<Carrito> productosCarrito = usuario.getProductosCarrito();
		boolean productosEnCarrito = false;
		//Ver si el producto está en el carrito y en tal caso incrementar su catidad
		for(Carrito pc : productosCarrito) {
			if(pc.getPrenda().getId()== idProducto) {
				productosEnCarrito = true;
				pc.setCantidad(pc.getCantidad() + cantidad);
				entityManager.merge(pc); //Actualizamos el producto en carrito
			}
		}
		//Si el producto no está en el carrito, crear un registro nuevo
		if( ! productosEnCarrito ) {
			Carrito productoCarrito = new Carrito();
			productoCarrito.setUsuario(usuario);
			productoCarrito.setPrenda(entityManager.find(Prenda.class, idProducto));
			productoCarrito.setCantidad(cantidad);
			entityManager.persist(productoCarrito);
		}
	}

	@Override
	public List<Map<String, Object>> obtenerProductosCarrito(int idUsuario) {
		Query query = 
				entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO, Tuple.class);
		query.setParameter("usuario_id", idUsuario);
		
		List<Tuple> tuples = query.getResultList();

		List<Map<String, Object>> resultado = new ArrayList<>();
		for (Tuple tuple : tuples) {
		    Map<String, Object> fila = new HashMap<>();
		    for (TupleElement<?> element : tuple.getElements()) {
		        fila.put(element.getAlias(), tuple.get(element));
		    }
		    resultado.add(fila);
		}
		return resultado;
	}

	@Override
	public void quitarProductoCarrito(int idUsuario, long idPrenda) {
		entityManager.createNativeQuery(ConstantesSQL.SQL_ELIMINAR_PRODUCTO_CARRITO).setParameter("usuario_id", idUsuario).setParameter("prenda_id", idPrenda).executeUpdate();
		
	}

}

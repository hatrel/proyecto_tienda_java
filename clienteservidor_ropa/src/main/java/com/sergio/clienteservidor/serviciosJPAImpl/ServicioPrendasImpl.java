package com.sergio.clienteservidor.serviciosJPAImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;
import java.util.Set;

import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import com.sergio.clienteservidor.constantesSQL.ConstantesSQL;
import com.sergio.clienteservidor.model.Categoria;
import com.sergio.clienteservidor.model.Prenda;
import com.sergio.clienteservidor.servicios.ServicioPrendas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioPrendasImpl implements ServicioPrendas{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void registrarPrenda(Prenda prenda) {		
		try {
			prenda.setImagenPortada(prenda.getImagen().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Tenemos que asociar la categoria a nivel base de datos porque idCategoria tiene puesto @Transient
		Categoria categoria = entityManager.find(Categoria.class, prenda.getIdCategoria());
		prenda.setCategoria(categoria);
		entityManager.persist(prenda);
	}

	@Override
	public List<Prenda> obtenerPrendas() {
		return entityManager.createQuery("select p from Prenda p order by p.id desc").getResultList();
	}

	@Override
	public void borrarPrenda(long id) {
		//antes de borrar el producto debemos eliminar todas las referencias al mismo en el carrito y pedidos
		entityManager.createNativeQuery(
				"delete from CARRITO where PRENDA_ID = :id ").
				setParameter("id", id).executeUpdate();
		

		entityManager.createNativeQuery(
				"delete from PRODUCTO_PEDIDO where PRENDA_ID = :id ").
				setParameter("id", id).executeUpdate();
		
		//createNativeQuery lanza sql, no confundir con createQuery
		entityManager.createNativeQuery(
				"delete from tabla_prendas where id = :id ").
				setParameter("id", id).executeUpdate();
	}

	@Override
	public void actualizarPrenda(Prenda prendaFormEditar) {
		//lo siguiente es un tanto bestia, 
		//porque hay campos sensibles como por ejemplo el de byte[]
		//entityManager.merge(libro);
		Prenda prendaBaseDeDatos = entityManager.find(Prenda.class, prendaFormEditar.getId());
		prendaBaseDeDatos.setNombre(prendaFormEditar.getNombre());
		prendaBaseDeDatos.setDescripcion(prendaFormEditar.getDescripcion());
		prendaBaseDeDatos.setPrecio(prendaFormEditar.getPrecio());
		prendaBaseDeDatos.setTalla(prendaFormEditar.getTalla());
		prendaBaseDeDatos.setGenero(prendaFormEditar.getGenero());
		prendaBaseDeDatos.setFabricante(prendaFormEditar.getFabricante());
		if( prendaFormEditar.getImagen().getSize() > 0 ) {
			try {
				prendaBaseDeDatos.setImagenPortada(prendaFormEditar.getImagen().getBytes());
			} catch (IOException e) {
				System.out.println("no se pudo procesar el archivo subido");
				e.printStackTrace();
			}
		}
		prendaBaseDeDatos.setCategoria(entityManager.find(Categoria.class, prendaFormEditar.getIdCategoria()));
		entityManager.merge(prendaBaseDeDatos);
		
	}

	@Override
	public Prenda obtenerPrendaPorId(long id) {
		return entityManager.find(Prenda.class, id);
	}

	@Override
	public List<Map<String, Object>> obtenerPrendasParaFormarJSON() {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PRENDAS_PARA_JSON, Tuple.class);
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
	public Map<String, Object> obtenerPrendaParaFormarJSONPorId(long long1) {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PRENDAS_PARA_JSON_POR_ID, Tuple.class);
		query.setParameter("id", long1);
		Tuple tupla = (Tuple)query.getSingleResult();

	    Map<String, Object> resultado = new HashMap<>();
	    for (TupleElement<?> element : tupla.getElements()) {
	    	resultado.put(element.getAlias(), tupla.get(element));
	    }

		return resultado;
	}
	
	
}







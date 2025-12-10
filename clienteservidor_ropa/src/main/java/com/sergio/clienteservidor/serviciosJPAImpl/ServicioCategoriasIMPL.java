package com.sergio.clienteservidor.serviciosJPAImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sergio.clienteservidor.model.Categoria;
import com.sergio.clienteservidor.servicios.ServicioCategorias;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioCategoriasIMPL implements ServicioCategorias{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Categoria> obtenerCategorias() {
		return entityManager.createQuery("select c from Categoria c").getResultList();
	}
}

package com.sergio.clienteservidor.setUp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale.Category;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergio.clienteservidor.model.Carrito;
import com.sergio.clienteservidor.model.Categoria;
import com.sergio.clienteservidor.model.Prenda;
import com.sergio.clienteservidor.model.Usuario;
import com.sergio.clienteservidor.servicios.ServicioCarrito;
import com.sergio.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
@Transactional
public class SetUpImpl implements SetUp{
	
	@Autowired
	private ServicioPedidos servicioPedidos;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void prepararRegistros() {
		TablaSetUp registroSetUp = null;
		//si no hay un registro en la tabla set up, preparamos los registros iniciales
		try {
			registroSetUp = (TablaSetUp) entityManager.createQuery(
					"select r from TablaSetUp r").getSingleResult();
		} catch (Exception e) {
			System.out.println("no se encontro ningun registro en la tabla setup, "
					+ "comenzamos a realizar los registros iniciales...");
		}
		if( registroSetUp != null && registroSetUp.isCompletado() ) {
			return;
		}
		
		//preparar categorias iniciales
		Categoria cAccesorios= new Categoria("Accesorios", "Partes de cabeza");
		entityManager.persist(cAccesorios);
		
		Categoria cCabeza = new Categoria("Cabeza", "Partes de cabeza");
		entityManager.persist(cCabeza);
		
		Categoria cPantalon = new Categoria("Pantalón", "Partes de abajo");
		entityManager.persist(cPantalon);

		Categoria cCamiseta = new Categoria("Camiseta", "Partes de arriba");
		entityManager.persist(cCamiseta);

		Categoria cCalzado = new Categoria("Calzado", "Partes de pies");
		entityManager.persist(cCalzado);
		
		
		//preparamos los registros inciales:
		Prenda prenda1 = new Prenda("Pantaca","pantalon", 12.5, "S", "Hombre", "Yo");
		prenda1.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/prendas/pantaca.jpg") );
		prenda1.setCategoria(cPantalon);
		entityManager.persist(prenda1);
		
		Prenda prenda2 = new Prenda("Camitonto","camiseta", 12.5, "S", "Todos", "Yo");
		prenda2.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/prendas/camiseta.jpg") );
		prenda2.setCategoria(cCamiseta);
		entityManager.persist(prenda2);
		
		Prenda prenda3 = new Prenda("Calcetines antideslizantes","calzado", 12.5, "XL", "Mujer", "Yo");
		prenda3.setImagenPortada( obtenerInfoImagen(
				"http://localhost:8080/imagenes_base/prendas/calcetines_antideslizantes.jpg") );
		prenda3.setCategoria(cCalzado);
		entityManager.persist(prenda3);

		Prenda prenda4 = new Prenda("Pantalón holográfico", "pantalon", 24.99, "M", "Todos", "Yo");
		prenda4.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/pantalonHolografico.jpg"));
		prenda4.setCategoria(cPantalon);
		entityManager.persist(prenda4);

		Prenda prenda5 = new Prenda("Camiseta obrero reflectante", "camiseta", 18.90, "L", "Hombre", "Yo");
		prenda5.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/camisetaReflectante.jpg"));
		prenda5.setCategoria(cCamiseta);
		entityManager.persist(prenda5);

		Prenda prenda6 = new Prenda("Top brilli brilli", "camiseta", 15.50, "S", "Mujer", "Yo");
		prenda6.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/topBrili.jpg"));
		prenda6.setCategoria(cCamiseta);
		entityManager.persist(prenda6);

		Prenda prenda7 = new Prenda("Botas neón plataforma", "calzado", 39.99, "38", "Todos", "Yo");
		prenda7.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/BotasNeon.jpg"));
		prenda7.setCategoria(cCalzado);
		entityManager.persist(prenda7);

		Prenda prenda8 = new Prenda("Gafas futuristas LED", "accesorio", 12.00, "Única", "Todos", "Yo");
		prenda8.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/gafasLed.jpg"));
		prenda8.setCategoria(cAccesorios);
		entityManager.persist(prenda8);

		Prenda prenda9 = new Prenda("Máscara alien fosforescente", "accesorio", 10.50, "Única", "Todos", "Yo");
		prenda9.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/MascaraAlien.jpg"));
		prenda9.setCategoria(cAccesorios);
		entityManager.persist(prenda9);

		Prenda prenda13 = new Prenda("Gorra LED", "accesorio", 14.75, "Única", "Todos", "Yo");
		prenda13.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/gorraLed.jpg"));
		prenda13.setCategoria(cAccesorios);
		entityManager.persist(prenda13);

		Prenda prenda14 = new Prenda("Pantalón cargo neón", "pantalon", 22.40, "XL", "Hombre", "Yo");
		prenda14.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/cargoNeon.jpg"));
		prenda14.setCategoria(cPantalon);
		entityManager.persist(prenda14);

		Prenda prenda17 = new Prenda("Capucha ninja rave", "accesorio", 11.99, "Única", "Todos", "Yo");
		prenda17.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/ninja.jpg"));
		prenda17.setCategoria(cAccesorios);
		entityManager.persist(prenda17);

		Prenda prenda18 = new Prenda("Camiseta hippie", "camiseta", 19.99, "XL", "Todos", "Yo");
		prenda18.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/prendas/camisetaHippie.jpg"));
		prenda18.setCategoria(cCamiseta);
		entityManager.persist(prenda18);

		
		Usuario usuario1 = new Usuario("ares", "123", "ares@centronelson.org");
		entityManager.persist(usuario1);
		
		Usuario usuario2 = new Usuario("juan", "abc123", "juan@centronelson.org");
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario("maria", "pass456", "maria@centronelson.org");
		entityManager.persist(usuario3);

		Usuario usuario4 = new Usuario("carlos", "secure789", "carlos@centronelson.org");
		entityManager.persist(usuario4);

		Usuario usuario5 = new Usuario("laura", "clave321", "laura@centronelson.org");
		entityManager.persist(usuario5);
		
		
		//vamos a meter unos productos en el carrito de varios usuarios:
		Carrito registroCarrito = new Carrito();
		registroCarrito.setUsuario(usuario1);
		registroCarrito.setPrenda(prenda1);
		registroCarrito.setCantidad(3);
		entityManager.persist(registroCarrito);
		
		//registrar un pedido para Usuario1
		servicioPedidos.procesarPaso1("Ares", "Casa de Ares", "AresLandia", 23443, 41, "Masculino", usuario1.getId());
		servicioPedidos.procesarPaso2("1", "523453245", "Ares Sancho", usuario1.getId());
		servicioPedidos.procesarPaso3("3", usuario1.getId());
		servicioPedidos.confirmarPedido(usuario1.getId());
		
		Carrito registroCarrito2 = new Carrito();
		registroCarrito2.setUsuario(usuario3);
		registroCarrito2.setPrenda(prenda3);
		registroCarrito2.setCantidad(2);
		entityManager.persist(registroCarrito2);
		
		//una vez preparados los registros iniciales, 
		//marcamos el setup completado de la siguiente forma:
		TablaSetUp registro = new TablaSetUp();
		registro.setCompletado(true);
		entityManager.persist(registro);
		
	}//end prepararRegistros
	
	//metodo que nos va a permitir obtener un byte[] de cada archivo
	// de imagenes_base
	private byte[] obtenerInfoImagen(String ruta_origen) {
		byte[] info = null;
		try {
			URL url = new URL(ruta_origen);
			info = IOUtils.toByteArray(url);
		} catch (IOException e) {
			System.out.println("no se pudo procesar: " + ruta_origen);
			e.printStackTrace();
		}
		return info;
	}

}





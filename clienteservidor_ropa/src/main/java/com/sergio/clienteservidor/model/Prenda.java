package com.sergio.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="tabla_prendas")
public class Prenda {
	
	@Size(min = 3, max = 40, message = "titulo debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "debes insertar un titulo")
	@Pattern(
		    regexp = "^(?!.*  )[A-Za-z0-9 áéíóúÁÉÍÓÚÑñ]+$",
		    message = "solo letras, números y espacios, sin espacios consecutivos"
	)
	private String nombre;
	private String descripcion;
	
	@NotNull(message = "debes insertar un precio")
	@Min(value = 1, message = "el precio minimo es de 1 euro")
	@Max(value = 999, message = "El precio máximo es 999 euros")
	private double precio;
	private String talla;
	private String genero;
	private String fabricante;
	//campo para la imagen del producto:
	@Lob
	@Column(name = "imagen_portada", columnDefinition = "LONGBLOB")
	private byte[] imagenPortada;
	
	@Transient
	private MultipartFile imagen;
	
	@Transient
	private long idCategoria;
	
	@OneToMany(mappedBy = "prenda")
	private List<Carrito> carritos = new ArrayList<Carrito>();

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	public Prenda() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Prenda(String nombre, String descripcion, double precio, String talla, String genero, String fabricante, long id) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.talla = talla;
		this.genero = genero;
		this.fabricante = fabricante;
		this.id = id;
	}
	
	
	
	public Prenda(String nombre, String descripcion, double precio, String talla, String genero, String fabricante) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.talla = talla;
		this.genero = genero;
		this.fabricante = fabricante;
	}
	
	
	
	public List<Carrito> getCarritos() {
		return carritos;
	}
	public void setCarritos(List<Carrito> carritos) {
		this.carritos = carritos;
	}
	public byte[] getImagenPortada() {
		return imagenPortada;
	}
	public void setImagenPortada(byte[] imagenPortada) {
		this.imagenPortada = imagenPortada;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String getTalla() {
		return talla;
	}
	public void setTalla(String talla) {
		this.talla = talla;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public MultipartFile getImagen() {
		return imagen;
	}
	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}
	@Override
	public String toString() {
		return "Prenda [nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + ", talla=" + talla + ", genero="
				+ genero + ", fabricante=" + fabricante + ", imagen=" + imagen + ", id=" + id + "]";
	}

	
}

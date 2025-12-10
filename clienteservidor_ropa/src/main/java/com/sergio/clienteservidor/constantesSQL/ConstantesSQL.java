package com.sergio.clienteservidor.constantesSQL;

public class ConstantesSQL {
	
	
	
	public static final String SQL_OBTENER_PRENDAS_PARA_JSON = 
			"select P.id, p.nombre, p.descripcion, p.precio, p.talla, p.genero, p.fabricante, c.nombre as nombre_categoria from tabla_prendas as p, categoria as c where p.categoria_id = c.id order by p.id desc";

	public static final String SQL_OBTENER_PRENDAS_PARA_JSON_POR_ID = 
			"select P.id, p.nombre, p.descripcion, p.precio, p.talla, p.genero, p.fabricante, c.nombre as nombre_categoria from tabla_prendas as p, categoria as c where p.id = :id and p.categoria_id = c.id order by p.id desc";


	 public static final String SQL_OBTENER_PRODUCTOS_CARRITO =
	           "SELECT "
	           + "C.USUARIO_ID , TP.NOMBRE, TP.ID AS PRENDA_ID, TP.DESCRIPCION, TP.PRECIO, TP.TALLA, TP.GENERO, TP.FABRICANTE, C.CANTIDAD "
	           + "FROM CARRITO AS C, TABLA_PRENDAS AS TP "
	           + "WHERE "
	           + "USUARIO_ID = :usuario_id AND TP.ID = C.PRENDA_ID ";
	 public static final String SQL_ELIMINAR_PRODUCTO_CARRITO = "DELETE FROM CARRITO WHERE PRENDA_ID = :prenda_id AND USUARIO_ID = :usuario_id";

	 public static final String SQL_VACIAR_CARRITO = "DELETE FROM CARRITO WHERE USUARIO_ID = :usuario_id";
}


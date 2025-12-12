	//funciones generales:

function checkout_paso_4(json){
	let html = Mustache.render(plantilla_checkout_4, json)
	$("#contenedor").html(html)
	$("#boton_confirmar_pedido").click(function(){
		$.post("pedidosREST/paso4").done(function(res){
			alert(res)
			if(res == 'ok'){
				obtenerPrendas()
			}
		})
	})
}

function checkout_paso_3(){
	$("#contenedor").html(plantilla_checkout_3)
	$('#aceptar_paso_3').click(function(){
		forma_conocer = $("#forma_conocer").find(":selected").val()
		if(forma_conocer == "0"){
			alert("selecciona una opcion")
			return
		}
		$.post("pedidosREST/paso3",{
			conocer : forma_conocer
		}).done(function(res){
			checkout_paso_4(res)
		})
	})
}

function checkout_paso2(){
	$("#contenedor").html(plantilla_checkout_2)
	$("#aceptar_paso_2").submit(function(e){
		e.preventDefault()
		tipo_tarjeta = $("#tipo_tarjeta").find(":selected").val()
		if(tipo_tarjeta == "0"){
			alert("selecciona un tipo de tarjeta")
			return
		}
		numero = $("#numero_tarjeta").val()
		titular = $("#titular_tarjeta").val()
		$.post("pedidosREST/paso2",{
			tarjeta : tipo_tarjeta,
			numero : numero,
			titular : titular
		}).done(function(){
			alert("ok")
			checkout_paso_3()
		})
	})
}

function checkout_paso_1(){
	nombre = $("#campo_nombre").val()
	direccion = $("#campo_direccion").val()
	provincia = $("#campo_provincia").val()
	codigo = $("#codigo_postal").val()
	edad = $("#edad").val()
	genero = $("#genero").val()
	alert("mandar la info insertada a pedidosREST")
	$.post("pedidosREST/paso1",{
		nombre: nombre,
		direccion: direccion,
		provincia: provincia,
		codigo: codigo,
		edad: edad,
		genero: genero
	}).done(function(res){
		alert(res)
		if(res == "ok"){
			alert("Mostrar plantilla checkout2")
			checkout_paso2()
		}
	})
}

function checkout_paso_0(){
	$("#contenedor").html(plantilla_checkout_1)
	$("#aceptar_paso_1").submit(
		function(e){
			e.preventDefault()
			checkout_paso_1()
		}
	)
}

function mostrarCarrito(){
	if(nombre_login == ""){
		alert("tienes que identificarte para comprar productos")
	}else{
		//$("#contenedor").html(plantilla_carrito)
		$.get("carritoREST/obtener", function(r){
			if(r.length == 0){
				alert("aun no has agregado nada al carrito")
			}else{
				let html = Mustache.render(plantilla_carrito,r)
				$("#contenedor").html(html)
				$("#realizar_pedido").click(checkout_paso_0)
				//decir que hay que hacer cuando se haga click en el enlace de borrar producto
				$(".enlace-borrar-producto-carrito").click(function(){
					let idPrenda = $(this).attr("id-prenda")
					alert("mandar el id de prenda: " + idPrenda + " a prendasRest para que lo borre el carrito")
					$.post("carritoREST/eliminar",{
						id: idPrenda
					}).done(function(res){
						alert("Borrar")
						if(rest == "ok"){
							mostrarCarrito()
						}
					})//end done
				})//end click
			}//end else
		})//end get
	}//end else
}//end function

function comprar_producto(){
	if(nombre_login == ""){
		alert("tienes que identificarte para comprar productos")
	}else{
		var id_producto = $(this).attr("data-producto-id")
		alert("añadir producto de id: " + id_producto + " al carrito")	
		//invocar a una operacion de CarritoREST para agregar
		//el producto al carrito
		$.post("carritoREST/agregarProducto",{
			id: id_producto,
			cantidad: 1
		}).done(function(res){
			alert(res)
		})
		
	}
}//end comprar_producto

function mostrarDetalles(){
	$("#contenedor").html(plantilla_detalles)
	var id_producto = $(this).attr("data-producto-id")
	alert("Ver detalles de " + id_producto)
	$.post("prendasREST/MostrarDetalles",{
		id: id_producto
	}).done(function(res){
		console.log(res);
		var prod = Mustache.render(
			plantilla_detalles , { producto : res}
		)
		$("#contenedor").html(prod)
		$(".enlace_comprar_producto").click(comprar_producto)
	})
	
}
	
function obtenerPrendas(){
	$.get("prendasREST/obtener", function(r){
		//codigo a ejecutar cuando reciba la respuesta del recurso indicado
		//alert("recibido: " + r );
		var prendas = r //JSON.parse(r)
		console.log(prendas)
		var info = Mustache.render( 
				plantilla_prendas , { xxx : "hola desde mustache", array_prendas: prendas } ) 
		$("#contenedor").html(info)
		$(".enlace_comprar_producto").click(comprar_producto)		
		$(".ver_detalles").click(mostrarDetalles)
		$(".ver_detalles2").click(mostrarDetalles)
	})//end $.get
	$("#contenedor").html("cargando...");
}//end obtenerprendas

function mostrarLogin(){
	$("#contenedor").html(plantilla_login)
	$("#form_login").submit(function(e){
		e.preventDefault()
		var email = $("#email").val()
		var pass = $("#pass").val()
		$.post("usuariosREST/login",{
			email: email,
			pass: pass
		}).done(function(res){
			var parte1 = res.split(",")[0]
			var parte2 = res.split(",")[1]
			if (parte1 == "ok"){
				alert("bienvenido " + parte2 + " ya puedes comprar")
				nombre_login = parte2
				$("#login_usuario").html("hola " + parte2)
			}else{
				alert(res)
			}
		})//end done		
	})//end submit
}//end mostrarLogin

function mostrarRegistro(){
	$("#contenedor").html(plantilla_registro)
	//vamos a interceptar el envio de formulario
	$("#form_registro").submit(function(e){
		e.preventDefault()
		//alert("se intenta enviar form")
		//recoger los datos del form y mandarselos a UsuariosREST
		var nombre = $("#nombre").val()
		var email = $("#email").val()
		var pass = $("#pass").val()
		$.post("usuariosREST/registrar",{
			nombre: nombre, 
			email: email,
			pass: pass
		}).done(function(res){
			alert(res)
		})//end done
	})//end submit form
}//end mostrarRegistro

//atencion a eventos:

$("#enlace_detalles").click(mostrarDetalles)
$("#enlace_productos").click(obtenerPrendas)
$("#enlace_identificarme").click(mostrarLogin)
$("#enlace_registrarme").click(mostrarRegistro)
$("#enlace_carrito").click(mostrarCarrito)
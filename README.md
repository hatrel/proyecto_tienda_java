# proyecto_tienda_java
Proyecto de tienda online usando java y spring boot

Descripción: Proyecto Java Spring Boot para una tienda online con Thymeleaf, JPA y H2 por defecto.

Versiones

Spring Boot: 3.5.6 
Java: 21 
Maven: usa mvnw (Maven Wrapper) incluido; funciona sin tener Maven global instalado
Dependencias clave: Spring Web, Spring Data JPA, Thymeleaf, H2, MySQL Connector, commons-io
Requisitos previos

JDK: Instalar Java 21 (OpenJDK o Oracle JDK).
Git: Para clonar el repositorio. Si PowerShell muestra "git no se reconoce..." instala Git for Windows y añade git al PATH: https://git-scm.com/download/win
Maven: Opcional si usarás el wrapper mvnw/mvnw.cmd.
Clonar el repositorio

En PowerShell:
(Si PowerShell dice que git no se reconoce, instala Git y reinicia la terminal).

Abrir el proyecto

Importa la carpeta clienteservidor_ropa como proyecto Maven en tu IDE (IntelliJ IDEA / Eclipse / VSCode).
Ejecutar con Maven Wrapper (Windows PowerShell)

Ejecutar en modo desarrollo:
O compilar y empaquetar:
Ejecutar el jar generado:
Configuración por defecto de la BD

El proyecto viene configurado por defecto para usar H2 (archivo local). Ajustes en application.properties:
spring.datasource.url = jdbc:h2:file:./data/mydb
Usuario: sa, sin contraseña
spring.h2.console.enabled = true
Acceder consola H2: http://localhost:8080/h2-console (URL JDBC por defecto: jdbc:h2:file:./data/mydb)
Cambiar a MySQL

Edita application.properties y descomenta / configura:
spring.datasource.url = jdbc:mysql://localhost:3306/tu_base_datos?createDatabaseIfNotExist=true
spring.datasource.username = tu_usuario
spring.datasource.password = tu_password
Asegúrate de tener MySQL corriendo y el conector en pom.xml (ya incluido).
Reinicia la aplicación.
Puerto

Por defecto 8080. Para cambiarlo edita server.port en application.properties:
Ejecución en IDE

Importa como proyecto Maven.
Ejecuta la clase principal con la anotación @SpringBootApplication (run → Spring Boot App).
Consejos

Si usas Windows y no quieres instalar Maven global, utiliza .\mvnw.cmd.
Asegúrate de abrir una nueva terminal después de instalar Git para que el PATH se actualice.
Revisa pom.xml para ver otras dependencias y versiones.
Contacto / Autor

Proyecto originado en com.sergio — adapta el README según necesidades del equipo.
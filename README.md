<h1>Literatura</h1>
<p>Es un desafio creado por el programa One de Oracle Next Education y AluraLatam, en este desafio se crea una API que va consultar los datos de libros existentes en https://gutendex.com/, permitiendo guardar la informacion para posteriormente ser consultada en nuestra base de datos.</p>

<h2>Descripcion del codigo</h2>
<p>La aplicacion esta construida y distribuida en 4 paquetes, donde encontramos el paquete principal que se encarga de llevar el control del flujo de la aplicacion atraves del menu:
<ol>buscar libros </ol>
<ol>visualizar libros </ol>
<ol>visualizar autores </ol>
<ol>visualizar autores vivos por determinado año </ol>
<ol>visualizar libros por idioma </ol>
En nuestro paquete model, encontramos las clases de libro,autor e idioma con sus DTO respectivos que definen los atributos, variables tabla en la base de datos.
En nuestro paquete repository encontramos las clase que sirven para buscar libros, autores y idiomas en nuestra base de datos.
En nuestro paquete service encontramos la clase para obtener los datos por nuestra API, asi mismo la clase que convierte la informacion consultada en un JSON y una clase que nos permite validar la informacion antes de ser guardada en la base de datos en la tablas (libros, autores y idiomas)
</p>

<h2>Requisitos y Tecnnologias</h2>
<ul>
  <li>Java OpenJDK ver. 17.0.10</li>
  <li>Libreria Jackson</li>
  <li>IDE(IntelliJ o del su preferencia)</li>
  <li>Spring</li>
  <li>Dependencias de Spring</li>
    <ol>spring-boot-starter</ol>
    <ol>pring-boot-starter-data-jpa</ol>
    <ol>postgresql</ol>
    <ol>spring-boot-starter-test</ol>
    <ol>jackson-databind</ol>
    <ol>spring-boot-devtools</ol>
</ul>

<h2>Autor</h2>
<p> Edgar Reyes </p>

<h2>Licencia</h2>
<p>Licencia MI </p>

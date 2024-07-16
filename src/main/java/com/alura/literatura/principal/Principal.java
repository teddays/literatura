package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AutorRepositorio;
import com.alura.literatura.repository.IdiomaRepositorio;
import com.alura.literatura.repository.LibroRepositorio;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConversorDatos;
import com.alura.literatura.service.SLibro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class Principal {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private IdiomaRepositorio idiomaRepositorio;
    private SLibro sLibro;
    private Scanner teclado = new Scanner(System.in);
    private Libro libro = new Libro();
    private List<DatoLibro> datoLibros = new ArrayList<>();
    private DatosObtenidos datosObtenidos;
    private List<DatoLibro> datoLibro;
    private Integer numeroDeLibros;
    private List<Libro> libros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();
    private List<Idioma> idiomas = new ArrayList<>();
    private String json;



    public Principal(SLibro sLibro, LibroRepositorio libroRepositorio, AutorRepositorio autorRepositorio, IdiomaRepositorio idiomaRepositorio){
        this.sLibro = sLibro;
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
        this.idiomaRepositorio = idiomaRepositorio;

    }




    public void menu () throws JsonProcessingException {
        int opcion=0;
        while (opcion != 6){
            System.out.println("""
                    Seleccione una de las opciones disponibles
                    1 - Buscar Libro por título
                    2 - Visualizar libros registrados
                    3 - Visualizar autores registrados
                    4 - Visualizar autores vivos en un determinado año
                    5 - Visualizar libros por idiomas
                    6 - Salir!
                    """);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    visualizarLibros();
                    break;
                case 3:
                    visualizarLibroAutores();
                    break;
                case 4:
                    visualizarLibroAutoresVivos();
                    break;
                case 5:
                    visualizarLibroPorIdioma();
                    break;
                case 6:
                    System.out.printf("Saliendo de la aplicacion");
                    break;
                default:
                    System.out.println("La opcion seleccionada no esta disponible");
                    break;

            }
        }
    }

    private void  buscarLibroTitulo(){
        System.out.println("Escribe el titulo del libro");
        String buscarTitulo = teclado.next().replace(" ","+");
        json = ConsumoAPI.consultaDatos("https://gutendex.com/books/?search=" + buscarTitulo);

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode rootNode = objectMapper.readTree(json);
            if(!(rootNode.has("results") && rootNode.get("results").size()>0)){
                System.out.println("El titulo ingresado no existe");
            } else {
                //System.out.printf(json);
                numeroDeLibros = rootNode.get("results").size();
                datosObtenidos = new ConversorDatos().obtenerDatos(json,DatosObtenidos.class);
                datoLibro = datosObtenidos.datoLibros();

                for (int i=0; i<numeroDeLibros;i++){
                    System.out.println(i+1);
                    System.out.println("Titulo: " + datoLibro.get(i).titulo()
                            + "Autores: " + datoLibro.get(i).autor()
                            + "Idiomas: " + datoLibro.get(i).idioma()
                            + "Descargas: " + datoLibro.get(i).descargas() + "\n");
                }

                System.out.println("Selecciona el libro que deseas guardar");
                if (teclado.hasNext()){
                    Integer opcion = teclado.nextInt();
                    teclado.nextLine();
                    if ((opcion > 0) && (opcion <= numeroDeLibros)){
                        DatoLibro datoLibro = datoLibros.get(opcion-1);
                        try{
                            sLibro.guardaLibro(datoLibro);
                            System.out.println("Se guardo libro " + opcion);
                        } catch (DataIntegrityViolationException e){
                            System.out.println("El libro ya existe en la base de datos");
                        }
                    } else {
                        System.out.println("No se guardo libro");
                    }
                }else {
                    teclado.nextLine();
                    System.out.println("No se guardo libro");
                }
            }
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }


    private void visualizarLibros(){
        libros = libroRepositorio.findAll();
        muestraLibros(libros);

    }

    private void muestraLibros(List<Libro> libros) {
        libros.forEach(libro ->{
            System.out.println("Titulo: " + libro.getTitulo());
            autores = new ArrayList<>();
            autores = libroRepositorio.encuentraAutoresDeLibro(libro.getId());
            System.out.println("Autor(s): " + autores.get(0).getNombre());
            for (int i=1; i < autores.size(); i++){
                System.out.println(" -> " + idiomas.get(i).getNombre());
            }
            System.out.println("Descargas: " + libro.getDescargas());
        } );
    }

    private void visualizarLibroAutores(){
        autores = autorRepositorio.findAll();
        muestraAutores(autores);
    }

    private void muestraAutores(List<Autor> autores) {
        if (!autores.isEmpty()){
            autores.forEach(autor -> {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());

                libros = libroRepositorio.encuentraLibrosDeAutor(autor.getId());
                System.out.println("Libro(s): \n" + libros.get(0).getTitulo());
                for (int i=1; i < libros.size(); i++){
                    System.out.println(" -> " + libros.get(i).getTitulo());
                }
            });
        } else {
            System.out.println("No disponemos de ningun autor para mostrar");
        }

    }

    private void visualizarLibroAutoresVivos(){
        System.out.println("Escribe el año que deseas buscar un autor(es) ");
        Integer opcion;
        if (teclado.hasNext()){
            opcion = teclado.nextInt();
            teclado.nextLine();
            autores = autorRepositorio.buscarAutoresPorAno(opcion);
            muestraAutores(autores);
        } else{
            System.out.println("Año invalido");
            teclado.nextLine();
        }
    }

    private void visualizarLibroPorIdioma(){
        idiomas = idiomaRepositorio.findAll();
        List<String> listaIdiomas = new ArrayList<>();
        System.out.println("Los libros que disponemos con los idiomas siguientes: ");
        idiomas.forEach(i-> System.out.println(i.getNombre() + " "));
        System.out.println("Escribe el idioma interesado: ");
        String idiomaSeleccionado = teclado.nextLine();
        Optional<Idioma> existeIdioma = idiomaRepositorio.findByNombre(idiomaSeleccionado);
        if (existeIdioma.isPresent()){
            libros = libroRepositorio.encuentaLibrosPorIdioma(existeIdioma.get().getId());
            muestraLibros(libros);
        } else {
            System.out.println("El idioma no existe");
        }

    }


}

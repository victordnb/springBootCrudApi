package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import com.example.demo.models.Contacto;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//anotacion para convertir esta clase en un controlador
@RestController
public class Views {

    // 2º vamos a generar unos datos mediante una lista( en el futuro será en una base de datos)
    //nota: cuando se trabaje con bases de datos no usar static.
    public static List<Contacto> database = new ArrayList<>();
    public static int position = -1;
    public static int contador = 0;


    // 1º definimos nuestra primera ruta:
    // 5º devolvemos la lista de datos:
    @GetMapping("/")
    public List<Contacto> index() {
        return Views.database;
    }

    // 3º definimos nuestra segunda ruta:
    @GetMapping("/find/{id}")
    public String find(@PathVariable int id) {
        this.locate(id);
        return "Buscamos el id: " + id;
    }

    // 4º definimos las siguentes rutas:
    @PutMapping("/update/{id}")
    public Contacto update(@PathVariable int id,@RequestBody Contacto contacto) {

        //return "Actualizando el id: " + id;

        // aqui actualizamos el id  con el id que pasamos por params.
        contacto.id = id;
        this.locate(id);
        if(position < 0) {
            return null;
        }else{
            Views.database.set(position, contacto);
            position = -1;
            return contacto;
        }
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        this.locate(id);
        if(position < 0) {
            return "not found id: " + id;
        }else{
            Views.database.remove(position);
            position = -1;
            return "contacto eliminado con id: " + id;
        }
    }

    @PostMapping("/create")
    public Contacto create(@RequestBody Contacto contacto) {
        Random rm = new Random();
        contacto.id = rm.nextInt(1000);
        Views.database.add(contacto);
        System.out.println(Views.database.toString());
        System.out.println("hola");
        return contacto;
    }    

    public void locate(int id) {
        Views.database.forEach(data -> {
            if(data.id == id) {
                position = contador;
            }
            contador ++;
        });
        contador = 0;
    }
}


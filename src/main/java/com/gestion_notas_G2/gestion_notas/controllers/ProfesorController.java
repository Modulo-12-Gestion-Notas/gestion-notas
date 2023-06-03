package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.models.Profesor;
import com.gestion_notas_G2.gestion_notas.services.ProfesorService;

import Interfaces.IProfesor;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfesorController {
    private IProfesor profesorService;

    public ProfesorController(IProfesor profesorService) {
        this.profesorService = profesorService;
    }

    /**
     * Obtiene una lista de todos los profesores.
     *
     * @return Una lista de objetos Profesor y un código de estado 200 (OK) si la
     *         lista se obtiene correctamente.
     *         Si se produce un error, devuelve un mensaje de error y un código de
     *         estado 500 (INTERNAL SERVER ERROR).
     */
    @GetMapping("/api/profesores")
    @ApiOperation(value = "Obtiene una lista de todos los profesores", response = Profesor.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de profesores obtenida correctamente"),
            @ApiResponse(code = 500, message = "Error al obtener la lista de profesores")
    })
    public ResponseEntity<Object> getProfesores() {
        try {
            List<Profesor> profesorList = this.profesorService.getProfesores();
            return new ResponseEntity<>(profesorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crea un nuevo profesor.
     *
     * @param profesor Objeto Profesor con la información del nuevo profesor.
     * @return Un mensaje de confirmación.
     */
    @PostMapping("/api/profesores")
    @ApiOperation(value = "Crea un nuevo profesor", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Profesor creado correctamente"),
            @ApiResponse(code = 400, message = "Los datos del profesor son incorrectos"),
            @ApiResponse(code = 500, message = "Error al crear el profesor")
    })
    public ResponseEntity<Object> postProfesor(@RequestBody Profesor profesor) {
        try {
            String message = this.profesorService.postProfesor(profesor);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Maneja una excepción de tipo IllegalArgumentException.
     *
     * @param ex la excepción de tipo IllegalArgumentException que se produjo
     * @return un mensaje de error y un código de estado 400 (BAD REQUEST)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

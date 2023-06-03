package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.models.Grupo;
import Interfaces.IGrupoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GrupoController {

    private IGrupoService grupoService;

    public GrupoController(IGrupoService grupoService) {
        this.grupoService = grupoService;
    }

    /**
     * Obtiene la lista de grupos asignados a un profesor.
     *
     * @param idProfesor El ID del profesor para buscar sus grupos.
     * @return La lista de grupos asignados al profesor.
     */
    @GetMapping("/api/{idProfesor}/grupos/")
    @ApiOperation(value = "Obtiene la lista de grupos asignados a un profesor", response = Grupo.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Grupo.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Object> getGruposByProfesor(@PathVariable Long idProfesor) {
        try {
            List<Grupo> grupos = grupoService.getGruposByProfesor(idProfesor);
            return new ResponseEntity<>(grupos, HttpStatus.OK);
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

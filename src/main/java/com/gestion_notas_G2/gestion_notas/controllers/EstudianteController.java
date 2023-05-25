package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.response.EstudianteGruposNotasResponse;
import com.gestion_notas_G2.gestion_notas.services.EstudianteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EstudianteController {
    private EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService){
        this.estudianteService= estudianteService;
    }


    /**
     * Obtiene las calificaciones de un estudiante.
     *
     * @param idEstudiante El ID del estudiante para obtener sus calificaciones.
     * @return Las calificaciones del estudiante.
     */
    @GetMapping("api/estudiantes/{idEstudiante}/mis-notas")
    @ApiOperation(value = "Obtiene las calificaciones de un estudiante",
            response = EstudianteGruposNotasResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = EstudianteGruposNotasResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Object> getCalificacionesByEstudiante(@PathVariable Long idEstudiante){
        try {
            EstudianteGruposNotasResponse estudianteGruposNotasResponse = this.estudianteService.getCalificacionesByEstudiante(idEstudiante);
            return new ResponseEntity<>(estudianteGruposNotasResponse, HttpStatus.OK);
        }catch (Exception e){
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

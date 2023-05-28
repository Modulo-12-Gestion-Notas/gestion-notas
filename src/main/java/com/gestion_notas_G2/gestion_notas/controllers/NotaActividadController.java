package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.dto.NotaActividadDTO;
import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;
import com.gestion_notas_G2.gestion_notas.services.NotaActividadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class NotaActividadController {
    private NotaActividadService notaActividadService;
    public  NotaActividadController(NotaActividadService notaActividadService){
        this.notaActividadService = notaActividadService;
    }


    /**
     * Guarda una lista de notas de actividades para un grupo específico.
     *
     * @param codigoGrupo El código del grupo al que pertenecen las notas de actividades.
     * @param notaActividadDTOList La lista de objetos NotaActividadDTO que representan las notas de actividades a guardar.
     * @return Una respuesta HTTP con el resultado de la operación.
     */
    @PostMapping("api/{codigoGrupo}/notas/save")
    @ApiOperation(value = "Guarda una lista de notas de actividades para un grupo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Object> postNotaActividadList(@RequestBody List<NotaActividadDTO> notaActividadDTOList, @PathVariable Long codigoGrupo){
        try {
            String message = this.notaActividadService.postNotaActividadList(notaActividadDTOList, codigoGrupo);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

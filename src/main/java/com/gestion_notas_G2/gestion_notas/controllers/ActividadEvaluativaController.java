package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;
import com.gestion_notas_G2.gestion_notas.services.ActividadEvaluativaService;

import Interfaces.IActividadEvaluativa;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ActividadEvaluativaController {

    private IActividadEvaluativa actividadEvaluativaService;

    public ActividadEvaluativaController(IActividadEvaluativa actividadEvaluativaService) {
        this.actividadEvaluativaService = actividadEvaluativaService;
    }

    /**
     * Obtiene la lista de actividades evaluativas de un grupo.
     *
     * @param codigoGrupo El código del grupo para buscar sus actividades
     *                    evaluativas.
     * @return La lista de actividades evaluativas del grupo.
     */
    @GetMapping("api/{codigoGrupo}/actividades_evaluativas/")
    @ApiOperation(value = "Obtiene la lista de actividades evaluativas de un grupo", response = ActividadEvaluativa.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ActividadEvaluativa.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Object> getActividadEvaluativaListByGrupo(@PathVariable Long codigoGrupo) {
        try {
            List<ActividadEvaluativa> actividadEvaluativaList = actividadEvaluativaService
                    .getActividadEvaluativaListByGrupo(codigoGrupo);
            return new ResponseEntity<>(actividadEvaluativaList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crea una lista de actividades evaluativas para un grupo.
     *
     * @param actividadEvaluativaList La lista de actividades evaluativas a crear.
     * @param codigoGrupo             El código del grupo al que se asociarán las
     *                                actividades evaluativas.
     * @return Un mensaje de confirmación de la operación.
     */
    @PostMapping("api/{codigoGrupo}/actividades_evaluativas/")
    @ApiOperation(value = "Crea una lista de actividades evaluativas para un grupo", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Object> postActividadEvaluativaList(
            @RequestBody List<ActividadEvaluativa> actividadEvaluativaList, @PathVariable Long codigoGrupo) {
        try {
            String message = this.actividadEvaluativaService.postActivadEvaluativaList(actividadEvaluativaList,
                    codigoGrupo);
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

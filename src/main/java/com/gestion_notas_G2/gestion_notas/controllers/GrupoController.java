package com.gestion_notas_G2.gestion_notas.controllers;


import com.gestion_notas_G2.gestion_notas.models.Grupo;
import com.gestion_notas_G2.gestion_notas.response.GrupoEstudiantesEvalInfoResponse;
import com.gestion_notas_G2.gestion_notas.services.ActividadEvaluativaService;
import com.gestion_notas_G2.gestion_notas.services.GrupoService;
import com.gestion_notas_G2.gestion_notas.services.MatriculaService;
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

    private GrupoService grupoService;
    private MatriculaService matriculaService;

    private ActividadEvaluativaService actividadEvaluativaService;

    public GrupoController(GrupoService grupoService, MatriculaService matriculaService, ActividadEvaluativaService actividadEvaluativaService){
       this.grupoService = grupoService;
       this.matriculaService = matriculaService;
       this.actividadEvaluativaService = actividadEvaluativaService;
    }


    /**
     * Obtiene la lista de grupos asignados a un profesor.
     *
     * @param idProfesor El ID del profesor para buscar sus grupos.
     * @return La lista de grupos asignados al profesor.
     */
    @GetMapping("/api/{idProfesor}/grupos/")
    @ApiOperation(value = "Obtiene la lista de grupos asignados a un profesor",
            response = Grupo.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Grupo.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Object> getGruposByProfesor(@PathVariable Long idProfesor){
        try {
            List<Grupo> grupos = grupoService.getGruposByProfesor(idProfesor);
            return new ResponseEntity<>(grupos, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Obtiene la información de estudiantes y evaluaciones de un grupo.
     *
     * @param codigoGrupo El código del grupo del que se obtendrá la información.
     * @return Una ResponseEntity con la información de estudiantes y evaluaciones del grupo.
     */
    @GetMapping("api/{codigoGrupo}/evaluacion/estudiantes")
    @ApiOperation(value = "Obtiene la información de estudiantes y evaluaciones de un grupo",
            response = GrupoEstudiantesEvalInfoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GrupoEstudiantesEvalInfoResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<GrupoEstudiantesEvalInfoResponse> getEstudiantesEvalInfoByGrupo(@PathVariable Long codigoGrupo) {
        try {
            GrupoEstudiantesEvalInfoResponse response = new GrupoEstudiantesEvalInfoResponse();
            response.setEstudiantes(matriculaService.getEstudianteListConNotaActividadListByGrupo(codigoGrupo));
            response.setProfesor(grupoService.getProfesorByGrupo(codigoGrupo));
            response.setGrupo(grupoService.getGrupoByCodigoGrupo(codigoGrupo));
            response.setEvaluaciones(actividadEvaluativaService.getActividadEvaluativaSimpleDTO(codigoGrupo));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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

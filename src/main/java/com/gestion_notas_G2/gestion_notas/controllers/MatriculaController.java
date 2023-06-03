package com.gestion_notas_G2.gestion_notas.controllers;

import com.gestion_notas_G2.gestion_notas.dto.EstudianteDTO;
import com.gestion_notas_G2.gestion_notas.dto.GrupoSimpleDTO;
import com.gestion_notas_G2.gestion_notas.dto.ProfesorDTO;
import com.gestion_notas_G2.gestion_notas.response.GrupoEstudiantesResponse;
import com.gestion_notas_G2.gestion_notas.services.GrupoService;
import com.gestion_notas_G2.gestion_notas.services.MatriculaService;

import Interfaces.IGrupoService;
import Interfaces.IMatricula;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MatriculaController {
    private IMatricula matriculaService;
    private IGrupoService grupoService;

    public MatriculaController(IMatricula matriculaService, IGrupoService grupoService) {
        this.matriculaService = matriculaService;
        this.grupoService = grupoService;
    }

    /**
     * Obtiene la lista de estudiantes de un grupo.
     *
     * @param codigoGrupo El código del grupo del que se obtendrán los estudiantes.
     * @return Una respuesta que contiene la información del profesor, el grupo y la
     *         lista de estudiantes.
     */
    @GetMapping("api/{codigoGrupo}/estudiantes")
    @ApiOperation(value = "Obtiene la lista de estudiantes de un grupo", response = GrupoEstudiantesResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GrupoEstudiantesResponse.class),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Object> getEstudieantesByGrupo(@PathVariable Long codigoGrupo) {
        try {
            List<EstudianteDTO> estudianteDTOList = this.matriculaService.getEstudieantesByGrupo(codigoGrupo);
            ProfesorDTO profesorDTO = this.grupoService.getProfesorByGrupo(codigoGrupo);
            GrupoSimpleDTO grupoSimpleDTO = this.grupoService.getGrupoByCodigoGrupo(codigoGrupo);

            GrupoEstudiantesResponse grupoEstudiantesResponse = new GrupoEstudiantesResponse();
            grupoEstudiantesResponse.setProfesor(profesorDTO);
            grupoEstudiantesResponse.setGrupo(grupoSimpleDTO);
            grupoEstudiantesResponse.setEstudianteList(estudianteDTOList);

            return new ResponseEntity<>(grupoEstudiantesResponse, HttpStatus.OK);

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

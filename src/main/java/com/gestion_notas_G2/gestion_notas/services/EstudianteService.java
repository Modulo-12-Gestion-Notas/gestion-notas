package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.dto.*;
import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.models.Grupo;
import com.gestion_notas_G2.gestion_notas.models.Matricula;
import com.gestion_notas_G2.gestion_notas.repositories.EstudianteRepository;
import com.gestion_notas_G2.gestion_notas.response.EstudianteGruposNotasResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    private EstudianteRepository estudianteRepository;
    private ModelMapper modelMapper;
    private NotaActividadService notaActividadService;

    public EstudianteService(EstudianteRepository estudianteRepository, ModelMapper modelMapper, NotaActividadService notaActividadService) {
        this.estudianteRepository = estudianteRepository;
        this.modelMapper = modelMapper;
        this.notaActividadService = notaActividadService;
    }


    /**
     * Obtiene las calificaciones de un estudiante por su identificador.
     *
     * @param idEstudiante El identificador del estudiante.
     * @return Un objeto EstudianteGruposNotasResponse que contiene las calificaciones del estudiante.
     * @throws Exception Si no se encuentran datos para el estudiante.
     */
    public EstudianteGruposNotasResponse getCalificacionesByEstudiante(Long idEstudiante) throws Exception {
        EstudianteGruposNotasResponse estudianteGruposNotasResponse = new EstudianteGruposNotasResponse();
        Optional<Estudiante> estudiante = estudianteRepository.findById(idEstudiante);
        List<GrupoNotasByEstudianteDTO> grupoNotasByEstudianteDTOList = new ArrayList<>();

        if (estudiante.isPresent()) {
            Estudiante estudianteObj = estudiante.get();
            estudianteGruposNotasResponse.setEstudiante(modelMapper.map(estudianteObj, EstudianteDTO.class));

            for (Matricula matricula : estudianteObj.getMatriculas()) {
                Grupo grupo = matricula.getGrupo();

                if (grupo.getPeriodoAcademico().isVigente()) {

                    GrupoNotasByEstudianteDTO grupoNotasByEstudianteDTO = new GrupoNotasByEstudianteDTO();

                    grupoNotasByEstudianteDTO.setNombreGrupo(grupo.getNombreGrupo());
                    grupoNotasByEstudianteDTO.setCurso(modelMapper.map(grupo.getCurso(), CursoSimpleDTO.class));
                    grupoNotasByEstudianteDTO.setPeriodoAcademico(modelMapper.map(grupo.getPeriodoAcademico(), PeriodoAcademicoDTO.class));
                    grupoNotasByEstudianteDTO.setProfesor(modelMapper.map(grupo.getProfesor(), ProfesorDTO.class));
                    grupoNotasByEstudianteDTO.setEvaluaciones((grupo.getActividadesEvaluativas().stream()
                            .map(actividadEvaluativa -> modelMapper.map(actividadEvaluativa, ActividadEvaluativaSimpleDTO.class))
                            .collect(Collectors.toList())));
                    grupoNotasByEstudianteDTO.setNotas(notaActividadService.getNotasByEstudianteOfGrupo(estudianteObj.getId(), grupo.getCodigoGrupo()));
                    grupoNotasByEstudianteDTOList.add(grupoNotasByEstudianteDTO);
                }
            }
            estudianteGruposNotasResponse.setGrupos(grupoNotasByEstudianteDTOList);

            return estudianteGruposNotasResponse;
        } else {
            throw new Exception("No se encontraron datos para el estudiante.");
        }
    }

}

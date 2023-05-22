package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.dto.NotaActividadDTO;
import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;
import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.models.NotaActividad;
import com.gestion_notas_G2.gestion_notas.repositories.NotaActividadRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotaActividadService {
    private NotaActividadRepository notaActividadRepository;

    public NotaActividadService(NotaActividadRepository notaActividadRepository){
        this.notaActividadRepository = notaActividadRepository;
    }


    /**
     * Obtiene la lista de notas de actividades de un estudiante en un grupo.
     *
     * @param e El objeto Estudiante del que se obtendrán las notas de actividades.
     * @param codigoGrupo El código del grupo del que se filtrarán las notas de actividades.
     * @return Una lista de objetos NotaActividadDTO que representan las notas de actividades del estudiante en el grupo.
     */
    public List<NotaActividadDTO> getNotaActividadListByEstudianteAndGrupo(Estudiante e, Long codigoGrupo) {
        return e.getNotaActividades().stream()
                .filter(n -> Objects.equals(n.getActividadEvaluativa().getGrupo().getCodigoGrupo(), codigoGrupo))
                .map(n -> {
                    NotaActividadDTO notaActividadDTO = new NotaActividadDTO();
                    notaActividadDTO.setId(n.getId());
                    notaActividadDTO.setIdEstudiante(n.getEstudiante().getId());
                    notaActividadDTO.setIdActividadEvaluativa(n.getActividadEvaluativa().getId());
                    notaActividadDTO.setCalificacion(n.getCalificacion());
                    return notaActividadDTO;
                })
                .collect(Collectors.toList());
    }


    /**
     * Guarda una lista de notas de actividades en el sistema.
     *
     * @param notaActividadDTOList La lista de objetos NotaActividadDTO que representan las notas de actividades a guardar.
     * @param codigiGrupo El código del grupo al que pertenecen las notas de actividades.
     * @return Un mensaje indicando el resultado de la operación.
     * @throws Exception Si ocurre un error al guardar los datos.
     */
    public String postNotaActividadList(List<NotaActividadDTO> notaActividadDTOList, Long codigiGrupo) throws Exception {
        List<NotaActividad> notaActividades = this.notaActividadRepository.findNotaActividadByActividadEvaluativa_Grupo(codigiGrupo);
        Set<String> combinacionesUnicas = new HashSet<>();

        try {
            List<NotaActividad> nuevaNotaActividadList = notaActividadDTOList.stream()
                    .filter(notaActividadDTO -> {
                        String combinacionUnica = notaActividadDTO.getIdEstudiante() + "-" + notaActividadDTO.getIdActividadEvaluativa();
                        return combinacionesUnicas.add(combinacionUnica);
                    })
                    .map(notaActividadDTO -> {
                        NotaActividad notaActividad = new NotaActividad();
                        notaActividad.setId(notaActividadDTO.getId());
                        notaActividad.setCalificacion(notaActividadDTO.getCalificacion());
                        notaActividad.setEstudiante(new Estudiante(notaActividadDTO.getIdEstudiante()));
                        notaActividad.setActividadEvaluativa(new ActividadEvaluativa(notaActividadDTO.getIdActividadEvaluativa()));
                        return notaActividad;
                    })
                    .collect(Collectors.toList());

            if (notaActividades.isEmpty()) {
                this.notaActividadRepository.saveAll(nuevaNotaActividadList);
            } else {
                notaActividades.removeIf(nuevaNotaActividadList::contains);
                this.notaActividadRepository.deleteAll(notaActividades);
                this.notaActividadRepository.saveAll(nuevaNotaActividadList);
            }

            return "Las notas se han guardado correctamente.";
        } catch (Exception e) {
            throw new Exception("Ha ocurrido un error al guardar los datos: " + e.getMessage());
        }
    }
}

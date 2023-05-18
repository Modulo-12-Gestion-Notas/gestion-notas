package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.dto.NotaActividadDTO;
import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.models.NotaActividad;
import com.gestion_notas_G2.gestion_notas.repositories.NotaActividadRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                    return notaActividadDTO;
                })
                .collect(Collectors.toList());
    }
}

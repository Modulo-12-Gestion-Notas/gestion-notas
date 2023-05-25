package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.dto.ActividadEvaluativaSimpleDTO;
import com.gestion_notas_G2.gestion_notas.dto.NotaActividadDTO;
import com.gestion_notas_G2.gestion_notas.dto.NotaActividadDetalladaDTO;
import com.gestion_notas_G2.gestion_notas.dto.NotasByEstudianteOfGrupoDTO;
import com.gestion_notas_G2.gestion_notas.models.ActividadEvaluativa;
import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.models.NotaActividad;
import com.gestion_notas_G2.gestion_notas.repositories.NotaActividadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotaActividadService {
    private NotaActividadRepository notaActividadRepository;
    private ModelMapper modelMapper;

    public NotaActividadService(NotaActividadRepository notaActividadRepository, ModelMapper modelMapper){
        this.notaActividadRepository = notaActividadRepository;
        this.modelMapper = modelMapper;
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
     * Obtiene las notas de un estudiante en un grupo específico.
     *
     * @param idEstudiante El identificador del estudiante.
     * @param codigoGrupo El código del grupo.
     * @return Un objeto NotasByEstudianteOfGrupoDTO que contiene las notas del estudiante en el grupo.
     */
    public NotasByEstudianteOfGrupoDTO getNotasByEstudianteOfGrupo(Long idEstudiante, Long codigoGrupo) {
        NotasByEstudianteOfGrupoDTO notasByEstudianteOfGrupoDTO = new NotasByEstudianteOfGrupoDTO();
        List<NotaActividadDetalladaDTO> notaActividadDetalladaDTOList = new ArrayList<>();
        List<NotaActividad> notaActividadList = this.notaActividadRepository.findNotaActividadByEstudianteAndGrupo(codigoGrupo,idEstudiante);
        float notaAcumulada = 0F;
        int porcentajeEvaluado = 0;

        for (NotaActividad n : notaActividadList) {

            if (Objects.equals(n.getActividadEvaluativa().getGrupo().getCodigoGrupo(), codigoGrupo)) {
                NotaActividadDetalladaDTO notaActividadDetalladaDTO = new NotaActividadDetalladaDTO();
                notaActividadDetalladaDTO.setId(n.getId());
                notaActividadDetalladaDTO.setEvaluacion(modelMapper.map(n.getActividadEvaluativa(), ActividadEvaluativaSimpleDTO.class));
                notaActividadDetalladaDTO.setCalificacion(n.getCalificacion());

                notaActividadDetalladaDTOList.add(notaActividadDetalladaDTO);

                float valorNota = (float) (n.getCalificacion() * (float) n.getActividadEvaluativa().getPorcentaje() / 100);
                notaAcumulada += (valorNota);
                porcentajeEvaluado += n.getActividadEvaluativa().getPorcentaje();
            }
        }

        notasByEstudianteOfGrupoDTO.setCalificaciones(notaActividadDetalladaDTOList);
        notasByEstudianteOfGrupoDTO.setNotaAcumulada(notaAcumulada);
        notasByEstudianteOfGrupoDTO.setPorcentajeEvaluado(porcentajeEvaluado);

        return notasByEstudianteOfGrupoDTO;
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

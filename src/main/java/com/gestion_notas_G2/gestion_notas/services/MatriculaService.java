package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.dto.EstudianteConNotaActividadListDTO;
import com.gestion_notas_G2.gestion_notas.dto.EstudianteDTO;
import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.repositories.MatriculaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    private MatriculaRepository matriculaRepository;
    private NotaActividadService notaActividadService;

    private ModelMapper modelMapper;

    public MatriculaService(MatriculaRepository matriculaRepository, NotaActividadService notaActividadService, ModelMapper modelMapper){
        this.matriculaRepository = matriculaRepository;
        this.notaActividadService = notaActividadService;
        this.modelMapper = modelMapper;
    }


    /**
     * Obtiene la lista de estudiantes matriculados en un grupo.
     *
     * @param codigoGrupo El código del grupo del que se obtendrán los estudiantes.
     * @return Una lista de objetos EstudianteDTO que representan a los estudiantes del grupo.
     */
    public List<EstudianteDTO> getEstudieantesByGrupo(Long codigoGrupo){
        List<Estudiante> estudianteList = this.matriculaRepository.findEstudiantesByGrupo_CodigoGrupo(codigoGrupo);
        return estudianteList.stream()
                .map(estudiante -> modelMapper.map(estudiante, EstudianteDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de estudiantes matriculados en un grupo con las notas de actividades.
     *
     * @param codigoGrupo El código del grupo del que se obtendrán los estudiantes con notas de actividades.
     * @return Una lista de objetos EstudianteConNotaActividadListDTO que representan a los estudiantes con sus respectivas notas de actividades del grupo.
     */
    public List<EstudianteConNotaActividadListDTO> getEstudianteListConNotaActividadListByGrupo(Long codigoGrupo) {
        List<Estudiante> estudianteList = this.matriculaRepository.findEstudiantesByGrupo_CodigoGrupo(codigoGrupo);
        return estudianteList.stream()
                .map(e -> {
                    EstudianteConNotaActividadListDTO estudianteConNotaActividadListDTO = new EstudianteConNotaActividadListDTO();
                    estudianteConNotaActividadListDTO.setId(e.getId());
                    estudianteConNotaActividadListDTO.setNombre(e.getNombre());
                    estudianteConNotaActividadListDTO.setApellido(e.getApellido());
                    estudianteConNotaActividadListDTO.setNumDocumento(e.getNumDocumento());
                    estudianteConNotaActividadListDTO.setNotas(this.notaActividadService.getNotaActividadListByEstudianteAndGrupo(e, codigoGrupo));
                    return estudianteConNotaActividadListDTO;
                })
                .collect(Collectors.toList());
    }
}

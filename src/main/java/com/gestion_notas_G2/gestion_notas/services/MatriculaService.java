package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.dto.EstudianteDTO;
import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.repositories.MatriculaRepository;

import Interfaces.IMatricula;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService implements IMatricula {

    private MatriculaRepository matriculaRepository;

    private ModelMapper modelMapper;

    public MatriculaService(MatriculaRepository matriculaRepository, ModelMapper modelMapper) {
        this.matriculaRepository = matriculaRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Obtiene la lista de estudiantes de un grupo.
     *
     * @param codigoGrupo El código del grupo del que se obtendrán los estudiantes.
     * @return Una lista de objetos EstudianteDTO que representan a los estudiantes
     *         del grupo.
     */
    public List<EstudianteDTO> getEstudieantesByGrupo(Long codigoGrupo) {
        List<Estudiante> estudianteList = this.matriculaRepository.findEstudiantesByGrupo_CodigoGrupo(codigoGrupo);
        return estudianteList.stream()
                .map(estudiante -> modelMapper.map(estudiante, EstudianteDTO.class))
                .collect(Collectors.toList());
    }
}

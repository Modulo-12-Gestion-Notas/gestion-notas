package com.gestion_notas_G2.gestion_notas.services;

import com.gestion_notas_G2.gestion_notas.models.Profesor;
import com.gestion_notas_G2.gestion_notas.repositories.ProfesorRepository;

import Interfaces.IProfesor;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfesorService implements IProfesor {
    private ProfesorRepository profesorRepository;

    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    /**
     * Recupera una lista de todos los profesores en la base de datos.
     *
     * @return Una lista de objetos Profesor que contiene toda la información de los
     *         profesores almacenados.
     */
    public List<Profesor> getProfesores() {
        List<Profesor> profesorList = profesorRepository.findAll();
        return profesorList;
    }

    /**
     * Crea un nuevo objeto Profesor en la base de datos.
     *
     * @param profesor Objeto Profesor con la información del nuevo profesor a
     *                 crear.
     * @return Un mensaje de confirmación si el profesor se crea exitosamente.
     * @throws Exception si ocurre algún error al intentar crear el profesor.
     */
    public String postProfesor(Profesor profesor) throws Exception {
        try {
            profesorRepository.save(profesor);
            return "Profesor creado exitosamente";
        } catch (Exception e) {
            throw new Exception("Error al crear el profesor");
        }
    }

}

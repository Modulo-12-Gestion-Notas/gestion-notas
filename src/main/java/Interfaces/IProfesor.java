package Interfaces;

import java.util.List;

import com.gestion_notas_G2.gestion_notas.models.Profesor;

public interface IProfesor {
    public List<Profesor> getProfesores();

    public String postProfesor(Profesor profesor) throws Exception;
}

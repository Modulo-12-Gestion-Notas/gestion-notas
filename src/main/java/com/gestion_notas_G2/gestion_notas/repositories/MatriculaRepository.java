package com.gestion_notas_G2.gestion_notas.repositories;

import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import com.gestion_notas_G2.gestion_notas.models.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    @Query("SELECT m.estudiante FROM Matricula m WHERE m.grupo.codigoGrupo = :codigoGrupo")
    List<Estudiante> findEstudiantesByGrupo_CodigoGrupo(@Param("codigoGrupo") Long codigoGrupo);
}

package com.gestion_notas_G2.gestion_notas.repositories;



import com.gestion_notas_G2.gestion_notas.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}

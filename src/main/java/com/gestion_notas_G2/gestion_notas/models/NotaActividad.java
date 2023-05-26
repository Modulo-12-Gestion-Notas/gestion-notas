package com.gestion_notas_G2.gestion_notas.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "nota_actividad")
public class NotaActividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column
    private Double calificacion;
    @ManyToOne
    private Estudiante estudiante;
    @ManyToOne
    private ActividadEvaluativa actividadEvaluativa;

    public NotaActividad() {
    }

    public NotaActividad(Long id, Double calificacion, Estudiante estudiante, ActividadEvaluativa actividadEvaluativa) {
        this.id = id;
        this.calificacion = calificacion;
        this.estudiante = estudiante;
        this.actividadEvaluativa = actividadEvaluativa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public ActividadEvaluativa getActividadEvaluativa() {
        return actividadEvaluativa;
    }

    public void setActividadEvaluativa(ActividadEvaluativa actividadEvaluativa) {
        this.actividadEvaluativa = actividadEvaluativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaActividad that = (NotaActividad) o;
        return Objects.equals(id, that.id);
    }
}

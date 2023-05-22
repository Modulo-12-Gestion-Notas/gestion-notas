package com.gestion_notas_G2.gestion_notas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "evaluacion")
public class ActividadEvaluativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String concepto;
    @Column(nullable = false)
    private int porcentaje;
    @ManyToOne
    private Grupo grupo;
    @OneToMany(mappedBy = "actividadEvaluativa", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NotaActividad> notaActividades;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean tieneNotas;

    public ActividadEvaluativa() {
    }

    public ActividadEvaluativa(Long id, String concepto, int porcentaje, Grupo grupo, List<NotaActividad> notaActividades) {
        this.id = id;
        this.concepto = concepto;
        this.porcentaje = porcentaje;
        this.grupo = grupo;
        this.notaActividades = notaActividades;
        this.tieneNotas = false;
    }

    public ActividadEvaluativa(Long idActividadEvaluativa) {
        this.id = idActividadEvaluativa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) throws Exception {
        if(porcentaje < 0 || porcentaje > 100){
            throw new IllegalArgumentException("No se pueden guardar, uno o varios porcentajes de las actividades evalautivas no están en el rango permitido de 0 a 100.");
        }
        this.porcentaje = porcentaje;
    }
    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public List<NotaActividad> getNotaActividades() {
        return notaActividades;
    }

    public void setNotaActividades(List<NotaActividad> notaActividades) {
        this.notaActividades = notaActividades;
    }

    public boolean isTieneNotas() {
        return tieneNotas;
    }

    public void setTieneNotas(boolean tieneNotas) {
        this.tieneNotas = tieneNotas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActividadEvaluativa that = (ActividadEvaluativa) o;
        return Objects.equals(id, that.id);
    }

}


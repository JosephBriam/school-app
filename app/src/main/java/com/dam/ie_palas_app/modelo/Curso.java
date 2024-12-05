package com.dam.ie_palas_app.modelo;

import com.dam.ie_palas_app.modelo.Anuncio;

public class Curso {
    private int idCurso;
    private String nombreCurso;
    private Anuncio anuncio = new Anuncio();

    public Curso(int idCurso, String nombreCurso) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
}

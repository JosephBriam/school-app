package com.dam.ie_palas_app.modelo;

public class Asistencia {

    private int id;
    private int estudianteId;
    private String fecha;
    private String estado;
    private int gradoId;
    private int cursoId;

    // Constructor
    public Asistencia(int estudianteId, String fecha, String estado, int gradoId, int cursoId) {
        this.estudianteId = estudianteId;
        this.fecha = fecha;
        this.estado = estado;
        this.gradoId = gradoId;
        this.cursoId = cursoId;
    }
    public Asistencia(){

    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getGradoId() {
        return gradoId;
    }

    public void setGradoId(int gradoId) {
        this.gradoId = gradoId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }
}


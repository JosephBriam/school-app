package com.dam.ie_palas_app.modelo;

public class Lugar {
    private int codigo;
    private int codEstudiante;
    private String lugar;
    private double latitud;
    private double longitud;

    public Lugar(int codigo, int codPersona, String lugar, double latitud, double longitud) {
        this.codigo = codigo;
        this.codEstudiante = codPersona;
        this.lugar = lugar;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodPersona() {
        return codEstudiante;
    }

    public void setCodPersona(int codPersona) {
        this.codEstudiante = codPersona;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}

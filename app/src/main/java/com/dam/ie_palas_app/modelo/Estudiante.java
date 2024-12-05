package com.dam.ie_palas_app.modelo;

public class Estudiante {
    private int EstudianteId;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String contrasena;
    private int grado;

    public Estudiante(int EstudianteId, String nombres, String apellidos, String usuario, String contrasena, int grado ) {
        this.EstudianteId = EstudianteId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.grado = grado;
    }

    public Estudiante(String nombres, String apellidos, String usuario, String contrasena, int grado) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.grado = grado;
    }
    public int getEstudianteId() {return EstudianteId;}
    public void setEstudianteId(int estudianteId) {EstudianteId = estudianteId;}

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasena() {return contrasena;}
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}

    public int getGrado() {return grado;}

    public void setGrado(int grado) {this.grado = grado;}
}

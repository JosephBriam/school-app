package com.dam.ie_palas_app.modelo;

public class Docente {
    private int idDocente; // Nuevo campo
    private String nombres;
    private String apellidos;
    private String usuario;
    private String contraseña;
    private Asistencia asistencia = new Asistencia();

    public Docente(int idDocente, String nombres, String apellidos, String usuario, String contraseña) {
        this.idDocente = idDocente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    // Constructor sin ID (opcional, para facilitar el registro)
    public Docente(String nombres, String apellidos, String usuario, String contraseña) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    // Getters y setters

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}

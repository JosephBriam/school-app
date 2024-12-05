package com.dam.ie_palas_app.controlador;

import android.content.Context;
import com.dam.ie_palas_app.modelo.Apoderado;
import com.dam.ie_palas_app.negocio.nApoderado;
import java.util.List;

public class cApoderado {

    private nApoderado negocioApoderado;

    public cApoderado(Context context) {
        negocioApoderado = new nApoderado(context);
    }

    public long crearApoderado(String nombres, String apellidos, String usuario, String contraseña) {
        Apoderado apoderado = new Apoderado(0, nombres, apellidos, usuario, contraseña);
        return negocioApoderado.insertarApoderado(apoderado);
    }

    public List<Apoderado> obtenerTodos() {
        return negocioApoderado.obtenerTodos();
    }

    public Apoderado obtenerPorId(int id) {
        return negocioApoderado.obtenerPorId(id);
    }

    public int actualizarApoderado(int id, String nombres, String apellidos, String usuario, String contraseña) {
        Apoderado apoderado = new Apoderado(id, nombres, apellidos, usuario, contraseña);
        return negocioApoderado.actualizarApoderado(apoderado);
    }

    public int eliminarApoderado(int id) {
        return negocioApoderado.eliminarApoderado(id);
    }
}

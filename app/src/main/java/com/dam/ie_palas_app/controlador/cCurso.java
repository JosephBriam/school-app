package com.dam.ie_palas_app.controlador;

import android.content.Context;

import com.dam.ie_palas_app.modelo.Curso;
import com.dam.ie_palas_app.negocio.nCurso;

import java.util.ArrayList;

public class cCurso {
    private nCurso negocioCurso;

    public cCurso(Context context) {
        negocioCurso = new nCurso(context);
    }

    // Método para agregar un curso
    public void agregarCurso(Curso curso) {
        negocioCurso.agregarCurso(curso);
    }

    // Método para obtener todos los cursos
    public ArrayList<Curso> obtenerCursos() {
        return negocioCurso.obtenerCursos();
    }

    // Método para actualizar un curso
    public void updateCurso(Curso curso) {
        negocioCurso.updateCurso(curso);
    }

    // Método para eliminar un curso
    public void deleteCurso(int idCurso) {
        negocioCurso.deleteCurso(idCurso);
    }
}

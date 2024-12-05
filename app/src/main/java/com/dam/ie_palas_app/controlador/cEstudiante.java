package com.dam.ie_palas_app.controlador;

import android.content.Context;
import com.dam.ie_palas_app.modelo.Estudiante;
import com.dam.ie_palas_app.negocio.nEstudiante;
import java.util.ArrayList;

public class cEstudiante {

    private nEstudiante nEstudiante;

    public cEstudiante(Context context) {
        nEstudiante = new nEstudiante(context);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        nEstudiante.agregarEstudiante(estudiante);
    }

    public ArrayList<Estudiante> obtenerEstudiante() {
        return nEstudiante.obtenerEstudiante();
    }
    public ArrayList<String> obtenerCodigosEstudiantes() {
        return nEstudiante.obtenerCodigosEstudiantes();
    }
    public ArrayList<Estudiante> obtenerEstudiantesPorGrado(int grado) {
        return nEstudiante.obtenerEstudiantesPorGrado(grado);
    }

    public void updateEstudiante(Estudiante estudiante) {
        nEstudiante.updateEstudiante(estudiante);
    }

    public void deleteEstudiantes(int idEstudiante) {
        nEstudiante.deleteEstudiantes(idEstudiante);
    }
}



package com.dam.ie_palas_app.controlador;

import android.content.Context;
import com.dam.ie_palas_app.modelo.Docente;
import com.dam.ie_palas_app.negocio.nDocente;

import java.util.ArrayList;

public class cDocente {
    private nDocente negocioDocente;

    public cDocente(Context context) {
        negocioDocente = new nDocente(context);
    }

    public void agregarDocente(Docente docente) {
        negocioDocente.agregarDocente(docente);
    }

    public ArrayList<Docente> obtenerDocentes() {
        return negocioDocente.obtenerDocentes();
    }

    public void updateDocente(Docente docente) {
        negocioDocente.updateDocente(docente);
    }

    public void deleteDocente(int idDocente) {
        negocioDocente.deleteDocente(idDocente);
    }
}

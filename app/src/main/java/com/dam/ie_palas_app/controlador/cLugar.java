package com.dam.ie_palas_app.controlador;

import android.content.Context;

import com.dam.ie_palas_app.modelo.Lugar;
import com.dam.ie_palas_app.negocio.nLugar;

import java.util.List;

public class cLugar {
    private final nLugar negocioLugar;

    public cLugar(Context context) {
        negocioLugar = new nLugar(context);
    }

    public void insertarLugar(int cod_persona, String lugar, double latitud, double longitud) {
        negocioLugar.registrarLugarVisitado(cod_persona, lugar, latitud, longitud);
    }

    public List<Lugar> obtenerLugarPorPersona(int codPersona) {
        return negocioLugar.obtenerLugarVisitadosPorPersona(codPersona);
    }
}

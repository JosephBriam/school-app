package com.dam.ie_palas_app.negocio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dam.ie_palas_app.dao.BasedeDatos;
import com.dam.ie_palas_app.modelo.Lugar;

import java.util.ArrayList;
import java.util.List;

public class nLugar {
    private final BasedeDatos baseDeDatos;

    public nLugar(Context context) {
        baseDeDatos = new BasedeDatos(context);
    }

    public void registrarLugarVisitado(int codEstudiante, String lugar, double latitud, double longitud) {
        SQLiteDatabase db = baseDeDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cod_estudiante", codEstudiante);
        values.put("lugar", lugar);
        values.put("latitud", latitud);
        values.put("longitud", longitud);
        db.insert(BasedeDatos.TABLE_LUGARES, null, values);
        db.close();
    }

    public List<Lugar> obtenerLugarVisitadosPorPersona(int codEstudiante) {
        List<Lugar> lugaresList = new ArrayList<>();
        SQLiteDatabase db = baseDeDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BasedeDatos.TABLE_LUGARES + " WHERE cod_estudiante = ?", new String[]{String.valueOf(codEstudiante)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int codigo = cursor.getInt(cursor.getColumnIndexOrThrow("codigo"));
                int codPersonaDb = cursor.getInt(cursor.getColumnIndexOrThrow("cod_estudiante"));
                String lugar = cursor.getString(cursor.getColumnIndexOrThrow("lugar"));
                double latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud"));
                double longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud"));

                Lugar lugarVisitado = new Lugar(codigo, codPersonaDb, lugar, latitud, longitud);
                lugaresList.add(lugarVisitado);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return lugaresList;
    }
}


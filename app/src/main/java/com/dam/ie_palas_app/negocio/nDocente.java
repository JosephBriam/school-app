package com.dam.ie_palas_app.negocio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.dam.ie_palas_app.dao.BasedeDatos;
import com.dam.ie_palas_app.modelo.Docente;

import java.util.ArrayList;

public class nDocente {
    private BasedeDatos dbHelper;

    public nDocente(Context context) {
        dbHelper = new BasedeDatos(context);
    }

    public void agregarDocente(Docente docente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("Nombres", docente.getNombres());
            values.put("Apellidos", docente.getApellidos());
            values.put("Usuario", docente.getUsuario());
            values.put("Contraseña", docente.getContraseña());

            db.insert(BasedeDatos.TABLE_DOCENTE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public ArrayList<Docente> obtenerDocentes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Docente> docentes = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + BasedeDatos.TABLE_DOCENTE, null);
            if (cursor.moveToFirst()) {
                do {
                    docentes.add(new Docente(
                            cursor.getInt(cursor.getColumnIndexOrThrow("IdDocente")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Nombres")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Apellidos")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Usuario")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Contraseña"))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return docentes;
    }

    public void updateDocente(Docente docente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("Nombres", docente.getNombres());
            values.put("Apellidos", docente.getApellidos());
            values.put("Usuario", docente.getUsuario());
            values.put("Contraseña", docente.getContraseña());

            db.update(BasedeDatos.TABLE_DOCENTE, values, "IdDocente=?", new String[]{String.valueOf(docente.getIdDocente())});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteDocente(int idDocente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete(BasedeDatos.TABLE_DOCENTE, "IdDocente=?", new String[]{String.valueOf(idDocente)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}

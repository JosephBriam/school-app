package com.dam.ie_palas_app.negocio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.dam.ie_palas_app.dao.BasedeDatos;
import com.dam.ie_palas_app.modelo.Apoderado;
import java.util.ArrayList;
import java.util.List;

public class nApoderado {
    private BasedeDatos dbHelper;

    public nApoderado(Context context) {
        dbHelper = new BasedeDatos(context);
    }

    public long insertarApoderado(Apoderado apoderado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Nombres", apoderado.getNombres());
        values.put("Apellidos", apoderado.getApellidos());
        values.put("Usuario", apoderado.getUsuario());
        values.put("Contraseña", apoderado.getContraseña());

        long id = db.insert(BasedeDatos.TABLE_PADRES, null, values);
        db.close();
        return id;
    }

    public List<Apoderado> obtenerTodos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Apoderado> apoderados = new ArrayList<>();
        Cursor cursor = db.query(BasedeDatos.TABLE_PADRES, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Apoderado apoderado = new Apoderado(
                        cursor.getInt(cursor.getColumnIndexOrThrow("IdPadre")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Nombres")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Apellidos")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Usuario")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Contraseña"))
                );
                apoderados.add(apoderado);
            }
            cursor.close();
        }
        db.close();
        return apoderados;
    }

    public Apoderado obtenerPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(BasedeDatos.TABLE_PADRES, null, "IdPadre = ?", new String[]{String.valueOf(id)}, null, null, null);
        Apoderado apoderado = null;

        if (cursor != null && cursor.moveToFirst()) {
            apoderado = new Apoderado(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IdPadre")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Nombres")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Apellidos")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Usuario")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Contraseña"))
            );
            cursor.close();
        }
        db.close();
        return apoderado;
    }

    public int actualizarApoderado(Apoderado apoderado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Nombres", apoderado.getNombres());
        values.put("Apellidos", apoderado.getApellidos());
        values.put("Usuario", apoderado.getUsuario());
        values.put("Contraseña", apoderado.getContraseña());

        int rowsAffected = db.update(BasedeDatos.TABLE_PADRES, values, "IdPadre = ?", new String[]{String.valueOf(apoderado.getIdPadre())});
        db.close();
        return rowsAffected;
    }

    public int eliminarApoderado(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(BasedeDatos.TABLE_PADRES, "IdPadre = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }
}

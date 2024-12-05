package com.dam.ie_palas_app.negocio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dam.ie_palas_app.dao.BasedeDatos;
import com.dam.ie_palas_app.modelo.Curso;

import java.util.ArrayList;

public class nCurso {
    private BasedeDatos dbHelper;

    public nCurso(Context context) {
        dbHelper = new BasedeDatos(context);
    }

    public void agregarCurso(Curso curso) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("NombreCurso", curso.getNombreCurso());

            db.insert(BasedeDatos.TABLE_CURSOS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    // Método para obtener todos los cursos
    public ArrayList<Curso> obtenerCursos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Curso> cursos = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + BasedeDatos.TABLE_CURSOS, null);
            if (cursor.moveToFirst()) {
                do {
                    cursos.add(new Curso(
                            cursor.getInt(cursor.getColumnIndexOrThrow("IdCurso")),
                            cursor.getString(cursor.getColumnIndexOrThrow("NombreCurso"))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return cursos;
    }

    // Método para actualizar un curso
    public void updateCurso(Curso curso) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("NombreCurso", curso.getNombreCurso());

            db.update(BasedeDatos.TABLE_CURSOS, values, "IdCurso=?", new String[]{String.valueOf(curso.getIdCurso())});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    // Método para eliminar un curso
    public void deleteCurso(int idCurso) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete(BasedeDatos.TABLE_CURSOS, "IdCurso=?", new String[]{String.valueOf(idCurso)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}

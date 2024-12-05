package com.dam.ie_palas_app.negocio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dam.ie_palas_app.dao.BasedeDatos;
import com.dam.ie_palas_app.modelo.Estudiante;

import java.util.ArrayList;

public class nEstudiante {

    private BasedeDatos dbHelper;
    private SQLiteDatabase db;

    public nEstudiante(Context context) {
        dbHelper = new BasedeDatos(context);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("Nombres", estudiante.getNombres());
            values.put("Apellidos", estudiante.getApellidos());
            values.put("Usuario", estudiante.getUsuario());
            values.put("Contraseña", estudiante.getContrasena());
            values.put("Grado", estudiante.getGrado());

            db.insert(BasedeDatos.TABLE_ESTUDIANTES, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public ArrayList<Estudiante> obtenerEstudiante() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Estudiante> estudiantes= new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + BasedeDatos.TABLE_ESTUDIANTES, null);
            if (cursor.moveToFirst()) {
                do {
                    estudiantes.add(new Estudiante(
                            cursor.getInt(cursor.getColumnIndexOrThrow("EstudianteId")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Nombres")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Apellidos")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Usuario")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Contraseña")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("Grado"))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return estudiantes;
    }
    public ArrayList<Estudiante> obtenerEstudiantesPorGrado(int grado) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + BasedeDatos.TABLE_ESTUDIANTES + " WHERE Grado = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(grado)});

            if (cursor.moveToFirst()) {
                do {
                    estudiantes.add(new Estudiante(
                            cursor.getInt(cursor.getColumnIndexOrThrow("EstudianteId")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Nombres")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Apellidos")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Usuario")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Contraseña")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("Grado"))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return estudiantes;
    }
    public Cursor obtenerEstudiantes() {
        db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + BasedeDatos.TABLE_ESTUDIANTES, null);
    }
    public ArrayList<String> obtenerCodigosEstudiantes() {
        ArrayList<String> codigos = new ArrayList<>();
        Cursor cursor = obtenerEstudiantes(); // Obtén todas las personas desde la base de datos
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int codigo = cursor.getInt(cursor.getColumnIndexOrThrow("EstudianteId"));
                codigos.add(String.valueOf(codigo));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return codigos;
    }
    public void updateEstudiante(Estudiante estudiante) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("Nombres", estudiante.getNombres());
            values.put("Apellidos", estudiante.getApellidos());
            values.put("Usuario", estudiante.getUsuario());
            values.put("Contraseña", estudiante.getContrasena());
            values.put("Grado", estudiante.getGrado());

            // Actualiza el registro basado en el IdDocente
            db.update(BasedeDatos.TABLE_ESTUDIANTES, values, "EstudianteId=?", new String[]{String.valueOf(estudiante.getEstudianteId())});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteEstudiantes(int idEstudiante){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            // Elimina el registro basado en el IdEstudiante
            db.delete(BasedeDatos.TABLE_ESTUDIANTES, "EstudianteId=?", new String[]{String.valueOf(idEstudiante)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

}

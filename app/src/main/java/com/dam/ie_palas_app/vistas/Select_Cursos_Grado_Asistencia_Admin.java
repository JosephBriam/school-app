package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

import java.util.ArrayList;
import java.util.List;

public class Select_Cursos_Grado_Asistencia_Admin extends AppCompatActivity {

    Spinner spListaC, spListaG;
    private BasedeDatos basedeDatos; // Instancia de la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cursos_grado_asistencia_admin);

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        // Configurar Spinners
        spListaC = findViewById(R.id.spListCurso);
        spListaG = findViewById(R.id.spListGrado);

        // Cargar datos de la base de datos
        cargarCursosDesdeBD();
        cargarGradosDesdeBD();
    }

    private void cargarCursosDesdeBD() {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT NombreCurso FROM " + BasedeDatos.TABLE_CURSOS, null);

        List<String> listaCursos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                listaCursos.add(cursor.getString(0)); // Obtener el nombre del curso
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaCursos
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spListaC.setAdapter(adapter1);
    }

    private void cargarGradosDesdeBD() {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Nombre FROM " + BasedeDatos.TABLE_GRADOS, null);

        List<String> listaGrados = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                listaGrados.add(cursor.getString(0)); // Obtener el nombre del grado
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaGrados
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spListaG.setAdapter(adapter2);
    }

    public void Confirmar(View view) {
        // Obtener el grado y curso seleccionados como cadenas
        String cursoSeleccionado = spListaC.getSelectedItem().toString();
        String gradoSeleccionado = spListaG.getSelectedItem().toString();

        // Crear Intent para abrir MenuAsistencia y pasar curso y grado seleccionados
        Intent intent = new Intent(this, MenuAsistencia.class);
        intent.putExtra("curso", cursoSeleccionado);
        intent.putExtra("grado", gradoSeleccionado);

        // Iniciar la actividad
        startActivity(intent);
    }

    public void atrasN(View view) {
        finish();
    }
}

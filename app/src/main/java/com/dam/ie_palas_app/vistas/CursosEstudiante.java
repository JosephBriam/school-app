package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cCurso;
import com.dam.ie_palas_app.modelo.Curso;

import java.util.ArrayList;

public class CursosEstudiante extends AppCompatActivity {

    private int estudianteId;
    private LinearLayout cursosLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos_estudiante);

        // Obtener el ID del estudiante desde el Intent
        estudianteId = getIntent().getIntExtra("estudianteId", -1);

        // Verificación del ID del estudiante
        if (estudianteId != -1) {
            Log.d("Debug", "Estudiante ID: " + estudianteId); // Log correcto para depuración
        } else {
            Log.d("Debug", "Error: Estudiante ID no recibido correctamente");
        }

        // Inicializar el contenedor donde se agregarán los botones de cursos
        cursosLayout = findViewById(R.id.layoutCursos);

        // Cargar los cursos desde la base de datos y mostrarlos
        cargarCursos();
    }

    private void cargarCursos() {
        cCurso controladorCur = new cCurso(this);
        ArrayList<Curso> cursos = controladorCur.obtenerCursos(); // Obtener todos los cursos

        // Verificar si existen cursos
        if (cursos.isEmpty()) {
            Log.d("Debug", "No hay cursos disponibles.");
            return;
        }

        // Crear un botón para cada curso en la base de datos
        for (Curso curso : cursos) {
            Button cursoButton = new Button(this);
            cursoButton.setText(curso.getNombreCurso()); // Asignar el nombre del curso al botón

            // Establecer los atributos del botón (color azul, texto blanco, tamaño de letra 18sp)
            cursoButton.setBackgroundColor(getResources().getColor(R.color.blue)); // Color de fondo azul
            cursoButton.setTextColor(getResources().getColor(android.R.color.white)); // Color del texto blanco
            cursoButton.setTextSize(18); // Tamaño de la letra 18sp

            // Redondear los bordes del botón
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(20); // Radio de los bordes redondeados
            shape.setColor(getResources().getColor(R.color.blue)); // Establecer color de fondo en el ShapeDrawable
            cursoButton.setBackground(shape);

            // Agregar un margen inferior de 20dp entre los botones
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20); // Margen inferior de 20dp
            cursoButton.setLayoutParams(params);

            // Asignar el comportamiento de cada botón (ver asistencia)
            cursoButton.setOnClickListener(v -> verAsistencia(curso));

            // Agregar el botón al contenedor de cursos en la interfaz
            cursosLayout.addView(cursoButton);
        }
    }

    private void verAsistencia(Curso curso) {
        // Verificar si el estudianteId es válido antes de continuar
        if (estudianteId != -1) {
            Intent intent = new Intent(CursosEstudiante.this, formAsistencias.class);
            intent.putExtra("curso", curso.getNombreCurso());
            intent.putExtra("estudianteId", estudianteId); // Pasa el ID del estudiante
            startActivity(intent);
        } else {
            Log.e("Error", "No se puede proceder, Estudiante ID es inválido.");
        }
    }
    public void Regresar(View view) {
        finish(); // Cierra la actividad actual
    }
}
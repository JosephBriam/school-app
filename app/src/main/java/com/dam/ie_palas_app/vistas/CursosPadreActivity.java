package com.dam.ie_palas_app.vistas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CursosPadreActivity extends AppCompatActivity {

    private TextView textViewNombreCurso, textViewAsistencias, textViewTardanzas, textViewFaltas;
    private Spinner spinnerHijos, spinnerCursos;
    private Button btnSelectDate, btnRegresar;
    private BasedeDatos basedeDatos;
    private GraphView graph;
    private int padreId;
    private int estudianteId;
    private String curso;
    private String fechaSeleccionada;
    private TextView textViewFechaSeleccionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos_padre);

        // Inicializar vistas
        textViewNombreCurso = findViewById(R.id.textViewCurso);
        textViewAsistencias = findViewById(R.id.numberAsistencias);
        textViewTardanzas = findViewById(R.id.numberTardanzas);
        textViewFaltas = findViewById(R.id.numberFaltas);
        spinnerHijos = findViewById(R.id.spinnerHijos);
        spinnerCursos = findViewById(R.id.spinnerCursos);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnRegresar = findViewById(R.id.buttonRegresar);
        graph = findViewById(R.id.graph);
        // Inicializar el TextView para mostrar la fecha seleccionada
        textViewFechaSeleccionada = findViewById(R.id.textViewFechaSeleccionada);
        // Inicializar base de datos
        basedeDatos = new BasedeDatos(this);

        // Obtener el padreId del Intent
        Intent intent = getIntent();
        padreId = intent.getIntExtra("padreId", -1);

        // Llenar el spinner de hijos
        cargarSpinnerHijos();

        // Llenar el spinner de cursos
        cargarSpinnerCursos();

        // Configurar el botón para seleccionar la fecha
        btnSelectDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(this, (view1, year1, month1, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year1, month1, dayOfMonth);
                fechaSeleccionada = new SimpleDateFormat("dd/MM/yyyy").format(selectedDate.getTime());
                // Mostrar la fecha seleccionada en el TextView
                textViewFechaSeleccionada.setText("Fecha seleccionada: " + fechaSeleccionada);
                cargarEstadisticas(curso, estudianteId, fechaSeleccionada);
            }, year, month, day).show();
        });

        // Botón Regresar
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void cargarSpinnerHijos() {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Cursor cursorEstudiantes = db.rawQuery(
                "SELECT E.EstudianteId, E.Nombres, E.Apellidos FROM Estudiantes E " +
                        "JOIN PadresEstudiantes PE ON E.EstudianteId = PE.EstudianteId " +
                        "WHERE PE.PadreId = ?",
                new String[]{String.valueOf(padreId)}
        );

        List<String> estudiantesList = new ArrayList<>();
        List<Integer> estudiantesIds = new ArrayList<>();

        while (cursorEstudiantes.moveToNext()) {
            String nombreCompleto = cursorEstudiantes.getString(1) + " " + cursorEstudiantes.getString(2);
            estudiantesList.add(nombreCompleto);
            estudiantesIds.add(cursorEstudiantes.getInt(0)); // Guardar el ID del estudiante
        }
        cursorEstudiantes.close();

        ArrayAdapter<String> estudiantesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estudiantesList);
        estudiantesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHijos.setAdapter(estudiantesAdapter);

        // Configurar el listener de selección de estudiante
        spinnerHijos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                estudianteId = estudiantesIds.get(position); // Obtener el ID del estudiante seleccionado
                Log.d("Debug", "Estudiante seleccionado: " + estudianteId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void cargarSpinnerCursos() {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Cursor cursorCursos = db.rawQuery("SELECT IdCurso, NombreCurso FROM Cursos", null);

        List<String> cursosList = new ArrayList<>();

        while (cursorCursos.moveToNext()) {
            cursosList.add(cursorCursos.getString(1));
        }
        cursorCursos.close();

        ArrayAdapter<String> cursosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cursosList);
        cursosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCursos.setAdapter(cursosAdapter);

        // Configurar el listener de selección de curso
        spinnerCursos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                curso = parentView.getItemAtPosition(position).toString();
                textViewNombreCurso.setText("Curso: " + curso);
                Log.d("Debug", "Curso seleccionado: " + curso);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void cargarEstadisticas(String curso, int estudianteId, String fechaSeleccionada) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();

        int totalAsistencias = 0;
        int totalTardanzas = 0;
        int totalFaltas = 0;

        try {
            // Obtener el CursoID basado en el nombre del curso
            Cursor cursoCursor = db.rawQuery("SELECT IdCurso FROM Cursos WHERE NombreCurso = ?", new String[]{curso});
            int cursoID = -1;
            if (cursoCursor.moveToFirst()) {
                cursoID = cursoCursor.getInt(0);
                Log.d("Debug", "CursoID encontrado: " + cursoID);
            }
            cursoCursor.close();

            if (cursoID == -1) {
                Log.e("Error", "Curso no encontrado en la base de datos para nombre: " + curso);
                return;
            }

            // Filtrar por curso, estudiante y fecha seleccionada para las estadísticas
            Cursor cursorAsistencias = db.rawQuery(
                    "SELECT COUNT(*) FROM Asistencias WHERE CursoID = ? AND EstudianteId = ? AND Estado = 'Asistió' AND Fecha = ?",
                    new String[]{String.valueOf(cursoID), String.valueOf(estudianteId), fechaSeleccionada});
            if (cursorAsistencias.moveToFirst()) {
                totalAsistencias = cursorAsistencias.getInt(0);
                Log.d("Debug", "Total Asistencias: " + totalAsistencias);
            }
            cursorAsistencias.close();

            Cursor cursorTardanzas = db.rawQuery(
                    "SELECT COUNT(*) FROM Asistencias WHERE CursoID = ? AND EstudianteId = ? AND Estado = 'Tarde' AND Fecha = ?",
                    new String[]{String.valueOf(cursoID), String.valueOf(estudianteId), fechaSeleccionada});
            if (cursorTardanzas.moveToFirst()) {
                totalTardanzas = cursorTardanzas.getInt(0);
                Log.d("Debug", "Total Tardanzas: " + totalTardanzas);
            }
            cursorTardanzas.close();

            Cursor cursorFaltas = db.rawQuery(
                    "SELECT COUNT(*) FROM Asistencias WHERE CursoID = ? AND EstudianteId = ? AND Estado = 'Faltó' AND Fecha = ?",
                    new String[]{String.valueOf(cursoID), String.valueOf(estudianteId), fechaSeleccionada});
            if (cursorFaltas.moveToFirst()) {
                totalFaltas = cursorFaltas.getInt(0);
                Log.d("Debug", "Total Faltas: " + totalFaltas);
            }
            cursorFaltas.close();

            // Actualizar TextViews
            textViewAsistencias.setText(String.valueOf(totalAsistencias));
            textViewTardanzas.setText(String.valueOf(totalTardanzas));
            textViewFaltas.setText(String.valueOf(totalFaltas));

            // Crear gráfico de barras con los resultados
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, totalAsistencias),
                    new DataPoint(1, totalTardanzas),
                    new DataPoint(2, totalFaltas)
            });
            graph.removeAllSeries();
            graph.addSeries(series);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Error en cargarEstadisticas: " + e.getMessage());
        }
    }
    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (basedeDatos != null) {
            basedeDatos.close();
        }
    }
}

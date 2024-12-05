package com.dam.ie_palas_app.vistas;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

import java.util.Calendar;

public class InformeAsistencia_admin extends AppCompatActivity {

    private TextView tvSelectedDate;
    private Spinner spinnerGrades;
    private Spinner spinnerCourses;
    private BasedeDatos basedeDatos;
    private ProgressBar progressBarAsistieron;
    private ProgressBar progressBarNoAsistieron;
    private ProgressBar progressBarLlegaronTarde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_asistencia_admin);

        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedDate.setText("Fecha seleccionada: Ninguna");  // Valor inicial

        spinnerGrades = findViewById(R.id.spinnerGrades);
        spinnerCourses = findViewById(R.id.spinnerCourses);
        Button btnSelectDate = findViewById(R.id.btnSelectDate);
        Button btnVerEstadisticas = findViewById(R.id.btnVerEstadisticas);
        basedeDatos = new BasedeDatos(this);

        // Inicializar los ProgressBar
        progressBarAsistieron = findViewById(R.id.progressBarAsistieron);
        progressBarNoAsistieron = findViewById(R.id.progressBarNoAsistieron);
        progressBarLlegaronTarde = findViewById(R.id.progressBarLlegaronTarde);

        // Inicialmente, ocultar los ProgressBar
        progressBarAsistieron.setVisibility(View.INVISIBLE);
        progressBarNoAsistieron.setVisibility(View.INVISIBLE);
        progressBarLlegaronTarde.setVisibility(View.INVISIBLE);

        // Configurar el botón para seleccionar fecha
        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());
        // Configurar el botón para ver estadísticas
        btnVerEstadisticas.setOnClickListener(v -> calcularEstadisticas());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            tvSelectedDate.setText("Fecha seleccionada: " + selectedDate);// Actualizar fecha seleccionada
        }, year, month, day);
        datePickerDialog.show();
    }

    private void calcularAsistencia(String grade, String course, String date) {
        if (grade == null || course == null || date.isEmpty()) {
            Toast.makeText(this, "Por favor seleccione un grado, curso y fecha.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = basedeDatos.getReadableDatabase();

        // Verificar ID del grado
        Cursor cursorGrado = db.rawQuery("SELECT IdGrado FROM Grados WHERE Nombre = ?", new String[]{grade});
        if (!cursorGrado.moveToFirst()) {
            Toast.makeText(this, "Grado no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }
        int gradoID = cursorGrado.getInt(0);
        cursorGrado.close();

        // Verificar ID del curso
        Cursor cursorCurso = db.rawQuery("SELECT IdCurso FROM Cursos WHERE NombreCurso = ?", new String[]{course});
        if (!cursorCurso.moveToFirst()) {
            Toast.makeText(this, "Curso no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }
        int cursoID = cursorCurso.getInt(0);
        cursorCurso.close();

        // Contar asistencias
        Cursor cursorAssisted = db.rawQuery(
                "SELECT COUNT(*) FROM Asistencias WHERE GradoID = ? AND CursoID = ? AND Fecha = ? AND Estado = 'Asistió'",
                new String[]{String.valueOf(gradoID), String.valueOf(cursoID), date});
        cursorAssisted.moveToFirst();
        int totalAssisted = cursorAssisted.getInt(0);
        cursorAssisted.close();

        // Contar no asistencias
        Cursor cursorNoAssisted = db.rawQuery(
                "SELECT COUNT(*) FROM Asistencias WHERE GradoID = ? AND CursoID = ? AND Fecha = ? AND Estado = 'Faltó'",
                new String[]{String.valueOf(gradoID), String.valueOf(cursoID), date});
        cursorNoAssisted.moveToFirst();
        int totalNoAssisted = cursorNoAssisted.getInt(0);
        cursorNoAssisted.close();

        // Contar llegadas tarde
        Cursor cursorLate = db.rawQuery(
                "SELECT COUNT(*) FROM Asistencias WHERE GradoID = ? AND CursoID = ? AND Fecha = ? AND Estado = 'Tarde'",
                new String[]{String.valueOf(gradoID), String.valueOf(cursoID), date});
        cursorLate.moveToFirst();
        int totalLate = cursorLate.getInt(0);
        cursorLate.close();

        // Contar estudiantes
        Cursor cursorTotal = db.rawQuery("SELECT COUNT(*) FROM Estudiantes WHERE Grado = ?",
                new String[]{String.valueOf(convertirGrado(grade))});
        cursorTotal.moveToFirst();
        int totalStudents = cursorTotal.getInt(0);
        cursorTotal.close();

        if (totalStudents == 0) {
            Toast.makeText(this, "No hay estudiantes en este grado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calcular porcentaje
        int percentageAssisted = (int) ((totalAssisted / (float) totalStudents) * 100);
        int percentageNoAssisted = (int) ((totalNoAssisted / (float) totalStudents) * 100);
        int percentageLate = (int) ((totalLate / (float) totalStudents) * 100);

        // Hacer visibles los ProgressBar y actualizar sus valores
        progressBarAsistieron.setVisibility(View.VISIBLE);
        progressBarNoAsistieron.setVisibility(View.VISIBLE);
        progressBarLlegaronTarde.setVisibility(View.VISIBLE);

        progressBarAsistieron.setProgress(percentageAssisted);
        progressBarNoAsistieron.setProgress(percentageNoAssisted);
        progressBarLlegaronTarde.setProgress(percentageLate);

        // Mostrar porcentaje en texto
        Toast.makeText(this, "Porcentaje de asistencia: " + percentageAssisted + "%", Toast.LENGTH_SHORT).show();
    }

    private void calcularEstadisticas() {
        String selectedGrade = spinnerGrades.getSelectedItem().toString();
        String selectedCourse = spinnerCourses.getSelectedItem().toString();
        String selectedDate = tvSelectedDate.getText().toString();

        // Validar si no se ha seleccionado una fecha
        if (selectedGrade.isEmpty() || selectedCourse.isEmpty() || selectedDate.contains("Ninguna")) {
            Toast.makeText(this, "Por favor seleccione un grado, curso y fecha.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Si pasa la validación, elimina el prefijo para usar solo la fecha:
        selectedDate = selectedDate.replace("Fecha seleccionada: ", "").trim();
        // Calcular estadísticas
        calcularAsistencia(selectedGrade, selectedCourse, selectedDate);

    }

    private int convertirGrado(String grado) {
        switch (grado) {
            case "Primero":
                return 1;
            case "Segundo":
                return 2;
            case "Tercero":
                return 3;
            case "Cuarto":
                return 4;
            case "Quinto":
                return 5;
            case "Sexto":
                return 6;
            default:
                return -1; // En caso de que no coincida con ningún grado
        }
    }

    public void RegresarPaginaAnterior(View view) {
        finish();  // Cerrar la actividad actual y regresar a la anterior
    }
}

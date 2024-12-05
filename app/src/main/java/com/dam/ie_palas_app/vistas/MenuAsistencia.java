package com.dam.ie_palas_app.vistas;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

import java.util.Calendar;

public class MenuAsistencia extends AppCompatActivity {

    private TextView tvSelectedDate, tvGradoSeleccionado, tvCursoSeleccionado;
    private LinearLayout estudiantesContainer;
    private String gradoSeleccionado;
    private String cursoSeleccionado;
    private BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_asistencia);

        // Enlazar vistas
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvGradoSeleccionado = findViewById(R.id.tvGradoSeleccionado);
        tvCursoSeleccionado = findViewById(R.id.tvCursoSeleccionado);
        estudiantesContainer = findViewById(R.id.estudiantesContainer);
        Button btnSelectDate = findViewById(R.id.btnSelectDate);
        Button buttonConfirmar = findViewById(R.id.buttonConfirmar);

        // Inicializa el campo de fecha con un valor claro
        tvSelectedDate.setText("Fecha no seleccionada");

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        // Obtener los datos del Intent
        gradoSeleccionado = getIntent().getStringExtra("grado");
        cursoSeleccionado = getIntent().getStringExtra("curso");

        // Verificar que los valores se hayan obtenido correctamente
        if (gradoSeleccionado == null || cursoSeleccionado == null) {
            Toast.makeText(this, "Error al obtener grado o curso", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar los datos en los TextView
        tvGradoSeleccionado.setText("Grado seleccionado: " + gradoSeleccionado);
        tvCursoSeleccionado.setText("Curso seleccionado: " + cursoSeleccionado);

        // Llamar al método para cargar los estudiantes del grado y curso seleccionados
        cargarEstudiantes(gradoSeleccionado, cursoSeleccionado);

        // Configurar el botón para seleccionar la fecha
        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        // Manejar botón de confirmar
        buttonConfirmar.setOnClickListener(v -> confirmarAsistencia());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            tvSelectedDate.setText("Fecha seleccionada: " + selectedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void cargarEstudiantes(String grado, String curso) {
        // Convertir el grado a su valor numérico
        int gradoNumerico = convertirGrado(grado);

        // Verificar si la conversión del grado fue correcta
        if (gradoNumerico == -1) {
            Toast.makeText(this, "Grado no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener los estudiantes desde la base de datos SQLite
        SQLiteDatabase db = basedeDatos.getReadableDatabase();

        String query = "SELECT EstudianteId, Nombres, Apellidos FROM " + BasedeDatos.TABLE_ESTUDIANTES +
                " WHERE Grado = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(gradoNumerico)});

        estudiantesContainer.removeAllViews(); // Limpiar contenedor

        if (cursor.moveToFirst()) {
            do {
                // Obtener los datos del estudiante
                int estudianteId = cursor.getInt(0);
                String nombres = cursor.getString(1);
                String apellidos = cursor.getString(2);

                // Crear la vista del estudiante
                View itemView = LayoutInflater.from(this).inflate(R.layout.item_estudiante, estudiantesContainer, false);

                TextView tvEstudianteNombre = itemView.findViewById(R.id.tvEstudianteNombre);
                Spinner spinnerAsistencia = itemView.findViewById(R.id.spinnerAsistencia);

                // Mostrar el nombre completo del estudiante
                tvEstudianteNombre.setText(nombres + " " + apellidos);

                // Configurar el Spinner con las opciones de asistencia
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.asistencia_options, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAsistencia.setAdapter(adapter);

                // Usar el `tag` para asociar el ID del estudiante con la vista del estudiante
                itemView.setTag(estudianteId);

                // Añadir la vista al contenedor
                estudiantesContainer.addView(itemView);

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No se encontraron estudiantes", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
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

    private void confirmarAsistencia() {
        String fecha = tvSelectedDate.getText().toString().replace("Fecha seleccionada: ", "").trim();

        // Verificar si no se ha seleccionado ninguna fecha
        if (fecha.equals("Fecha no seleccionada") || fecha.isEmpty()) {
            Toast.makeText(this, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalEstudiantes = estudiantesContainer.getChildCount();
        for (int i = 0; i < totalEstudiantes; i++) {
            View estudianteView = estudiantesContainer.getChildAt(i);

            TextView tvEstudianteNombre = estudianteView.findViewById(R.id.tvEstudianteNombre);
            Spinner spinnerAsistencia = estudianteView.findViewById(R.id.spinnerAsistencia);

            String asistenciaSeleccionada = spinnerAsistencia.getSelectedItem().toString();
            int estudianteId = (int) estudianteView.getTag();

            int gradoID = convertirGrado(gradoSeleccionado);
            int cursoID = getCursoID(cursoSeleccionado);

            // Guardar la asistencia en la base de datos
            guardarAsistenciaEnBD(estudianteId, asistenciaSeleccionada, fecha, gradoID, cursoID);
        }

        Toast.makeText(this, "Asistencia guardada correctamente", Toast.LENGTH_SHORT).show();
    }

    private int getCursoID(String curso) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT IdCurso FROM Cursos WHERE NombreCurso = ?", new String[]{curso});
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        return -1; // En caso de que no se encuentre el curso
    }

    private void guardarAsistenciaEnBD(int estudianteId, String asistencia, String fecha, int gradoID, int cursoID) {
        SQLiteDatabase db = basedeDatos.getWritableDatabase();

        // Verificar si ya existe una asistencia registrada para este estudiante en la misma fecha, curso y grado
        String query = "SELECT Id FROM " + BasedeDatos.TABLE_ASISTENCIAS +
                " WHERE EstudianteId = ? AND Fecha = ? AND GradoID = ? AND CursoID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(estudianteId), fecha, String.valueOf(gradoID), String.valueOf(cursoID)});

        ContentValues values = new ContentValues();
        values.put("EstudianteId", estudianteId);
        values.put("Fecha", fecha);
        values.put("Estado", asistencia);
        values.put("GradoID", gradoID);
        values.put("CursoID", cursoID);

        if (cursor.moveToFirst()) {
            // Si ya existe un registro, actualizamos la fila existente
            int asistenciaId = cursor.getInt(0); // Obtener el ID de la asistencia existente
            db.update(BasedeDatos.TABLE_ASISTENCIAS, values, "Id = ?", new String[]{String.valueOf(asistenciaId)});
        } else {
            // Si no existe un registro, insertamos uno nuevo
            db.insert(BasedeDatos.TABLE_ASISTENCIAS, null, values);
        }

        cursor.close(); // Cerrar el cursor después de usarlo
        db.close(); // Cerrar la base de datos después de la operación
    }


    public void RegresarPaginaAnterior(View view) {
        finish();
    }
}

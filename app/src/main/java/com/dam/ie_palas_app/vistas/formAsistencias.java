package com.dam.ie_palas_app.vistas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class formAsistencias extends AppCompatActivity {

    private TextView textViewNombreCurso, textViewAsistencias, textViewTardanzas, textViewFaltas;
    private BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_asistencias);

        // Inicializar los TextView
        textViewNombreCurso = findViewById(R.id.textViewNombreCurso);
        textViewAsistencias = findViewById(R.id.numberAsistencias);
        textViewTardanzas = findViewById(R.id.numberTardanzas);
        textViewFaltas = findViewById(R.id.numberFaltas);

        // Inicializar el gráfico
        GraphView graph = findViewById(R.id.graph);

        // Recibir datos de la actividad anterior
        String curso = getIntent().getStringExtra("curso");
        int estudianteId = getIntent().getIntExtra("estudianteId", -1);

        // Verificar si los datos son válidos y cargar estadísticas
        if (curso != null && estudianteId != -1) {
            Log.d("Debug", "Cargando estadísticas para el curso: " + curso + ", Estudiante ID: " + estudianteId);
            // Mostrar el nombre del curso en el TextView
            textViewNombreCurso.setText("Curso: " + curso);

            // Cargar estadísticas
            cargarEstadisticas(curso, estudianteId, graph);
        } else {
            Log.e("Error", "No se pudieron cargar las estadísticas. Estudiante ID o curso inválidos.");
            textViewAsistencias.setText("0");
            textViewTardanzas.setText("0");
            textViewFaltas.setText("0");
        }
    }

    private void cargarEstadisticas(String curso, int estudianteId, GraphView graph) {
        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);
        SQLiteDatabase db = basedeDatos.getReadableDatabase();

        int totalAsistencias = 0;
        int totalTardanzas = 0;
        int totalFaltas = 0;

        try {
            // Obtener total de asistencias
            Cursor cursorAsistencias = db.rawQuery("SELECT COUNT(*) FROM Asistencias WHERE CursoID = (SELECT IdCurso FROM Cursos WHERE NombreCurso = ?) AND EstudianteId = ? AND Estado = 'Asistió'", new String[]{curso, String.valueOf(estudianteId)});
            if (cursorAsistencias.moveToFirst()) {
                totalAsistencias = cursorAsistencias.getInt(0);
                textViewAsistencias.setText(String.valueOf(totalAsistencias));
            }
            cursorAsistencias.close();

            // Obtener total de tardanzas
            Cursor cursorTardanzas = db.rawQuery("SELECT COUNT(*) FROM Asistencias WHERE CursoID = (SELECT IdCurso FROM Cursos WHERE NombreCurso = ?) AND EstudianteId = ? AND Estado = 'Tarde'", new String[]{curso, String.valueOf(estudianteId)});
            if (cursorTardanzas.moveToFirst()) {
                totalTardanzas = cursorTardanzas.getInt(0);
                textViewTardanzas.setText(String.valueOf(totalTardanzas));
            }
            cursorTardanzas.close();

            // Obtener total de faltas
            Cursor cursorFaltas = db.rawQuery("SELECT COUNT(*) FROM Asistencias WHERE CursoID = (SELECT IdCurso FROM Cursos WHERE NombreCurso = ?) AND EstudianteId = ? AND Estado = 'Faltó'", new String[]{curso, String.valueOf(estudianteId)});
            if (cursorFaltas.moveToFirst()) {
                totalFaltas = cursorFaltas.getInt(0);
                textViewFaltas.setText(String.valueOf(totalFaltas));
            }
            cursorFaltas.close();

            // Configurar el gráfico
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, totalAsistencias),
                    new DataPoint(1, totalTardanzas),
                    new DataPoint(2, totalFaltas)
            });

            graph.addSeries(series);
            series.setSpacing(10); // Espaciado entre las barras
            graph.getViewport().setMinY(0); // Configurar el mínimo valor del eje Y
            graph.getViewport().setMaxY(Math.max(totalAsistencias, Math.max(totalTardanzas, totalFaltas)) + 1); // Configurar el máximo valor del eje Y
            graph.getViewport().setYAxisBoundsManual(true); // Hacer los límites del eje Y manuales
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Estadísticas");
            graph.getGridLabelRenderer().setVerticalAxisTitle("Cantidad");

        } catch (Exception e) {
            e.printStackTrace();
            textViewAsistencias.setText("Error");
            textViewTardanzas.setText("Error");
            textViewFaltas.setText("Error");
        } finally {
            db.close();
        }
    }

    public void Regresar(View view) {
        finish(); // Cierra la actividad actual
    }
}

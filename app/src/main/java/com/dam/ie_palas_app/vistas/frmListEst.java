package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cEstudiante;
import com.dam.ie_palas_app.modelo.Estudiante;

import java.util.ArrayList;

public class frmListEst extends AppCompatActivity {
    private Spinner SelectSpinner;
    private TextView ListarEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_list_est);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            SelectSpinner = findViewById(R.id.spinnerSelectGrado);
            ListarEst = findViewById(R.id.listaEst);

            spinnerCon();

            return insets;
        });

    }
    private void spinnerCon(){
        SelectSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                int gradoSeleccionado = position + 1; // Primero = 1, Segundo = 2, etc.
                listarEstudiantesPorGrado(gradoSeleccionado);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // No se seleccionó nada
            }
        });
    }
    private void listarEstudiantesPorGrado(int grado) {
        cEstudiante controlador = new cEstudiante(this); // Inicializar el controlador solo aquí
        ArrayList<Estudiante> estudiantes = controlador.obtenerEstudiantesPorGrado(grado);

        if (estudiantes.isEmpty()) {
            ListarEst.setText("No hay estudiantes registrados en este grado.");
        } else {
            mostrarEstudiantes(estudiantes);
        }

    }
    private void mostrarEstudiantes(ArrayList<Estudiante> estudiantes) {
        StringBuilder sb = new StringBuilder();
        for (Estudiante estudiante : estudiantes) {
            sb.append("ID: ").append(estudiante.getEstudianteId()).append("\n")
                    .append("Nombre: ").append(estudiante.getNombres()).append(" ").append(estudiante.getApellidos()).append("\n")
                    .append("Usuario: ").append(estudiante.getUsuario()).append("\n")
                    .append("Contraseña: ").append(estudiante.getContrasena()).append("\n")
                    .append("\n");
        }
        ListarEst.setText(sb.toString());
    }
    public void Salir(View view) {
        finish();
    }
}
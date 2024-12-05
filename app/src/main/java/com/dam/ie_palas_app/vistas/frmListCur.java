package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.dam.ie_palas_app.controlador.cCurso;
import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.modelo.Curso;

import java.util.ArrayList;

public class frmListCur extends AppCompatActivity {

    private EditText editTextListCursos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_list_cur);

        editTextListCursos = findViewById(R.id.editTextListCursos);
        listarCursos();
    }
    private void listarCursos() {
        cCurso controlador = new cCurso(this);
        ArrayList<Curso> cursos = controlador.obtenerCursos();

        if (cursos.isEmpty()) {
            editTextListCursos.setText("No hay Cursos registrados.");
        } else {
            mostrarCursos(cursos);
        }
    }

    private void mostrarCursos(ArrayList<Curso> cursos) {
        StringBuilder sb = new StringBuilder();
        for (Curso curso : cursos) {
            sb.append("ID: ").append(curso.getIdCurso()).append("\n")
                    .append("Nombre del Curso: ").append(curso.getNombreCurso()).append("\n\n");
        }
        editTextListCursos.setText(sb.toString());
    }

    public void fmrSalirCursoList(View view) {
        finish();
    }
}
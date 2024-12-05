package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cCurso;
import com.dam.ie_palas_app.modelo.Curso;

import java.util.ArrayList;

public class frmEliCur extends AppCompatActivity {

    private EditText txtidcurso, txtListCurEliminar;
    private Curso cursoEncontrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_eli_cur);

        txtidcurso = findViewById(R.id.txtidcurso);
        txtListCurEliminar = findViewById(R.id.txtListCurEliminar);
    }
    public void buscarCursoEli(View view) {
        String idStr = txtidcurso.getText().toString().trim();

        if (idStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ID del curso", Toast.LENGTH_LONG).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un ID válido", Toast.LENGTH_LONG).show();
            return;
        }

        cCurso controladorCur = new cCurso(this);
        ArrayList<Curso> cursos = controladorCur.obtenerCursos();

        // Busca el curso con el ID especificado
        for (Curso curso : cursos) {
            if (curso.getIdCurso() == id) {
                cursoEncontrado = curso;

                // Muestra los datos del curso en el campo txtListCurEliminar
                String datosCurso = "ID: " + curso.getIdCurso() + "\n"
                        + "Nombre del Curso: " + curso.getNombreCurso();
                txtListCurEliminar.setText(datosCurso);

                Toast.makeText(this, "Curso encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        cursoEncontrado = null; // No encontrado
        txtListCurEliminar.setText(""); // Limpia el campo si no se encuentra
        Toast.makeText(this, "Curso no encontrado", Toast.LENGTH_LONG).show();
    }

    public void eliminarCurso(View view) {
        if (cursoEncontrado == null) {
            Toast.makeText(this, "No se ha encontrado ningún curso para eliminar", Toast.LENGTH_LONG).show();
            return;
        }

        cCurso controladorCur = new cCurso(this);
        int idCurso = cursoEncontrado.getIdCurso();

        // Llama al método deleteCurso del controlador para eliminar el curso por ID
        controladorCur.deleteCurso(idCurso);

        // Verificar si la eliminación fue exitosa
        Toast.makeText(this, "Curso eliminado correctamente", Toast.LENGTH_SHORT).show();
        txtListCurEliminar.setText(""); // Limpia los datos después de eliminar
        cursoEncontrado = null;
    }

    public void fmrSalirEliCur(View view) {
        finish();
    }

}
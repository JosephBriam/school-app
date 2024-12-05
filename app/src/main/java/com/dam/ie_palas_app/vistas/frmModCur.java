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

public class frmModCur extends AppCompatActivity {

    private EditText txtCodigoCurMod, txtNombreCur;
    private Curso cursoEncontrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_mod_cur);

        txtCodigoCurMod = findViewById(R.id.txtCodigoCurMod);
        txtNombreCur = findViewById(R.id.txtNombreCur);
    }

    // Método para buscar un curso por su ID
    public void buscarCurso(View view) {
        String idStr = txtCodigoCurMod.getText().toString().trim();

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

                // Llena los campos de texto con los datos del curso encontrado
                txtNombreCur.setText(curso.getNombreCurso());

                Toast.makeText(this, "Curso encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        cursoEncontrado = null; // No encontrado
        limpiarCampos(); // Limpia los campos si no se encuentra el curso
        Toast.makeText(this, "Curso no encontrado", Toast.LENGTH_LONG).show();
    }

    // Método para modificar los datos del curso
    public void modificarCurso(View view) {
        if (cursoEncontrado != null) {
            String nuevoNombre = txtNombreCur.getText().toString().trim();

            if (nuevoNombre.isEmpty()) {
                Toast.makeText(this, "No se puede modificar. Complete el campo de nombre del curso", Toast.LENGTH_LONG).show();
                return;
            }

            // Actualiza los datos del curso
            cursoEncontrado.setNombreCurso(nuevoNombre);

            cCurso controladorCur = new cCurso(this);
            controladorCur.updateCurso(cursoEncontrado); // Usa el método existente en el controlador

            // Notifica al usuario y limpia los campos
            Toast.makeText(this, "Curso modificado correctamente", Toast.LENGTH_LONG).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Primero busque un curso", Toast.LENGTH_LONG).show();
        }
    }

    // Limpia los campos de entrada y reinicia la variable cursoEncontrado
    private void limpiarCampos() {
        txtCodigoCurMod.setText("");
        txtNombreCur.setText("");
        cursoEncontrado = null;
    }

    public void fmrSalirModCur(View view) {
        finish();
    }
}
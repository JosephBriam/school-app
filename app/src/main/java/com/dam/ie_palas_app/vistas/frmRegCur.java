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

public class frmRegCur extends AppCompatActivity {

    private EditText txtNombreCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_reg_cur);

        txtNombreCurso = findViewById(R.id.txtNombreEst);
    }

    public void registrarCurso(View view) {
        String nombreCurso = txtNombreCurso.getText().toString().trim();

        if (nombreCurso.isEmpty()) {
            Toast.makeText(this, "Por favor, complete el campo de nombre del curso", Toast.LENGTH_LONG).show();
            return;
        }

        Curso curso = new Curso(0, nombreCurso); // El ID será 0 porque se autogenera
        cCurso controladorCurso = new cCurso(this);

        controladorCurso.agregarCurso(curso);
        Toast.makeText(this, "Curso guardado exitosamente.", Toast.LENGTH_SHORT).show();

        // Limpiar el campo después de guardar
        txtNombreCurso.setText("");
    }

    public void fmrSalir(View view) {
        finish();
    }
}
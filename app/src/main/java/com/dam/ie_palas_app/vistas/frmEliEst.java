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
import com.dam.ie_palas_app.controlador.cEstudiante;
import com.dam.ie_palas_app.modelo.Estudiante;

import java.util.ArrayList;

public class frmEliEst extends AppCompatActivity {

    private EditText txtCodigoest, txtListaest;
    private Estudiante estudianteEncontrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_eli_est);

        txtCodigoest=findViewById(R.id.txtcodest);
        txtListaest=findViewById(R.id.txtlistaest);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void buscarEstEli(View view) {
        String idStr = txtCodigoest.getText().toString().trim();

        if (idStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ID del estudiante", Toast.LENGTH_LONG).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un ID válido", Toast.LENGTH_LONG).show();
            return;
        }

        cEstudiante controladorEst = new cEstudiante(this);
        ArrayList<Estudiante> estudiantes = controladorEst.obtenerEstudiante();

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getEstudianteId() == id) {
                estudianteEncontrado= estudiante;

                String datosDocente = "ID: " + estudiante.getEstudianteId() + "\n"
                        + "Nombre: " + estudiante.getNombres() + "\n"
                        + "Apellido: " + estudiante.getApellidos() + "\n"
                        + "Usuario: " + estudiante.getUsuario();
                txtListaest.setText(datosDocente);

                Toast.makeText(this, "Estudiante encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        estudianteEncontrado = null;
        txtListaest.setText("");
        Toast.makeText(this, "Estudiante no encontrado", Toast.LENGTH_LONG).show();
    }


    public void eliminarEst(View view) {
        if (estudianteEncontrado== null) {
            Toast.makeText(this, "No se ha encontrado ningún estudiante para eliminar", Toast.LENGTH_LONG).show();
            return;
        }

        cEstudiante controladorEst = new cEstudiante(this);
        int idEst = estudianteEncontrado.getEstudianteId();

        // Llama al método deleteEstudiante del controlador para eliminar al estudiante por ID
        controladorEst.deleteEstudiantes(idEst);

        // Verificar si la eliminación fue exitosa
        Toast.makeText(this, "Estudiante eliminado correctamente", Toast.LENGTH_SHORT).show();
        txtListaest.setText(""); // Limpia los datos después de eliminar
        estudianteEncontrado = null;
    }

    public void fmrSalirModEst(View view) {

        finish();
    }

}
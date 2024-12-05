package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cApoderado;
import com.dam.ie_palas_app.modelo.Apoderado;

import java.util.List;

public class frmListApo extends AppCompatActivity {

    private EditText editText;
    private cApoderado controladorApoderado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_list_apo);

        editText = findViewById(R.id.editTextTextMultiLine2);
        controladorApoderado = new cApoderado(this);

        listarApoderados();
    }

    private void listarApoderados() {
        List<Apoderado> apoderados = controladorApoderado.obtenerTodos();

        if (apoderados.isEmpty()) {
            Toast.makeText(this, "No se encontraron apoderados", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder apoderadosText = new StringBuilder();
            for (Apoderado apoderado : apoderados) {
                apoderadosText.append("Nombre: ").append(apoderado.getNombres()).append("\n");
                apoderadosText.append("Apellidos: ").append(apoderado.getApellidos()).append("\n");
                apoderadosText.append("Usuario: ").append(apoderado.getUsuario()).append("\n\n");
            }
            editText.setText(apoderadosText.toString());
        }
    }
    public void regresar(View view) {
        finish();
    }
}

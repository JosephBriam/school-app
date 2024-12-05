package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cApoderado;
import com.dam.ie_palas_app.modelo.Apoderado;

public class frmEliApo extends AppCompatActivity {

    private EditText codigoDocEditText;
    private EditText listadoDocEliminarEditText;
    private Button buscarButton;
    private Button confirmarEliminacionButton;
    private Button regresarButton;
    private cApoderado controladorApoderado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_eli_apo);

        codigoDocEditText = findViewById(R.id.txtcodest);
        listadoDocEliminarEditText = findViewById(R.id.txtlistaest);
        buscarButton = findViewById(R.id.button11);
        confirmarEliminacionButton = findViewById(R.id.button12);
        regresarButton = findViewById(R.id.buttonRegresar);
        controladorApoderado = new cApoderado(this);

        buscarButton.setOnClickListener(v -> buscarApoderado());
        confirmarEliminacionButton.setOnClickListener(v -> confirmarEliminacion());
        regresarButton.setOnClickListener(v -> finish());
    }

    private void buscarApoderado() {
        String codigo = codigoDocEditText.getText().toString().trim();

        if (codigo.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese el código del apoderado", Toast.LENGTH_SHORT).show();
            return;
        }

        Apoderado apoderado = controladorApoderado.obtenerPorId(Integer.parseInt(codigo));

        if (apoderado == null) {
            Toast.makeText(this, "Apoderado no encontrado", Toast.LENGTH_SHORT).show();
        } else {
            listadoDocEliminarEditText.setText("Nombre: " + apoderado.getNombres() + "\n"
                    + "Apellidos: " + apoderado.getApellidos() + "\n"
                    + "Usuario: " + apoderado.getUsuario());
        }
    }

    private void confirmarEliminacion() {
        String codigo = codigoDocEditText.getText().toString().trim();

        if (codigo.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese el código del apoderado", Toast.LENGTH_SHORT).show();
            return;
        }

        int resultado = controladorApoderado.eliminarApoderado(Integer.parseInt(codigo));

        if (resultado > 0) {
            Toast.makeText(this, "Apoderado eliminado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al eliminar apoderado", Toast.LENGTH_SHORT).show();
        }
    }
    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}

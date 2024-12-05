package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cApoderado;

public class frmRegApo extends AppCompatActivity {

    private cApoderado controladorApoderado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_reg_apo);

        controladorApoderado = new cApoderado(this);
    }

    public void guardarApoderado(View view) {
        EditText txtNombre = findViewById(R.id.txtNombreEst);
        EditText txtApellido = findViewById(R.id.txtApellidoEst);
        EditText txtUsuario = findViewById(R.id.txtUsuarioEst);
        EditText txtContrasena = findViewById(R.id.txtContrasenaEst);

        String nombres = txtNombre.getText().toString();
        String apellidos = txtApellido.getText().toString();
        String usuario = txtUsuario.getText().toString();
        String contrasena = txtContrasena.getText().toString();

        if (nombres.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = controladorApoderado.crearApoderado(nombres, apellidos, usuario, contrasena);

        if (id != -1) {
            Toast.makeText(this, "Apoderado registrado con éxito", Toast.LENGTH_SHORT).show();
            limpiarCampos();  // Limpiar los campos después de la inserción
        } else {
            Toast.makeText(this, "Error al registrar al apoderado", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        EditText txtNombre = findViewById(R.id.txtNombreEst);
        EditText txtApellido = findViewById(R.id.txtApellidoEst);
        EditText txtUsuario = findViewById(R.id.txtUsuarioEst);
        EditText txtContrasena = findViewById(R.id.txtContrasenaEst);

        txtNombre.setText("");
        txtApellido.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
    }
    public void Regresar(View view) {
        finish();
    }
}

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

public class frmModApo extends AppCompatActivity {

    private cApoderado controlador;
    private EditText txtCodigoDocMod, txtNombreDoc, txtApellidoDoc, txtUsuarioDoc, txtContrasenaDoc;
    private Button btnBuscar, btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_mod_apo);

        txtCodigoDocMod = findViewById(R.id.txtcodest);
        txtNombreDoc = findViewById(R.id.txtNombreEst);
        txtApellidoDoc = findViewById(R.id.txtApellidoEst);
        txtUsuarioDoc = findViewById(R.id.txtUsuarioEst);
        txtContrasenaDoc = findViewById(R.id.txtContrasenaEst);
        btnBuscar = findViewById(R.id.button13);
        btnModificar = findViewById(R.id.button10);

        controlador = new cApoderado(this);

        btnBuscar.setOnClickListener(v -> buscarApoderado());

        btnModificar.setOnClickListener(v -> modificarApoderado());
    }

    private void buscarApoderado() {
        String codigo = txtCodigoDocMod.getText().toString();

        if (codigo.isEmpty()) {
            Toast.makeText(this, "Ingrese un código de apoderado", Toast.LENGTH_SHORT).show();
            return;
        }

        Apoderado apoderado = controlador.obtenerPorId(Integer.parseInt(codigo));
        if (apoderado != null) {
            txtNombreDoc.setText(apoderado.getNombres());
            txtApellidoDoc.setText(apoderado.getApellidos());
            txtUsuarioDoc.setText(apoderado.getUsuario());
            txtContrasenaDoc.setText(apoderado.getContraseña());
        } else {
            Toast.makeText(this, "Apoderado no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarApoderado() {
        String codigo = txtCodigoDocMod.getText().toString();
        String nombres = txtNombreDoc.getText().toString();
        String apellidos = txtApellidoDoc.getText().toString();
        String usuario = txtUsuarioDoc.getText().toString();
        String contrasena = txtContrasenaDoc.getText().toString();

        if (codigo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Apoderado apoderado = new Apoderado(Integer.parseInt(codigo), nombres, apellidos, usuario, contrasena);

        int resultado = controlador.actualizarApoderado(apoderado.getIdPadre(), apoderado.getNombres(), apoderado.getApellidos(), apoderado.getUsuario(), apoderado.getContraseña());

        if (resultado > 0) {
            Toast.makeText(this, "Apoderado actualizado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar apoderado", Toast.LENGTH_SHORT).show();
        }
    }
    public void regresar(View view) {
        finish();
    }
}

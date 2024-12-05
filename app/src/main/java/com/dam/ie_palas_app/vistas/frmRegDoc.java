package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cDocente;
import com.dam.ie_palas_app.modelo.Docente;

public class frmRegDoc extends AppCompatActivity {

    private EditText txtNombreDoc, txtApellidoDoc, txtUsuarioDoc, txtContrasenaDoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_reg_doc);

        txtNombreDoc = findViewById(R.id.txtNombreEst);
        txtApellidoDoc = findViewById(R.id.txtApellidoEst);
        txtUsuarioDoc = findViewById(R.id.txtUsuarioEst);
        txtContrasenaDoc = findViewById(R.id.txtContrasenaEst);
    }

    public void registrarDocente(View view){
        String nombreDocente = txtNombreDoc.getText().toString().trim();
        String apellidoDocente = txtApellidoDoc.getText().toString().trim();
        String usuarioDocente = txtUsuarioDoc.getText().toString().trim();
        String contrasenaDocente = txtContrasenaDoc.getText().toString().trim();

        if(nombreDocente.isEmpty()|| apellidoDocente.isEmpty() || usuarioDocente.isEmpty() || contrasenaDocente.isEmpty() ){
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
            return;
        }

        Docente docente =new Docente(nombreDocente,apellidoDocente,usuarioDocente,contrasenaDocente);
        cDocente controladorDoc = new cDocente(this);

        controladorDoc.agregarDocente(docente);
        Toast.makeText(this, "Docente guardado exitosamente.", Toast.LENGTH_SHORT).show();

        // Limpia los campos después de guardar
        txtNombreDoc.setText("");
        txtApellidoDoc.setText("");
        txtUsuarioDoc.setText("");
        txtContrasenaDoc.setText("");

    }
    public void fmrSalir(View view) {
        finish();
    }
}
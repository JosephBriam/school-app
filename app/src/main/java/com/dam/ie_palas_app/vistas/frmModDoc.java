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

import java.util.ArrayList;

public class frmModDoc extends AppCompatActivity {

    private  EditText txtCodigoDocMod,txtNombreDoc, txtApellidoDoc, txtUsuarioDoc, txtContrasenaDoc;
    private Docente docenteEncontrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_mod_doc);

        txtCodigoDocMod = findViewById(R.id.txtcodest);
        txtNombreDoc = findViewById(R.id.txtNombreEst);
        txtApellidoDoc = findViewById(R.id.txtApellidoEst);
        txtUsuarioDoc = findViewById(R.id.txtUsuarioEst);
        txtContrasenaDoc = findViewById(R.id.txtContrasenaEst);
    }

    // Método para buscar un docente por su ID
    public void buscarDocente(View view) {
        String idStr = txtCodigoDocMod.getText().toString().trim();

        if (idStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ID del docente", Toast.LENGTH_LONG).show();
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un ID válido", Toast.LENGTH_LONG).show();
            return;
        }

        cDocente controladorDoc = new cDocente(this);
        ArrayList<Docente> docentes = controladorDoc.obtenerDocentes();

        // Busca el docente con el ID especificado
        for (Docente docente : docentes) {
            if (docente.getIdDocente() == id) {
                docenteEncontrado = docente;

                // Llena los campos de texto con los datos del docente encontrado
                txtNombreDoc.setText(docente.getNombres());
                txtApellidoDoc.setText(docente.getApellidos());
                txtUsuarioDoc.setText(docente.getUsuario());
                txtContrasenaDoc.setText(docente.getContraseña());

                Toast.makeText(this, "Docente encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        docenteEncontrado = null; // No encontrado
        limpiarCampos(); // Limpia los campos si no se encuentra el docente
        Toast.makeText(this, "Docente no encontrado", Toast.LENGTH_LONG).show();
    }

    // Método para modificar los datos de un docente
    public void modificarDocente(View view) {
        if (docenteEncontrado != null) {
            String nuevoNombre = txtNombreDoc.getText().toString().trim();
            String nuevoApellido = txtApellidoDoc.getText().toString().trim();
            String nuevoUsuario = txtUsuarioDoc.getText().toString().trim();
            String nuevaContrasena = txtContrasenaDoc.getText().toString().trim();

            if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty()) {
                Toast.makeText(this, "No se puede modificar. Complete todos los campos", Toast.LENGTH_LONG).show();
                return;
            }

            // Actualiza los datos del docente
            docenteEncontrado.setNombres(nuevoNombre);
            docenteEncontrado.setApellidos(nuevoApellido);
            docenteEncontrado.setUsuario(nuevoUsuario);
            docenteEncontrado.setContraseña(nuevaContrasena);

            cDocente controladorDoc = new cDocente(this);
            controladorDoc.updateDocente(docenteEncontrado); // Usa el método existente en el controlador

            // Notifica al usuario y limpia los campos
            Toast.makeText(this, "Docente modificado correctamente", Toast.LENGTH_LONG).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Primero busque un docente", Toast.LENGTH_LONG).show();
        }
    }

    // Limpia los campos de entrada y reinicia la variable docenteEncontrado
    private void limpiarCampos() {
        txtCodigoDocMod.setText("");
        txtNombreDoc.setText("");
        txtApellidoDoc.setText("");
        txtUsuarioDoc.setText("");
        txtContrasenaDoc.setText("");
        docenteEncontrado = null;
    }

    public void fmrSalirModDoc(View view) {
        finish();
    }
}
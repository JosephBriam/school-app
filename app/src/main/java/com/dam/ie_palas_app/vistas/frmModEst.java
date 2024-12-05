package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class frmModEst extends AppCompatActivity {


    private EditText nombres, apellidos, usuario, contrasena, codestudiante;
    private Estudiante founEstudiante;
    private Spinner txtSpinnerGrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_mod_est);

        codestudiante=findViewById(R.id.txtcodest);
        nombres=findViewById(R.id.txtNombreEst);
        apellidos=findViewById(R.id.txtApellidoEst);
        usuario=findViewById(R.id.txtUsuarioEst);
        contrasena=findViewById(R.id.txtContrasenaEst);
        txtSpinnerGrado=findViewById(R.id.spinnerGrado);

        String[] grados = {"Seleccione un grado", "Primero", "Segundo", "Tercero", "Cuarto", "Quinto","Sexto"};

        // Crear un ArrayAdapter con los grados y asignarlo al Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, grados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtSpinnerGrado.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    private void limpiarCampos() {
        codestudiante.setText("");
        nombres.setText("");
        apellidos.setText("");
        usuario.setText("");
        contrasena.setText("");
        founEstudiante = null;
    }

    public void Buscarestudiante(View view){
        String idEst = codestudiante.getText().toString().trim();

        if (idEst.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ID del estudiante", Toast.LENGTH_LONG).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idEst);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El ID es válido, por favor colocar uno existente", Toast.LENGTH_LONG).show();
            return;
        }


        cEstudiante controladorEst = new cEstudiante(this);
        ArrayList<Estudiante> estudiantes = controladorEst.obtenerEstudiante();

        // Busca el estudiante con el ID especificado
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getEstudianteId() == id) {
                founEstudiante = estudiante;

                // Llena los campos de texto con los datos del docente encontrado
                nombres.setText(estudiante.getNombres());
                apellidos.setText(estudiante.getApellidos());
                usuario.setText(estudiante.getUsuario());
                contrasena.setText(estudiante.getContrasena());

                if (estudiante.getGrado() != 0) { // Verificamos que el grado sea válido (mayor que 0)
                    txtSpinnerGrado.setSelection(estudiante.getGrado()); // Esto selecciona el grado en el Spinner
                }


                Toast.makeText(this, "Estudiante encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        founEstudiante = null;
        limpiarCampos(); // Limpia los campos si no se encuentra el estudiante
        Toast.makeText(this, "Estudiante no encontrado", Toast.LENGTH_LONG).show();
    }

    public void modificarestudiante(View view){
        if (founEstudiante != null) {
            String nuevoNombre = nombres.getText().toString().trim();
            String nuevoApellido = apellidos.getText().toString().trim();
            String nuevoUsuario = usuario.getText().toString().trim();
            String nuevaContrasena = contrasena.getText().toString().trim();


            int gradoEst = txtSpinnerGrado.getSelectedItemPosition(); // 0 será "Seleccione un grado", por lo tanto no se debe tomar en cuenta
            if (gradoEst == 0) {
                Toast.makeText(this, "Seleccione un grado válido", Toast.LENGTH_LONG).show();
                return;
            }

            gradoEst = gradoEst;

            if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty() )  {
                Toast.makeText(this, "No se puede modificar. Complete todos los campos", Toast.LENGTH_LONG).show();
                return;
            }

            // Actualiza los datos del estudiante
            founEstudiante.setNombres(nuevoNombre);
            founEstudiante.setApellidos(nuevoApellido);
            founEstudiante.setUsuario(nuevoUsuario);
            founEstudiante.setContrasena(nuevaContrasena);
            founEstudiante.setGrado(gradoEst);

            cEstudiante controladorEst = new cEstudiante(this);
            controladorEst.updateEstudiante(founEstudiante); // Usa el método existente en el controlador

            // Notifica al usuario y limpia los campos
            Toast.makeText(this, "Estudiante modificado correctamente", Toast.LENGTH_LONG).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Primero busque un estudiante", Toast.LENGTH_LONG).show();
        }
    }

    public void fmrSalirModEst(View view) {

        finish();
    }
}
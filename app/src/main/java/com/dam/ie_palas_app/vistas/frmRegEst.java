package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
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


public class frmRegEst extends AppCompatActivity {

    private EditText txtNombreEst, txtApellidoEst, txtUsuarioEst, txtContrasenaEst;
    private Spinner txtSpinnerGrado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_reg_est);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            txtNombreEst=findViewById(R.id.txtNombreEst);
            txtApellidoEst=findViewById(R.id.txtApellidoEst);
            txtUsuarioEst=findViewById(R.id.txtUsuarioEst);
            txtContrasenaEst=findViewById(R.id.txtContrasenaEst);
            txtSpinnerGrado = findViewById(R.id.spinnerGrado);

            return insets;

        });
    }
    public void RegistrarEstudiante(View view){
        String nombreEst = txtNombreEst.getText().toString().trim();
        String apellidoEst = txtApellidoEst.getText().toString().trim();
        String usuarioEst = txtUsuarioEst.getText().toString().trim();
        String contrasenaEst = txtContrasenaEst.getText().toString().trim();
        int gradoEst = txtSpinnerGrado.getSelectedItemPosition() + 1;//Guardar en numero dependiendo de la posicion seleccionada

        if(nombreEst.isEmpty()|| apellidoEst.isEmpty() || usuarioEst.isEmpty() || contrasenaEst.isEmpty() ){
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
            return;
        }
        Estudiante estudiante =new Estudiante(nombreEst,apellidoEst,usuarioEst,contrasenaEst,gradoEst);

        cEstudiante estudianteCon = new cEstudiante(this);

        estudianteCon.agregarEstudiante(estudiante);
        Toast.makeText(this, "Estudiante guardado exitosamente.", Toast.LENGTH_SHORT).show();

        // Limpia los campos después de guardar
        txtNombreEst.setText("");
        txtApellidoEst.setText("");
        txtUsuarioEst.setText("");
        txtContrasenaEst.setText("");
    }
    public void Salir(View view) {
        finish();
    }

}
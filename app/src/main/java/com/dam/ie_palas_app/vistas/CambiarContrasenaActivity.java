package com.dam.ie_palas_app.vistas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

// Importa la clase de tu base de datos SQLite


public class CambiarContrasenaActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etNuevaContrasena;
    private Button btnCambiarContrasena;
    private TextView tvMensaje;

    // Variable para la base de datos
    private BasedeDatos basedeDatos;

    // Bandera para determinar el tipo de usuario
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        etUsuario = findViewById(R.id.txtusuariocont);
        etNuevaContrasena = findViewById(R.id.txtnuevacontraseña);
        btnCambiarContrasena = findViewById(R.id.btnnuevacontraseña);
        tvMensaje = findViewById(R.id.tvMensaje);

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        // Obtener el tipo de usuario del Intent
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        btnCambiarContrasena.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String nuevaContrasena = etNuevaContrasena.getText().toString();

            if (usuario.isEmpty() || nuevaContrasena.isEmpty()) {
                Toast.makeText(CambiarContrasenaActivity.this, "Por favor, ingresa usuario y nueva contraseña.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isAdmin) {
                // Cambiar la contraseña del administrador
                if (actualizarContraseñaDocente(usuario, nuevaContrasena)) {
                    tvMensaje.setText("Contraseña de administrador cambiada exitosamente.");
                    Toast.makeText(CambiarContrasenaActivity.this, "Contraseña de administrador cambiada exitosamente.", Toast.LENGTH_SHORT).show();
                } else {
                    tvMensaje.setText("Usuario de administrador no encontrado.");
                    Toast.makeText(CambiarContrasenaActivity.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Cambiar la contraseña del estudiante
                if (actualizarContraseñaEstudiante(usuario, nuevaContrasena)) {
                    tvMensaje.setText("Contraseña de estudiante cambiada exitosamente.");
                    Toast.makeText(CambiarContrasenaActivity.this, "Contraseña de estudiante cambiada exitosamente.", Toast.LENGTH_SHORT).show();
                } else {
                    tvMensaje.setText("Usuario de estudiante no encontrado.");
                    Toast.makeText(CambiarContrasenaActivity.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para actualizar la contraseña de un docente (administrador)
    private boolean actualizarContraseñaDocente(String usuario, String nuevaContraseña) {
        SQLiteDatabase db = basedeDatos.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Contraseña", nuevaContraseña);

        // Actualizar la contraseña para el usuario especificado
        int filasAfectadas = db.update(BasedeDatos.TABLE_DOCENTE, values, "Usuario = ?", new String[]{usuario});

        db.close();
        return filasAfectadas > 0;
    }

    // Método para actualizar la contraseña de un estudiante
    private boolean actualizarContraseñaEstudiante(String usuario, String nuevaContraseña) {
        SQLiteDatabase db = basedeDatos.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Contraseña", nuevaContraseña);

        // Actualizar la contraseña para el estudiante con el usuario especificado
        int filasAfectadas = db.update(BasedeDatos.TABLE_ESTUDIANTES, values, "Usuario = ?", new String[]{usuario});

        db.close();
        return filasAfectadas > 0;
    }

    public void RegresarPagina(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }

}

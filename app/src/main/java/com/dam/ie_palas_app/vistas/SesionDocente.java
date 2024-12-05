package com.dam.ie_palas_app.vistas;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

public class SesionDocente extends AppCompatActivity {

    // Definir variables para los campos de texto
    private EditText txtUsuario;
    private EditText txtContraseña;
    private Button btnInicioSesion;
    private TextView txtOlvidasteContrasena;

    // Base de datos
    private BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Conectar las vistas con el código Java
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtContraseña);
        btnInicioSesion = findViewById(R.id.btnInicioSesionAdmin);
        txtOlvidasteContrasena = findViewById(R.id.txtolvidecontraseña);

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        // Listener para el botón "Iniciar Sesión"
        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el texto de los campos de usuario y contraseña
                String usuarioIngresado = txtUsuario.getText().toString();
                String contrasenaIngresada = txtContraseña.getText().toString();

                // Validar las credenciales desde la base de datos
                if (validarCredenciales(usuarioIngresado, contrasenaIngresada)) {
                    // Mostrar un mensaje de éxito
                    Toast.makeText(SesionDocente.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    // Aquí puedes ocultar los campos de usuario y contraseña si es necesario
                    txtUsuario.setVisibility(View.GONE);
                    txtContraseña.setVisibility(View.GONE);
                    btnInicioSesion.setVisibility(View.GONE);
                    txtOlvidasteContrasena.setVisibility(View.GONE);

                    // Navegar a otra actividad (la plataforma principal)
                    Intent intent = new Intent(SesionDocente.this, PlataformaDocenteActivity.class);
                    startActivity(intent);

                } else {
                    // Mostrar un mensaje de error si las credenciales no coinciden
                    Toast.makeText(SesionDocente.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtOlvidasteContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SesionDocente.this, CambiarContrasenaActivity.class);
                intent.putExtra("isAdmin", true); // Indica que es un administrador
                startActivity(intent);
            }
        });

    }

    // Método para validar las credenciales del docente en la base de datos
    private boolean validarCredenciales(String usuario, String contrasena) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        String query = "SELECT * FROM " + BasedeDatos.TABLE_DOCENTE + " WHERE Usuario = ? AND Contraseña = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuario, contrasena});

        boolean resultado = cursor.moveToFirst(); // Si se encuentra una fila, significa que el usuario existe con esa contraseña
        cursor.close();
        return resultado;
    }

    public void RegresarPaginaAnterior(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }

}

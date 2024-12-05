package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

public class SesionPadreActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContraseña;
    private Button btnIniciarSesion;
    private TextView tvOlvidasteContraseña;

    // Variable para acceder a la base de datos
    private BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sesion_padre); // Este es el layout para los padres

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        etUsuario = findViewById(R.id.txtusuarioPadre);
        etContraseña = findViewById(R.id.txtcontraseñaPadre);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesionPadre);
        tvOlvidasteContraseña = findViewById(R.id.txtOlvidasteContraseñaPadre);

        btnIniciarSesion.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String contraseña = etContraseña.getText().toString();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(SesionPadreActivity.this, "Por favor, ingresa usuario y contraseña.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar inicio de sesión con la base de datos
            validarInicioSesionPadre(usuario, contraseña);
        });

        tvOlvidasteContraseña.setOnClickListener(v -> {
            Intent intent = new Intent(SesionPadreActivity.this, CambiarContrasenaActivity.class);
            intent.putExtra("isAdmin", false); // Indica que es un padre/madre
            startActivity(intent);
        });
    }

    public void validarInicioSesionPadre(String usuario, String contraseña) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        String query = "SELECT IdPadre FROM " + BasedeDatos.TABLE_PADRES + " WHERE Usuario = ? AND Contraseña = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuario, contraseña});

        if (cursor.moveToFirst()) {
            int padreId = cursor.getInt(0); // Obtener el ID del padre

            Log.d("Debug", "Padre ID desde la base de datos: " + padreId);

            // Pasar el ID del padre a la siguiente actividad
            Intent intent = new Intent(SesionPadreActivity.this, PlataformaPadreActivity.class);
            intent.putExtra("usuario", usuario);
            intent.putExtra("padreId", padreId); // Pasar el ID del padre
            startActivity(intent);
            finish(); // Cerrar la actividad actual
        } else {
            Toast.makeText(SesionPadreActivity.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }

    public void RegresarPaginaAnterior(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}

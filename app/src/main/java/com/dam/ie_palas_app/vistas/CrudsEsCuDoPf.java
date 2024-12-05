package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;

public class CrudsEsCuDoPf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cruds_es_cu_do_pf);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void dirCrudEstudiantes(View view) {
        Intent intent = new Intent(CrudsEsCuDoPf.this, CrudEstudiantes.class);
        startActivity(intent);
    }

    public void dirCrudCursos(View view) {
        Intent intent = new Intent(CrudsEsCuDoPf.this, CrudCursos.class);
        startActivity(intent);
    }

    public void dirCrudApoderados(View view) {
        Intent intent = new Intent(CrudsEsCuDoPf.this, CrudApoderados.class);
        startActivity(intent);
    }

    public void dirCrudDocentes(View view) {
        Intent intent = new Intent(CrudsEsCuDoPf.this, CrudDocentes.class);
        startActivity(intent);
    }

    // Method to open YouTube link
    public void openYouTube(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@i.e.p.palasatenea9839?si=Muuww66EBkZ1IPlm"));
        startActivity(intent);
    }

    // Method to open Instagram link
    public void openInstagram(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/iep_palas_atenea?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw=="));
        startActivity(intent);
    }

    // Method to open Facebook link
    public void openFacebook(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/PalasAteneaOficial"));
        startActivity(intent);
    }
    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}
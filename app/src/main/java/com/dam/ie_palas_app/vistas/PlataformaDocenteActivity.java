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

public class PlataformaDocenteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plataforma);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void VerCursos(View view) {
        Intent x = new Intent(this, Select_Cursos_Grado_Asistencia_Admin.class);
        startActivity(x);
    }

    public void VerAnuncios(View view) {
        Intent x = new Intent(this, AnuncioE.class);
        startActivity(x);
    }

    public void CrearAnuncios(View view) {
        Intent x = new Intent(this, CrearAnuncio_admin.class);
        startActivity(x);
    }

    public void InformeAsistencia(View view) {
        Intent x = new Intent(this, InformeAsistencia_admin.class);
        startActivity(x);
    }

    // Method to go back to the previous page
    public void RegresarPaginaAnterior(View view) {
        finish();  // This will close the current activity and return to the previous one.
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
}
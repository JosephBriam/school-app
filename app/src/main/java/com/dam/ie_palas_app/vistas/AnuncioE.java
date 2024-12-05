package com.dam.ie_palas_app.vistas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;


public class AnuncioE extends AppCompatActivity {

    private BasedeDatos basedeDatos;
    private ListView listViewAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_e);  // Este es el nuevo layout que vamos a ajustar

        listViewAnuncios = findViewById(R.id.listViewAnuncios);
        basedeDatos = new BasedeDatos(this);

        mostrarAnuncios();
    }

    private void mostrarAnuncios() {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();

        // Consulta para obtener todos los anuncios
        Cursor cursor = db.rawQuery("SELECT IdAnuncio as _id, Titulo, Descripcion FROM Anuncio", null);

        // Adaptador para llenar el ListView
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"Titulo", "Descripcion"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );

        listViewAnuncios.setAdapter(adapter);
    }

    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }

}

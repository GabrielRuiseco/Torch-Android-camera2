package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> listaPermisos;
    Switch s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle nbundle = getIntent().getExtras();
        listaPermisos = nbundle.getStringArrayList("permisos");

        recyclerView = findViewById(R.id.rv1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(listaPermisos, Main2Activity.this);
        recyclerView.setAdapter(myAdapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            for (int x = 0; x < permissions.length; x++) {
                if (grantResults.length > 0 && grantResults[x] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permiso consedido", Toast.LENGTH_SHORT).show();
                    if (listaPermisos.contains(permissions[0])){
                        listaPermisos.remove(permissions[0]);
                    }
                    MyAdapter ap=new MyAdapter(listaPermisos,this);
                    recyclerView.setAdapter(ap);
                    if(listaPermisos.size()==0){
                        Intent ipp = new Intent(this, MainActivity.class);
                        startActivity(ipp);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Permiso no consedido", Toast.LENGTH_SHORT).show();
                    s1=recyclerView.findViewById(R.id.sw1);
                    s1.setChecked(false);
                }
            }
        }
    }
}

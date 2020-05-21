package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Boolean f;
    Button bt1, bt2;
    String[] listaPermisos = new String[]{Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = findViewById(R.id.btn1);
        bt2 = findViewById(R.id.btn2);

        verificarPermisos(listaPermisos);

    }

    public void verificarPermisos(String[] permisos) {
        ArrayList<String> permisosNegados = new ArrayList<String>();

        for (String permiso : permisos) {
            if (ContextCompat.checkSelfPermission(getBaseContext(), permiso) != PackageManager.PERMISSION_GRANTED) {
                permisosNegados.add(permiso);
            }
        }

        if (permisosNegados.size() >= 1) {
            Intent ipp = new Intent(MainActivity.this, Main2Activity.class);
            ipp.putExtra("permisos", permisosNegados);
            startActivity(ipp);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    assert cameraManager != null;
                    String cameraId=cameraManager.getCameraIdList()[0];
                    CameraCharacteristics chars = cameraManager.getCameraCharacteristics(cameraId);
                    f = chars.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    if (f != null) {
                        cameraManager.setTorchMode(cameraId, true);
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    assert cameraManager != null;
                    String cameraId=cameraManager.getCameraIdList()[0];
                    CameraCharacteristics chars = cameraManager.getCameraCharacteristics(cameraId);
                    f = chars.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    if (f != null) {
                        cameraManager.setTorchMode(cameraId, false);
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

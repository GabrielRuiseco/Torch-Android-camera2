package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<String> data1;
    private Context context;
    private View.OnClickListener listener;

    public MyAdapter(ArrayList<String> data1, Context context) {
        this.data1 = data1;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.new_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sw1.setText(data1.get(position).replace("android.permission.", ""));
        holder.permiso = data1.get(position);
        holder.pos=position;
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        String permiso;
        Switch sw1;
        int pos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sw1 = itemView.findViewById(R.id.sw1);
            sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (ContextCompat.checkSelfPermission((Activity) context, permiso) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText((Activity) context, "Este permiso ya fue consedido", Toast.LENGTH_SHORT).show();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permiso)) {
                            new AlertDialog.Builder((Activity) context)
                                    .setTitle("Permiso Necesario")
                                    .setMessage("Necesitas este permiso para usar " + permiso)
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ActivityCompat.requestPermissions((Activity) context, new String[]{permiso}, 1);
                                        }
                                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    sw1.setChecked(false);
                                }
                            }).create().show();
                        } else {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permiso}, 1);
                        }
                    }
                }
            });
        }
    }
}

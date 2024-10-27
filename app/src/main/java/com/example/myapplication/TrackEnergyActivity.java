package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackEnergyActivity extends AppCompatActivity {

    private EditText editElectricity, editGas, editWater;
    private Button saveButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_energy);

        editElectricity = findViewById(R.id.editElectricity);
        editGas = findViewById(R.id.editGas);
        editWater = findViewById(R.id.editWater);
        saveButton = findViewById(R.id.saveButton);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("EnergyUsage");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEnergyData();
            }
        });
    }

    private void saveEnergyData() {
        String electricity = editElectricity.getText().toString();
        String gas = editGas.getText().toString();
        String water = editWater.getText().toString();

        if (electricity.isEmpty() || gas.isEmpty() || water.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();
        EnergyData energyData = new EnergyData(electricity, gas, water);
        if (id != null) {
            databaseReference.child(id).setValue(energyData);
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            clearFields();

            Intent intent = new Intent(TrackEnergyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void clearFields() {
        editElectricity.setText("");
        editGas.setText("");
        editWater.setText("");
    }
}

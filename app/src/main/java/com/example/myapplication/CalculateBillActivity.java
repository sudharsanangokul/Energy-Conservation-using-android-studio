package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class CalculateBillActivity extends AppCompatActivity {

    private EditText editElectricityUsage, editGasUsage, editWaterUsage;
    private TextView billAmountText;
    private Button calculateButton, saveBillButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_bill);

        editElectricityUsage = findViewById(R.id.editElectricityUsage);
        editGasUsage = findViewById(R.id.editGasUsage);
        editWaterUsage = findViewById(R.id.editWaterUsage);
        billAmountText = findViewById(R.id.billAmountText);
        calculateButton = findViewById(R.id.calculateButton);
        saveBillButton = findViewById(R.id.saveBillButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("EnergyUsage");

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBill();
            }
        });

        saveBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBill();
            }
        });
    }

    private void calculateBill() {
        double electricity = parseDouble(editElectricityUsage);
        double gas = parseDouble(editGasUsage);
        double water = parseDouble(editWaterUsage);

        double totalBill = (electricity * 0.15) + (gas * 0.09) + (water * 0.02);
        billAmountText.setText("Total Bill: $" + String.format("%.2f", totalBill));
    }

    private void saveBill() {
        double electricity = parseDouble(editElectricityUsage);
        double gas = parseDouble(editGasUsage);
        double water = parseDouble(editWaterUsage);
        double totalBill = (electricity * 0.15) + (gas * 0.09) + (water * 0.02);

        // Prepare data with electricity, gas, water, and totalBill
        Map<String, Object> billData = new HashMap<>();
        billData.put("electricity", String.format("%.2f", electricity));
        billData.put("gas", String.format("%.2f", gas));
        billData.put("water", String.format("%.2f", water));
        billData.put("totalBill", String.format("%.2f", totalBill));

        String id = databaseReference.push().getKey();
        if (id != null) {
            databaseReference.child(id).setValue(billData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Bill data saved successfully", Toast.LENGTH_SHORT).show();
                    clearFields();

                    Intent intent = new Intent(CalculateBillActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    private double parseDouble(EditText editText) {
        String text = editText.getText().toString();
        return text.isEmpty() ? 0 : Double.parseDouble(text);
    }

    private void clearFields() {
        editElectricityUsage.setText("");
        editGasUsage.setText("");
        editWaterUsage.setText("");
        billAmountText.setText("Total Bill: ");
    }
}

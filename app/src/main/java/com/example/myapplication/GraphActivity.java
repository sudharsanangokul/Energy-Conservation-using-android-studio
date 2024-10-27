package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);

        // Access Firebase database under the "EnergyUsage" node
        databaseReference = FirebaseDatabase.getInstance().getReference("EnergyUsage");

        // Retrieve data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                ArrayList<BarEntry> barEntries = new ArrayList<>();

                int index = 0; // Index for bar chart entries
                float totalBill = 0f;
                float electricity = 0f;
                float gas = 0f;
                float water = 0f;

                for (DataSnapshot data : snapshot.getChildren()) {
                    // Retrieve the totalBill value
                    String totalBillString = data.child("totalBill").getValue(String.class);
                    String electricityString = data.child("electricity").getValue(String.class);
                    String gasString = data.child("gas").getValue(String.class);
                    String waterString = data.child("water").getValue(String.class);

                    if (totalBillString != null && electricityString != null &&
                            gasString != null && waterString != null) {
                        totalBill += Float.parseFloat(totalBillString);
                        electricity += Float.parseFloat(electricityString);
                        gas += Float.parseFloat(gasString);
                        water += Float.parseFloat(waterString);
                    }
                }

                // Prepare Pie Chart data
                pieEntries.add(new PieEntry(electricity, "Electricity"));
                pieEntries.add(new PieEntry(gas, "Gas"));
                pieEntries.add(new PieEntry(water, "Water"));

                // Create and configure the PieDataSet
                PieDataSet pieDataSet = new PieDataSet(pieEntries, "Energy Usage");
                pieDataSet.setColors(new int[]{
                        ContextCompat.getColor(GraphActivity.this, R.color.red),
                        ContextCompat.getColor(GraphActivity.this, R.color.blue),
                        ContextCompat.getColor(GraphActivity.this, R.color.orange)
                });
                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.invalidate(); // refresh the pie chart

                // Prepare Bar Chart data
                barEntries.add(new BarEntry(0, totalBill));

                // Create and configure the BarDataSet
                BarDataSet barDataSet = new BarDataSet(barEntries, "Total Bill");
                barDataSet.setColor(ContextCompat.getColor(GraphActivity.this, R.color.teal_200));
                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                barChart.invalidate(); // refresh the bar chart
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(GraphActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

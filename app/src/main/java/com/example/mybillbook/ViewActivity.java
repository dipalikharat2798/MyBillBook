package com.example.mybillbook;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    private ArrayList<BillModal> billModalArrayList;
    private DBHandler dbHandler;
    private BillAdapter billRVAdapter;
    private RecyclerView billsRV;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        // initializing our all variables.
        billModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewActivity.this);
        fab=findViewById(R.id.fab);

        // getting our bill array
        // list from db handler class.
        billModalArrayList = dbHandler.readBills();

        // on below line passing our array lost to our adapter class.
        billRVAdapter = new BillAdapter(billModalArrayList, ViewActivity.this,dbHandler);
        billsRV = findViewById(R.id.idRVbills);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewActivity.this, RecyclerView.VERTICAL, false);
        billsRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        billsRV.setAdapter(billRVAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewActivity.this,MainActivity.class));
            }
        });
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewHelper(courseRVAdapter));
//        itemTouchHelper.attachToRecyclerView(coursesRV);
    }
}
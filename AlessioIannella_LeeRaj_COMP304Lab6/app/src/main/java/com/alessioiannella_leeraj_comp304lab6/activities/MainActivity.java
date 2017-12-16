package com.alessioiannella_leeraj_comp304lab6.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alessioiannella_leeraj_comp304lab6.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewExercises;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewExercises = (ListView) findViewById(R.id.listViewExercises);

        List<String> listExercises = new ArrayList<>();
        listExercises.add("Exercise 1");
        listExercises.add("Exercise 2");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview_item_exercise, listExercises);
        listViewExercises.setAdapter(adapter);

        listViewExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent;

                if (i == 0){
                    intent = new Intent(MainActivity.this, Exercise1Activity.class);

                }
                else{
                    intent = new Intent(MainActivity.this, Exercise2Activity.class);
                }

                startActivity(intent);
            }
        });
    }
}

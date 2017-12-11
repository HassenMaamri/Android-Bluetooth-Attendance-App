package com.example.student.bluetoothattendanceapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;


public class Menu extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);


        Prospective p = new Prospective(new String[]{"Steve", "James", "Taylor", "Megan", "John",
                "Mark", "Emily", "Jamie", "Joseph" });

        String[] people = p.getPeople();

        final ListView mylv = (ListView) findViewById(R.id.mylv);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        people) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);

                        Attendee a1 = new Attendee("Taylor", "Taylor LN");
                        Attendee a2 = new Attendee("Emily", "Emily LN");
                        Attendee a3 = new Attendee("Mark", "Mark LN");
                        Attendee a4 = new Attendee("Steve", "Steve LN");


                        String[] here = {a1.getfName(), a2.getfName(), a3.getfName(), a4.getfName()};

                        String person = String.valueOf(mylv.getItemAtPosition(position));


                        if (Arrays.asList(here).contains(person) ){
                            text.setTextColor(Color.rgb(0,153,76));

                        }else{
                            text.setTextColor(Color.RED);

                        }

                        mylv.setOnItemClickListener(
                                new AdapterView.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        String[] here = {"Steve", "Taylor", "Emily", "Mark"};

                                        String person = String.valueOf(parent.getItemAtPosition(position));
                                        if (Arrays.asList(here).contains(person)
                                                ){
                                            Toast.makeText(Menu.this, person + " is here", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(Menu.this, person + " is not here", Toast.LENGTH_LONG).show();

                                        }


                                    }
                                }
                        );

                        return view;
                    }
                };



        mylv.setAdapter(adapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void back(View view)
    {
        Intent intent = new Intent(Menu.this, MainActivity.class);
        startActivity(intent);
    }






}

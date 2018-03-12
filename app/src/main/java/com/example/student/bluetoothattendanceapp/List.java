package com.example.student.bluetoothattendanceapp;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class List extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Menu m = new Menu();

        final ListView mylv = (ListView) findViewById(R.id.mylv);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        m.people) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {


                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);

                        Attendee a1 = new Attendee("Taylor", "Taylor LN");
                        Attendee a2 = new Attendee("Emily", "Emily LN");
                        Attendee a3 = new Attendee("Mark", "Mark LN");
                        Attendee a4 = new Attendee("Steve", "Steve LN");

                        // String[] here = {a1.getfName(), a2.getfName(), a3.getfName(), a4.getfName()};
                        ArrayList<String> here = new ArrayList<String>();
                        here.add(a1.getfName());
                        here.add(a2.getfName());
                        here.add(a3.getfName());
                        here.add(a4.getfName());

                        String person = String.valueOf(mylv.getItemAtPosition(position));

                        if (here.contains(person) ){
                            text.setTextColor(Color.rgb(0,153,76));

                        }else{
                            text.setTextColor(Color.RED);

                        }

                        mylv.setOnItemClickListener(
                                new AdapterView.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        //String[] here = {"Steve", "Taylor", "Emily", "Mark"};
                                        ArrayList<String> here = new ArrayList<String>();
                                        here.add("Steve");
                                        here.add("Taylor");
                                        here.add("Emily");
                                        here.add("Mark");

                                        String person = String.valueOf(parent.getItemAtPosition(position));
                                        if (Arrays.asList(here).contains(person)
                                                ){
                                            Toast.makeText(List.this, person + " is here", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(List.this, person + " is not here", Toast.LENGTH_LONG).show();

                                        }


                                    }
                                }
                        );

                        return view;
                    }
                };

        mylv.setAdapter(adapter);

    }

    public void back(View view)
    {
        Intent intent = new Intent(List.this, Menu.class);

        //closeConnect();

        startActivity(intent);

    }

    public void stop(View view)
    {
        Intent intent = new Intent(List.this, MainActivity.class);

        //closeConnect();

        startActivity(intent);

    }

}

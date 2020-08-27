package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtComputerName,edtComputerType;
    private Button btnAdd,btnDelete;
    private ListView listView;

    List<Computer> allComputers;
    ArrayList<String> computersName;
    MySqliteHandler sqliteHandler;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtComputerName=findViewById(R.id.edtComputerName);
        edtComputerType=findViewById(R.id.edtComputerType);
        btnAdd=findViewById(R.id.btnAdd);
        btnDelete=findViewById(R.id.btnDelete);
        listView=findViewById(R.id.listView);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        sqliteHandler=new MySqliteHandler(this);
        allComputers=sqliteHandler.getAllComputers();
        computersName=new ArrayList<>();

        if(allComputers.size()>0){

            for(int i=0;i<allComputers.size();i++){

                Computer computer=allComputers.get(i);
                computersName.add(computer.getComputerName()+" - "+computer.getComputerType());
            }
        }

        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,computersName);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnAdd:

                if(edtComputerType.getText().toString().matches("")||
                edtComputerType.getText().toString().matches("")){
                    Toast.makeText(this, "Please fill all the data to store in database", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Computer computer = new Computer(edtComputerName.getText().toString(),
                            edtComputerType.getText().toString());

                    allComputers.add(computer);
                    sqliteHandler.addComputer(computer);
                    computersName.add(computer.getComputerName() + " - " + computer.getComputerType());
                    edtComputerName.setText("");
                    edtComputerType.setText("");

                }
                break;

            case R.id.btnDelete:

                if(allComputers.size()>0){

                    computersName.remove(0);
                    sqliteHandler.deleteComputer(allComputers.get(0));
                    allComputers.remove(0);
                    Toast.makeText(this, "delete successfull", Toast.LENGTH_SHORT).show();

                }else{
                    return;
                }
                break;
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
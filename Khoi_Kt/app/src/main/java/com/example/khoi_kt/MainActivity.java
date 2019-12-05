package com.example.khoi_kt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    Database myDB;
    EditText editTextId, editName, editHang, editNam, editHdh;
    Button buttonAdd, buttonGetData, buttonUpdate, buttonDelete, buttonViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new Database(this);
        editTextId = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
        editHang = findViewById(R.id.editText_hang);
        editNam = findViewById(R.id.editText_nam);
        editHdh = findViewById(R.id.editText_hdh);
        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);
        buttonUpdate = findViewById(R.id.button_update);
        buttonGetData = findViewById(R.id.button_view);
        buttonViewAll = findViewById(R.id.button_viewAll);
        AddData();
        getData();
        viewAll();
        updateData();
        deleteData();
    }
    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted =
                        myDB.insertData(editName.getText().toString(),editHang.getText().toString(),
                                editNam.getText().toString(),editHdh.getText().toString());
                if (isInserted == true){
                    Toast.makeText(MainActivity.this, "Them DL thanh cong",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Khong them duoc DL",
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(MainActivity.this, "test",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString();
                if (id.equals(String.valueOf(""))){
                    editTextId.setError("Nhap ID");
                    return;
                }
                Cursor cursor = myDB.getData(id);
                String data = null;
                if (cursor.moveToNext()){
                    data = "ID: "+ cursor.getString(0) +"\n"+
                            "Name: "+ cursor.getString(1) +"\n"+
                            "HANG: "+ cursor.getString(2) +"\n"+
                            "NAM: "+ cursor.getString(3) +"\n"+
                            "HDH: "+ cursor.getString(4) +"\n";
                }
                showMessage("Data: ", data);
            }
        });
    }
    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDB.getAllData();
                //small test
                if (cursor.getCount() == 0){
                    showMessage("Error", "Khong tim thay trong CSDL");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()){
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Hang: "+cursor.getString(2)+"\n");
                    buffer.append("Nam: "+cursor.getString(3)+"\n");
                    buffer.append("Hdh: "+cursor.getString(4)+"\n\n");
                }
                showMessage("CSDL", buffer.toString());
            }
        });
    }
    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate =
                        myDB.updateData(editTextId.getText().toString(),
                                editName.getText().toString(),
                                editHang.getText().toString(),
                                editNam.getText().toString(),
                                editHdh.getText().toString());
                if (isUpdate == true){
                    Toast.makeText(MainActivity.this, "Sua thanh cong",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Loi",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: ID should not be empty
                Integer deletedRow =
                        myDB.deleteData(editTextId.getText().toString());
                if (deletedRow > 0){
                    Toast.makeText(MainActivity.this, "Xoa thanh cong",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Loi!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
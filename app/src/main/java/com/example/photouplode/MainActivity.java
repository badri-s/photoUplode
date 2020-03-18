package com.example.photouplode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    EditText edtName,edtPrice;
    Button btnChoose,btnAdd,btnList;
    ImageView imageView;
    public static SQLiteHelper sqLiteHelper;
    final int REUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        sqLiteHelper = new SQLiteHelper(this,"FoodDB.sqlite",null,1);
        sqLiteHelper.queryData("CREATE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR,price VARCHAR,image BLOG)");
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REUEST_CODE_GALLERY
                );
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqLiteHelper.insertData(
                            edtName.getText().toString().trim(),
                            edtPrice.getText().toString().trim(),
                            imageViewToByte(imageView)
                    );
                    Toast.makeText(getApplicationContext(),"succes",Toast.LENGTH_LONG).show();
                    edtName.setText("");
                    edtPrice.setText("");
                    imageView.setImageResource(R.drawable.ic_launcher_foreground);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FoodListActivity.class);
                startActivity(intent);
            }
        });
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap= ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[]byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(),"vera",Toast.LENGTH_LONG).show();
            }
            return;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REUEST_CODE_GALLERY && resultCode == RESULT_OK &&
        data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        edtName = (EditText)findViewById(R.id.editText);
        edtPrice = (EditText)findViewById(R.id.editText2);
        btnChoose = (Button)findViewById(R.id.btnChoose);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnList = (Button)findViewById(R.id.btnList);
        imageView = (ImageView)findViewById(R.id.imageView);
    }
}

package com.example.pdf;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.idBtnGeneratePDF);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);

        createPdf();

    }

    private void createPdf() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(250,400,1).create();
                PdfDocument.Page myPage = pdfDocument.startPage(pageInfo);

                Canvas canvas = myPage.getCanvas();
                canvas.drawText("Welcome",50,50,paint);
                pdfDocument.finishPage(myPage);

                File file = new File(Environment.getExternalStorageDirectory(),"/FirstPdf.pdf");
                try{
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
                }  catch (IOException e) {
                    e.printStackTrace();
                }

                pdfDocument.close();
            }
        });
    }


}

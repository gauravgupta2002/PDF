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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button,buttonexl;
    ListView listView;
    public static int a1 = 1;
    public static int a2 = 2;
    public static int a3 = 30;
    public static int a4 = 40;
    public static int a5 = 5;
    private  File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/FirstPDF2.xls");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Assam University ( Agricultural Engineering )");
        arrayList.add("Assam University ( Computer Science )");
        arrayList.add("Assam University ( Electronics and Communication )");
        arrayList.add("BIT Mesra ( Bio Technology )");
        arrayList.add("BIT Mesra ( Chemical (Plastic and Polymer) ");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);



        button = findViewById(R.id.idBtnGeneratePDF);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        createPdf(arrayList);

        buttonexl = findViewById(R.id.idBtnGenerateExl);
        createExl(arrayList);

    }

    private void createExl(ArrayList<String> arrayList) {
        buttonexl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                HSSFSheet hssfSheet = hssfWorkbook.createSheet("Custom Sheet");

                for(int i=0;i<arrayList.size();i++) {
                    String string = arrayList.get(i);
                    HSSFRow hssfRow = hssfSheet.createRow(i);
                    HSSFCell hssfCell = hssfRow.createCell(0);

                    hssfCell.setCellValue(string);
                }


                try {
                    if (!filePath.exists()){
                        filePath.createNewFile();
                    }

                    FileOutputStream fileOutputStream= new FileOutputStream(filePath);
                    hssfWorkbook.write(fileOutputStream);
                    Toast.makeText(MainActivity.this, "Excel file generated successfully.", Toast.LENGTH_SHORT).show();

                    if (fileOutputStream!=null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }


    private void createPdf(ArrayList<String> arrayList) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
                PdfDocument.Page myPage = pdfDocument.startPage(pageInfo);

                Canvas canvas = myPage.getCanvas();

                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(12.0f);
                canvas.drawText("JOSAA 2022", pageInfo.getPageWidth() / 2, 30, paint);

                paint.setTextSize(6.0f);
                canvas.drawText("IIT NIT IIIT CFTI", pageInfo.getPageWidth() / 2, 40, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(9.0f);

                int startX = 10, startY = 60;
                //canvas.drawText("Welcome",50,50,paint);
                for (int i = 0; i < arrayList.size(); i++) {
                    String string = arrayList.get(0);
                    canvas.drawText(string, startX, startY, paint);
                    startY = +15;
                }


                pdfDocument.finishPage(myPage);

                //File file = new File(Environment.getExternalStorageDirectory(),"/FirstPdf.pdf");
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/FirstPDF2.pdf");
                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pdfDocument.close();
            }
        });

    }



}

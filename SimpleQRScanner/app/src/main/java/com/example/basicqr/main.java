package com.example.basicqr;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.net.URI;

public class main extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView csview;
    TextView re;

    AlertDialog dia;
    AlertDialog.Builder ab;


    AlertDialog camA;
    AlertDialog.Builder camBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        csview = findViewById(R.id.scannerview);
        codeScanner = new CodeScanner(this,csview);
        re = findViewById(R.id.textView2);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       re.setText(result.getText());
                       showToast(result.getText().substring(0,4));
                       boolean cond1 = result.getText().substring(0,4).equalsIgnoreCase("www.");
                       boolean cond2 = result.getText().substring(0,4).equalsIgnoreCase("http");
                        if(cond1||cond2)
                       {
                           showToast("Message recieved");
                           buildAlert(result.getText());
                       }

                    }
                });
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    private void showToast(String text){
        Toast t = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        t.show();
    }

    private void buildAlert(final String url){
        ab = new AlertDialog.Builder(main.this);
        ab.setTitle("Opening: "+ url +"\nAre you sure?");
        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showToast(url + "yes");
                Uri webI = Uri.parse(url);
                Intent web = new Intent(Intent.ACTION_VIEW,webI);
                startActivity(web);
                finish();
            }
        });

        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showToast(url + "no");
            }
        });

        dia = ab.create();
        dia.show();

    }
}

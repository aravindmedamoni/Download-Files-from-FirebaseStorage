package com.aravind.downloadfilesfromfirebasestorage;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {

    Button download;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference,ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download=findViewById(R.id.button);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowload("javascript_the_good_parts.pdf");
            }
        });
    }

    public void dowload(final String filename) {
        storageReference=firebaseStorage.getInstance().getReference();
        ref=storageReference.child(filename);
      //  final String firstFilename="the _book_of_css";

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
               // downloadFile(MainActivity.this,firstFilename,".pdf",DIRECTORY_DOWNLOADS,url);
                downloadFile(MainActivity.this,filename,DIRECTORY_DOWNLOADS,url);
                Toast.makeText(MainActivity.this, "downloading", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

   // public void downloadFile(Context context,String filename,String fileExtension,String destinationDirectory,String url){
public void downloadFile(Context context,String filename,String destinationDirectory,String url){
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
      //  request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename);
        downloadManager.enqueue(request);
    }
}

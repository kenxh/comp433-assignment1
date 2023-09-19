package com.example.comp433_assignment1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private File imageDir;
    private ImageView imageView, imageView2, imageView3, imageView4;
    private int imageDirIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageView.setTag(1);
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setTag(2);
        imageView3 = findViewById(R.id.imageView3);
        imageView3.setTag(3);
        imageView4 = findViewById(R.id.imageView4);
        imageView4.setTag(4);

        imageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        showLatestImages();
    }

    public void takePicture(View view) {
        Intent cam_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File imageFile = new File(imageDir, "IMG_" + timeStamp + ".png");
        Uri imageUri = FileProvider.getUriForFile(this, "com.example.fileprovider_1", imageFile);
        cam_intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(cam_intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            showLatestImages();
        }
    }

    private void showLatestImages() {
        imageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageDirIndex = imageDir.listFiles().length;

        if (imageDirIndex > 0) {
            Bitmap image = BitmapFactory.decodeFile(imageDir.listFiles()[imageDirIndex - 1].getPath());
            imageView.setImageBitmap(image);
            imageView2.setImageBitmap(image);
            if (imageDirIndex > 1) {
                image = BitmapFactory.decodeFile(imageDir.listFiles()[imageDirIndex - 2].getPath());
                imageView3.setImageBitmap(image);
                if (imageDirIndex > 2) {
                    image = BitmapFactory.decodeFile(imageDir.listFiles()[imageDirIndex - 3].getPath());
                    imageView4.setImageBitmap(image);
                }
            }
        }
    }

    public void replaceImage(View view) {
        switch ((int) view.getTag()) {
            case 2:
                Bitmap image = BitmapFactory.decodeFile(imageDir.listFiles()[imageDirIndex - 1].getPath());
                imageView.setImageBitmap(image);
                break;
            case 3:
                image = BitmapFactory.decodeFile(imageDir.listFiles()[imageDirIndex - 2].getPath());
                imageView.setImageBitmap(image);
                break;
            case 4:
                image = BitmapFactory.decodeFile(imageDir.listFiles()[imageDirIndex - 3].getPath());
                imageView.setImageBitmap(image);
                break;
        }
    }
}
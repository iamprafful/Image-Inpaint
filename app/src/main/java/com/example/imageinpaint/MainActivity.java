package com.example.imageinpaint;

import static org.opencv.photo.Photo.INPAINT_NS;
import static org.opencv.photo.Photo.INPAINT_TELEA;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.image);
        if (!OpenCVLoader.initDebug())
            Log.e("OpenCV", "Unable to load OpenCV!");
        else {
            Log.d("OpenCV", "OpenCV loaded Successfully!");
            try {
                Mat input = Utils.loadResource(this, R.drawable.input);
                Mat greyScale = Utils.loadResource(this, R.drawable.greyscale);
                Mat output = new Mat();
                Mat rgb = new Mat();
                Photo.inpaint(input, greyScale, output, 40, INPAINT_NS);
                Imgproc.cvtColor(output, rgb, Imgproc.COLOR_BGR2RGB);
                Bitmap bmp = Bitmap.createBitmap(output.cols(), output.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(rgb, bmp);
                int width= this.getResources().getDisplayMetrics().widthPixels;
                Bitmap bmp1 = Bitmap.createScaledBitmap(bmp, width, width*output.rows()/output.cols(), true);
                imageView.setImageBitmap(bmp1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
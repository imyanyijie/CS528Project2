package com.bignerdranch.android.criminalintent;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.List;
import java.util.UUID;


public class GalleryActivity extends AppCompatActivity {
    private static final String ARG_CRIME_ID = "crime_id";
    private Crime mCrime;
    private List<String> photoPathList;
    private GridView photoGridView;
    private static boolean faceFlag;
    public GalleryActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        faceFlag = false;
        photoGridView = (GridView) findViewById(R.id.galleryView);
        UUID crimeId = (UUID) getIntent().getSerializableExtra("UUID");
        faceFlag = getIntent().getBooleanExtra("FACEDTECT",false);
        Log.e("check","the face flag is "+faceFlag);
        mCrime = CrimeLab.get(this).getCrime(crimeId);
        photoPathList = CrimeLab.get(this).getPhotoFileList(mCrime);
        photoGridView.setAdapter(new ImageAdapterGridView(this));
    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        public int getCount() {
            return photoPathList.size();
        }

        public Object getItem(int position) {
            return position;
    }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            File storage = CrimeLab.get(GalleryActivity.this).getStorage(mCrime);
            File imageFile = new File(storage, photoPathList.get(position));


            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    imageFile.getPath(), GalleryActivity.this);
            Log.e("check","the face flag is "+faceFlag);
            if(faceFlag == true){
                bitmap = CrimeLab.get(GalleryActivity.this).setfacedetection(bitmap);
            }



            ImageView imageView = new ImageView(mContext);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));


            return imageView;
        }
    }


}

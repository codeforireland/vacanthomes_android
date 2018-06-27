package com.codeforireland.vacanthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.codeforireland.vacanthome.model.HomeData;
import com.codeforireland.vacanthome.utils.PhotoFixOrirntation;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nikodem Walicki on 2018-06-10.
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link FragmentStepFirstPhoto#newInstance} factory method to
 * create an instance of this fragment.
 * TODO: implement android View Model
 */
public class FragmentStepFirstPhoto extends Fragment implements Step {

    private final static String TAG = FragmentStepFirstPhoto.class.getSimpleName();
    private static final String ARG_POSITION = "current_step_position_key";
    private String mCurrentPhotoPath;
    private Button btnPhoto;
    private ImageView imgView;
    private static final int TAKE_PICTURE = 111;
    private Uri imageUri;
    private Bitmap photo;

    private FragmentStepsInterfaces.FirstStepInterface mListener;

    public FragmentStepFirstPhoto() {}

    public static FragmentStepFirstPhoto newInstance() {
        return new FragmentStepFirstPhoto();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_step_first_photo, container, false);
        imgView = v.findViewById(R.id.fragment_step_first_imageView);
        btnPhoto = v.findViewById(R.id.fragment_step_first_capture_pic_btn);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        something(true);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    public void something(boolean done) {
        if (mListener != null) {
            mListener.onPhotoDone(done);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "vacant-home-" + timeStamp;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        imageUri = FileProvider.getUriForFile(getContext(), "com.codeforireland.vacanthome.provider", image);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "create photo err: "+ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(getContext(),
                        "com.codeforireland.vacanthome.provider", photoFile);
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                Log.d(TAG, "photo absolute path: "+mCurrentPhotoPath);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }
    }

    private void setImage() {
        try {
            photo = PhotoFixOrirntation.corectPhoto(mCurrentPhotoPath );
            imgView.setImageBitmap(photo);
            something(true);
        } catch (Exception e) {
            e.printStackTrace();
            something(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TAKE_PICTURE){
            if (resultCode == Activity.RESULT_OK) {
                setImage();
            }else {
                Log.d(TAG, "Failed to take photo");
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentStepsInterfaces.FirstStepInterface) {
            mListener = (FragmentStepsInterfaces.FirstStepInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FirstStepInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        Log.d(TAG, " step verify if is photo taken");
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        if(photo!=null){
            HomeData.getHomeDataInstance().setPhoto(photo);
            return null;
        }else return new VerificationError("please take a photo before go NEXT");
    }

    @Override
    public void onSelected() {
        //update UI when selected
        if(HomeData.getHomeDataInstance().getPhoto()!=null) imgView.setImageBitmap(HomeData.getHomeDataInstance().getPhoto());
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside fragment
        Log.d(TAG, "step error: "+error.getErrorMessage());
    }
}
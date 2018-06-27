package com.codeforireland.vacanthome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeforireland.vacanthome.model.HomeData;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link FragmentThirdDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThirdDetails extends Fragment implements Step{

    private static final String TAG = FragmentThirdDetails.class.getSimpleName();
    private static final String ARG_POSITION = "current_step_position_key";
    private TextView tvInfo, tvHomeType, tvGrass, tvWindows, tvActivity, tvComments;
    private ImageView imgPhoto;

    private FragmentStepsInterfaces.ThirdStepInterface mListener;

    public FragmentThirdDetails() {
    }

    public static FragmentThirdDetails newInstance() {
        return new FragmentThirdDetails();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_third_details, container, false);
        tvInfo = v.findViewById(R.id.fragment_step_third_text_info);
        tvHomeType = v.findViewById(R.id.fragment_step_third_text_typeHome_value);
        tvGrass = v.findViewById(R.id.fragment_step_third_text_grass_value);
        tvWindows = v.findViewById(R.id.fragment_step_third_text_windows_value);
        tvActivity = v.findViewById(R.id.fragment_step_third_text_activity_value);
        tvComments = v.findViewById(R.id.fragment_step_third_text_comment_value);
        imgPhoto = v.findViewById(R.id.fragment_step_third_imageView_photo);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void displayData(){
        if(HomeData.getHomeDataInstance()!=null){
            tvHomeType.setText(HomeData.getHomeDataInstance().getHomeType());
            tvGrass.setText((HomeData.getHomeDataInstance().isGrassOverGrown() ? "yes" : "no"));
            tvWindows.setText(HomeData.getHomeDataInstance().isWindowsBoarded() ? "yes" : "no");
            tvActivity.setText(HomeData.getHomeDataInstance().isVisibleActivity() ? "yes" : "no");
            tvComments.setText(HomeData.getHomeDataInstance().getComment());
            imgPhoto.setImageBitmap(HomeData.getHomeDataInstance().getPhoto());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentStepsInterfaces.ThirdStepInterface) {
            mListener = (FragmentStepsInterfaces.ThirdStepInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ThirdStepInterface");
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
        if(HomeData.getHomeDataInstance()!=null){
            return null;
        }else return new VerificationError("missing data");
    }

    @Override
    public void onSelected() {
        //update UI
        displayData();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
    }
}
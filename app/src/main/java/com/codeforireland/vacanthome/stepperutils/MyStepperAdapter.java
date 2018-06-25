package com.codeforireland.vacanthome.stepperutils;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.codeforireland.vacanthome.FragmentSecondStepAddress;
import com.codeforireland.vacanthome.FragmentStepFirstPhoto;
import com.codeforireland.vacanthome.FragmentThirdDetails;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    private final static String TAG = MyStepperAdapter.class.getSimpleName();

    public MyStepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Log.d(TAG, "create step no: "+position);
        Step step;
        switch (position){
            case 0: step = FragmentStepFirstPhoto.newInstance();
                break;
            case 1: step = FragmentSecondStepAddress.newInstance();
                break;
            case 2: step = FragmentThirdDetails.newInstance();
                break;
            default: step = null;
                Log.d(TAG, "createStep switch default");
        }
        return step;
    }

    @Override
    public int getCount() {
        return RegisterHomeSteps.getStepsList().size();
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange (from = 0) int position) {
        Log.d(TAG, "return new stepper view model");
        return new StepViewModel.Builder(context)
//                .setSubtitle("sub position of "+String.valueOf(position))
                .setTitle(RegisterHomeSteps.getStepsList().get(position))
                .create();
    }
}

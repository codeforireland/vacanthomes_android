package com.codeforireland.vacanthome;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.codeforireland.vacanthome.stepperutils.MyStepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public abstract class AbstractStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener, OnNavigationBarListener,
        FragmentStepsInterfaces.FirstStepInterface, FragmentStepsInterfaces.SecondStepInterface, FragmentStepsInterfaces.ThirdStepInterface {

    private static final String TAG=AbstractStepperActivity.class.getSimpleName();
    private static final  String CURRENT_STEP_POSITION_KEY = "current_step_position";
    private int layoutId;
    private StepperLayout stepperLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutId = R.layout.activity_main_new;
        Log.d(TAG, "running onCreate, layout ID: "+layoutId);
        setContentView(layoutId);
        setTitle(getResources().getString(R.string.app_name));
        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;
        stepperLayout = findViewById(R.id.stepperLayout);
        stepperLayout.setAdapter(new MyStepperAdapter(getSupportFragmentManager(), this));
        stepperLayout.setListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, stepperLayout.getCurrentStepPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        int currentStepPosition = stepperLayout.getCurrentStepPosition();
        Log.d(TAG, "onBackPressed position: "+currentStepPosition);
        if (currentStepPosition > 0) {
            stepperLayout.onBackClicked();
        } else {
            finish();
        }
    }

    @Override
    public void onChangeEndButtonsEnabled(boolean enabled) {
        Log.d(TAG, "onChangeEndButtonsEnabled: "+String.valueOf(enabled));
        stepperLayout.setNextButtonVerificationFailed(!enabled);
        stepperLayout.setCompleteButtonVerificationFailed(!enabled);
    }

    @Override
    public void onCompleted(View completeButton) {
        Log.d(TAG, "onCompleted (button ID): "+String.valueOf(completeButton.getId()));
        Toast.makeText(this, "Data ready to GO!", Toast.LENGTH_SHORT).show();
        //TODO:send data to VH server....
        //TODO: if server response success clear HomeData singleton
    }

    @Override
    public void onError(VerificationError verificationError) {
        Log.d(TAG, "onError: "+verificationError.getErrorMessage());
        showSnackbar(verificationError.getErrorMessage());
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        Log.d(TAG, "onStepSelected: "+newStepPosition);
    }

    @Override
    public void onReturn() {
        Log.d(TAG, "onReturn");
        finish();
    }

    @Override
    public void onPhotoDone(boolean isDone) {
        Log.d(TAG, "onPhotoDone: "+String.valueOf(isDone));
    }

    @Override
    public void onAddressDone(boolean isDone) {
        Log.d(TAG, "onAddressDone: "+String.valueOf(isDone));
    }

    @Override
    public void onDetailsDone(boolean isDone) {
        Log.d(TAG, "onDetailsDone: "+String.valueOf(isDone));
    }

    private void showSnackbar(String message){
        final Snackbar snackbar = Snackbar.make(stepperLayout, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
package com.codeforireland.vacanthome;

public class FragmentStepsInterfaces {

    interface FirstStepInterface{
        void onPhotoDone(boolean isDone);
    }
    interface SecondStepInterface{
        void onAddressDone(boolean isDone);
    }
    interface ThirdStepInterface{
        void onDetailsDone(boolean isDone);
    }

}

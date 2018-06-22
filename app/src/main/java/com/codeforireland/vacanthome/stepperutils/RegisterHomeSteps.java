package com.codeforireland.vacanthome.stepperutils;

import java.util.ArrayList;

public class RegisterHomeSteps {

    private static ArrayList<String> stepsList = new ArrayList<>();

    static {
        stepsList.add("take a photo");
        stepsList.add("details");
        stepsList.add("upload");
    }

    public static ArrayList<String> getStepsList(){
        return stepsList;
    }
}

package com.example.sfmapp;

import android.widget.EditText;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

public class GlobalVariablesJava {
    public static String currentAcRefInstaller;
    public static int instructionCounter = 0;
    public static EditText ref;
    public static List<String> selected = new ArrayList<String>();
    public static List<String> tempList = new ArrayList<String>();
    public static int generalTemp;
    public static int currentTemperature;
    public static int minTemp, maxTemp;
    public static List<String> emplacementSelected = new ArrayList<String>();
}

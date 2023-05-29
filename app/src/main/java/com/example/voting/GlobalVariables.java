package com.example.voting;

import java.util.ArrayList;

public interface GlobalVariables {

    //hosting
//    public static final String url = "http://ucc-csd-bscs.com/AlumniVot";
    //localHost
    public static final  String url = "http://192.168.1.135/AlumniVot";

    public ArrayList<Candidates> votedList = new ArrayList<Candidates>();

}

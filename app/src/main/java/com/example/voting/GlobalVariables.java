package com.example.voting;

import java.util.ArrayList;

public interface GlobalVariables {

    //hosting
//    public static final String url = "http://ucc-csd-bscs.com/VOT";
    //localHost
    public static final  String url = "http://192.168.240.116/VOT";

    public ArrayList<Candidates> votedList = new ArrayList<Candidates>();

}

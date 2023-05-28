package com.example.voting;

public class Candidates {

    private int id;

    private String name;

    private String position;
    private String img;

    public Candidates(int id, String name,String position, String image) {
        super();
        this.id = id;
        this.name = name;

        this.position = position;
        this.img = image;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

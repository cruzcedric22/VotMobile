package com.example.voting;

public class Candidates {

    private int id;

    private String name;
    private String section;
    private String position;
    private String img;

    public Candidates(int id, String name, String section, String position, String image) {
        super();
        this.id = id;
        this.name = name;
        this.section = section;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

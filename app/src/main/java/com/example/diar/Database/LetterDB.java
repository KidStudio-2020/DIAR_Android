package com.example.diar.Database;

public class LetterDB {
    public static final String NAME = "letter";

    public static final String LETTERID = "letter_id";
    public static final String LAT = "lat";
    public static final String LONG = "lng";
    public static final String CREATEDATE = "create_date";
    public static final String CONTENT = "content";
    public static final String PICTURE = "picture";
    public static final String TITLE = "title";

    private int letter_id;
    private double lat;
    private double lng;
    private String create_date;
    private String content;
    private String picture;
    private String title;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NAME + "("
                    + LETTERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + LAT + " DOUBLE NOT NULL, "
                    + LONG + " DOUBLE NOT NULL, "
                    + CREATEDATE + " STRING, "
                    + CONTENT + " TEXT, "
                    + PICTURE + " STRING, "
                    + TITLE + " STRING"
                    + ");";

    public LetterDB(){
    }

    public LetterDB(int letter_id, double lat, double lng, String create_date, String content, String picture, String title) {
        this.letter_id = letter_id;
        this.lat = lat;
        this.lng = lng;
        this.create_date = create_date;
        this.content = content;
        this.picture = picture;
        this.title = title;
    }

    public int getCapsule_id() {
        return letter_id;
    }

    public double getLatitude(){

        return lat;
    }
    public double getLongitude(){
        return lng;
    }
    public String getCreate_date(){
        return create_date;
    }
    public String getContent(){
        return content;
    }
    public String getPicture(){
        return picture;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void  setContent(String content){
        this.content = content;
    }

    public void setLetter_id(int letter_id) {
        this.letter_id = letter_id;
    }

    public void setLatitude(double lat) {
        this.lat = lat;
    }
    public void setLongitude(double lng) {
        this.lng = lng;
    }
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
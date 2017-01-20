package com.example.adminto.mytasks;


public class Task {


    String Time, Note , Date, Daily;
    int id;

    public Task(){
    }

    public Task(String Time, String Note , String Date, String Daily){
        this.Time = Time;
        this.Note = Note;
        this.Date = Date;
        this.Daily = Daily;
    }
    public Task(int id){
        this.id = id;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getTime(){
        return this.Time;
    }

    public void setTime(String Time){
        this.Time = Time;
    }

    public String getNote(){
        return this.Note;
    }

    public void setNote(String Note){
        this.Note = Note;
    }

    public String getDate(){
        return this.Date;
    }

    public void setDate(String Date){
        this.Date = Date;
    }

    public String getDaily(){
        return this.Daily;
    }

    public void setDaily(String Daily){
        this.Daily = Daily;
    }
}

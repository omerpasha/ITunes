package com.example.itunes;

public class ListItem {

    private String head;
    private String  desc;
    private String  id;
    private String  value;
    //encapsullation açısından private yapıldı
    //ve getter setter metodlarıyla ancak ulaşılabiliyor
    public ListItem(String head,String desc, String id, String value){
        this.head=head;
        this.desc=desc;
        this.id=id;
        this.value=value;
        //constructor
    }
    public String getHead(){
        return head;
    }
    public String getDesc(){
        return desc;
    }
    public String getId(){
        return id;
    }
    public String getValue(){ return value; }
}

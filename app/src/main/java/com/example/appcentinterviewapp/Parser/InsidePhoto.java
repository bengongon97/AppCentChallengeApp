package com.example.appcentinterviewapp.Parser;

public class InsidePhoto {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;

    public InsidePhoto(String id, String owner, String secret, String server, int farm, String title, int ispublic, int isfriend, int isfamily) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.ispublic = ispublic;
        this.isfriend = isfriend;
        this.isfamily = isfamily;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }
}
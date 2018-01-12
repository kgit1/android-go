package com.konggit.appmvpdaggerted.model;

/**
 * Created by erik on 08.01.2018.
 */

public class ItemTalk {

    private int id;
    private String name;
    private String description;
    private String published;

    public ItemTalk() {
    }

    @Override
    public String toString() {
        return "ItemTalk{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", published='" + published + '\'' +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

}

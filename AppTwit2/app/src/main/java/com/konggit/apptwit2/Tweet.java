package com.konggit.apptwit2;

public class Tweet {

    private String tweet;
    private String username;
    private String createdAt;
    private String changedAt;

    public Tweet() {
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(String changedAt) {
        this.changedAt = changedAt;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "username='" + username + '\'' +
                ", tweet='" + tweet + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", changedAt='" + changedAt + '\'' +
                '}';
    }
}

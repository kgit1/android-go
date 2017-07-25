package com.konggit.apptwit;

public class ContactListItem {

    String username;
    boolean checked = false;

    public ContactListItem() {
    }

    public ContactListItem(String username, boolean checked) {
        this.username = username;
        this.checked = checked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "ContactListItem{" +
                "username='" + username + '\'' +
                ", checked=" + checked +
                '}';
    }
}

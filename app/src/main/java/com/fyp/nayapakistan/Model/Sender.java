package com.fyp.nayapakistan.Model;

public class Sender {
    public String to;
    public Data data;
    public String priority;

    public Sender(String to, Data notification, String priority) {
        this.to = to;
        this.data = notification;
        this.priority = priority;
    }

    public Sender() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getNotification() {
        return data;
    }

    public void setNotification(Data notification) {
        this.data = notification;
    }
}

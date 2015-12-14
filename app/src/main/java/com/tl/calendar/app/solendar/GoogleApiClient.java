package com.tl.calendar.app.solendar;

/**
 * Created by AMONRATS on 15/12/2558.
 */
public interface GoogleApiClient {
    void connect();

    void disconnect();

    public class Builder {
        public Builder(MainActivity mainActivity) {
        }
    }
}

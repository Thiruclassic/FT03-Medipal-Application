package iss.medipal.model;

import java.util.Date;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class Reminder {
    int id;
    int frequency;
    Date startTime;
    int Interval;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getInterval() {
        return Interval;
    }

    public void setInterval(int interval) {
        Interval = interval;
    }
}

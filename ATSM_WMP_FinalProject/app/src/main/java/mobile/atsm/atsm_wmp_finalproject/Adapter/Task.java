package mobile.atsm.atsm_wmp_finalproject.Adapter;

import java.util.Date;

/**
 * Created by JinGuang on 12/22/2016.
 */

public class Task {
    public Task(String id_task, String name_task, String level, String start_date, String end_date) {
        super();
        this.id_task = id_task;
        this.name_task = name_task;
        this.level = level;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getId_task() {
        return id_task;
    }

    public void setId_task(String id_task) {
        this.id_task = id_task;
    }

    public String getName_task() {
        return name_task;
    }

    public void setName_task(String name_task) {
        this.name_task = name_task;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String id_task;
    public String name_task;
    public String level;
    public String start_date;
    public String end_date;

}

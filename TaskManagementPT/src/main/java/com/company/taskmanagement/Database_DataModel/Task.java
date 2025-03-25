package com.company.taskmanagement.Database_DataModel;

import java.io.Serializable;

public abstract class Task implements Serializable {

    protected int idTask;
    protected String statusTask;
    protected String name;

    //the constructor
    public Task(int idTask, String statusTask, String name) {
        this.idTask = idTask;
        this.statusTask = statusTask;
        this.name = name;
    }

    //getters and setters
    public int getIdTask() {return idTask;}
    public String getStatusTask() {return statusTask;}

    public String getName() {
        return name;
    }

    public void setStatusTask(String statusTask) {this.statusTask = statusTask;}

    //the abstract methods to be implemented in the subclasses
    public abstract int estimateDuration();
    public abstract String getTaskType();

    @Override
    public String toString() {
        return name;
    }
}

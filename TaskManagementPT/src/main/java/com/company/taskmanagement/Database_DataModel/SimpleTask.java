package com.company.taskmanagement.Database_DataModel;

public class SimpleTask extends Task {

    private int startHour;
    private int endHour;

    //the constructor
    public SimpleTask(int idTask, String StatusTask, String name,int startHour, int endHour) {
        super(idTask, StatusTask, name);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    @Override
    public int estimateDuration() {
        return endHour - startHour;
    }

    @Override
    public String getTaskType() {
        return "Simple Task";
    }
}

package com.company.taskmanagement.Database_DataModel;

import java.util.ArrayList;
import java.util.List;

public class ComplexTask extends Task {
    private List<Task> subTasks;

    public ComplexTask(int idTask, String statusTask, String name) {
        super(idTask, statusTask, name);
        this.subTasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        subTasks.add(task);
    }

    public void deleteTask(Task task) {
        subTasks.remove(task);
    }

    @Override
    public int estimateDuration() {
        return subTasks.stream().mapToInt(Task::estimateDuration).sum();
    }

    @Override
    public String getTaskType() {
        return "Complex Task";
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }
}

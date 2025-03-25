package com.company.taskmanagement.Business_Services;

import com.company.taskmanagement.Database_DataModel.*;
import com.company.taskmanagement.Persistance_UtilsAndRepository.*;


import java.util.*;

public class TaskService {
    private TaskRepository taskRepository;

    // Constructor
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Add an employee and save to file
    public void addEmployee(Employee employee) {
        taskRepository.addEmployee(employee);
        taskRepository.saveEmployees();
    }

    // Add a task and save to file
    public void addTask(Task task) {
        taskRepository.addTask(task);
        taskRepository.saveTasks();
    }

    // Assign a task to an employee
    public void assignTaskToEmployee(Employee employee, Task task) {
        taskRepository.assignTaskToEmployee(employee, task);
        taskRepository.saveEmployeeTasks();
    }

    // Calculate total work duration for an employee based on "Done" tasks
    public int calculateEmployeeWorkDuration(Employee employee) {
        List<Task> tasks = taskRepository.getTasksByEmployee(employee);
        int totalDuration = 0;

        for (Task task : tasks) {
            if ("Done".equalsIgnoreCase(task.getStatusTask())) {
                totalDuration += task.estimateDuration();
            }
        }
        return totalDuration;
    }

    public boolean modifyTaskStatus(int taskId, String newStatus) {
        // Fetch the task from the repository
        Task task = taskRepository.getTaskById(taskId);

        if (task != null) {
            task.setStatusTask(newStatus);
            taskRepository.saveTasks();  // Save the updated task in the repository
            return true;
        }
        return false;
    }

    // Get all tasks assigned to a specific employee
    public List<Task> getTasksByEmployee(Employee employee) {
        return taskRepository.getTasksByEmployee(employee);
    }

    // Retrieve list of all employees
    public List<Employee> getAllEmployees() {
        return taskRepository.getEmployees();
    }

    // Retrieve list of all tasks
    public List<Task> getAllTasks() {
        return taskRepository.getTasks();
    }

    // Save all data
    public void saveAllData() {
        taskRepository.saveAll();
    }

    public void loadData() {
        System.out.println("Loading data from files...");
        taskRepository = new TaskRepository();

        List<Employee> employees = taskRepository.getEmployees();
        List<Task> tasks = taskRepository.getTasks();

        System.out.println("Loaded Employees:");
        for (Employee emp : employees) {
            System.out.println(emp);
        }

        System.out.println("Loaded Tasks:");
        for (Task task : tasks) {
            System.out.println(task);
        }

        System.out.println("Loaded Employee-Task Mappings:");
        for (Employee emp : taskRepository.getEmployees()) {
            List<Task> assignedTasks = taskRepository.getTasksByEmployee(emp);
            System.out.println(emp.getName() + " has " + assignedTasks.size() + " assigned tasks.");
        }
    }
}

package com.company.taskmanagement.Persistance_UtilsAndRepository;

import com.company.taskmanagement.Database_DataModel.*;
import com.company.taskmanagement.Persistance_UtilsAndRepository.PersistanceUtility;


import java.io.Serializable;
import java.util.*;

public class TaskRepository implements Serializable{
    private static final String MAP_FILE = "Map.dat";

    private static final String EMPLOYEES_FILE = "EmployeesTable.dat";
    private static final String TASKS_FILE = "TasksTable.dat";

    private Map<Employee, List<Task>> MapET;
    private List<Employee> employees;
    private List<Task> tasks;

    //the constructor
    public TaskRepository() {
        Object loadedEmployees = PersistanceUtility.loadData(EMPLOYEES_FILE);
        Object loadedTasks = PersistanceUtility.loadData(TASKS_FILE);
        Object loadedMap = PersistanceUtility.loadData(MAP_FILE);

        employees = (loadedEmployees instanceof List) ? (List<Employee>) loadedEmployees : new ArrayList<>();
        tasks = (loadedTasks instanceof List) ? (List<Task>) loadedTasks : new ArrayList<>();
        MapET = (loadedMap instanceof Map) ? (Map<Employee, List<Task>>) loadedMap : new HashMap<>();
    }


    // Assign a task to an employee
    public void assignTaskToEmployee(Employee employee, Task task) {
        MapET.computeIfAbsent(employee, k -> new ArrayList<>()).add(task);
        saveEmployeeTasks();
    }

    ///GETTERS
    // Get all employees by the employee list
    public List<Employee> getEmployees() {
        return employees;
    }

    // Get all tasks by the list of tasks
    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getTasksByEmployee(Employee employee){
        return MapET.getOrDefault(employee, new ArrayList<>()); //what does this getOrDefault do???
    }

    public Task getTaskById(int taskId) {
        // First, check the tasks list (all tasks, not assigned to any employee)
        for (Task task : tasks) {
            if (task.getIdTask() == taskId) {
                return task;  // Found in the list of all tasks
            }
        }

        // If not found in the tasks list, check in the employee-task map
        for (List<Task> taskList : MapET.values()) {
            for (Task task : taskList) {
                if (task.getIdTask() == taskId) {
                    return task;  // Found in the task list of an employee
                }
            }
        }

        return null;  // If task is not found in either list
    }

//    public void saveTask(Task task) {
//        // Save the updated task back to the repository
//        // You can update the in-memory list or write it back to a file/database
//    }

    ///ADDING to the lists
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    // Save employees to file
    public void saveEmployees() {
        PersistanceUtility.saveData(employees, EMPLOYEES_FILE);
    }

    // Save tasks to file
    public void saveTasks() {
        PersistanceUtility.saveData(tasks, TASKS_FILE);
    }

    ///de ce nu mergeeeee :""""""))
    // Save employee-task mapping to file
    public void saveEmployeeTasks() {
        PersistanceUtility.saveData(MapET, MAP_FILE);
    }

    // Save all data
    public void saveAll() {
        saveEmployees();
        saveTasks();
        saveEmployeeTasks();
    }

}

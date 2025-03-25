package com.company.taskmanagement.Presentation_GUI;

import com.company.taskmanagement.Business_Services.TaskService;
import com.company.taskmanagement.Database_DataModel.*;
import com.company.taskmanagement.Persistance_UtilsAndRepository.TaskRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    private TaskService taskService;
    private TaskRepository taskRepository;

    public GUI() {
        // Initialize services
        taskRepository = new TaskRepository();
        taskService = new TaskService(taskRepository);

        // Set up the frame
        setTitle("Task Management Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the main panel with buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton addEmployeeButton = new JButton("Add Employee");
        JButton addTaskButton = new JButton("Add Task");
        JButton modifyTaskStatusButton = new JButton("Modify Task Status");
        JButton assignTaskButton = new JButton("Assign Task");
        JButton viewEmployeeTasksButton = new JButton("View Employee Tasks");
        JButton calculateWorkDurationButton = new JButton("Calculate Work Duration");


        mainPanel.add(addEmployeeButton);
        mainPanel.add(addTaskButton);
        mainPanel.add(modifyTaskStatusButton);
        mainPanel.add(assignTaskButton);
        mainPanel.add(viewEmployeeTasksButton);
        mainPanel.add(calculateWorkDurationButton);

        // Create the bottom panel with additional buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 4, 5, 5));

        JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");
        JButton viewEmployeesButton = new JButton("View Employees");
        JButton viewTasksButton = new JButton("View Tasks");

        bottomPanel.add(loadButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(viewEmployeesButton);
        bottomPanel.add(viewTasksButton);

        // Add action listeners
        addEmployeeButton.addActionListener(e -> openAddEmployeePanel());
        addTaskButton.addActionListener(e -> openAddTaskPanel());
        modifyTaskStatusButton.addActionListener(e -> openModifyTaskStatusPanel());
        assignTaskButton.addActionListener(e -> assignTaskToEmployee());
        viewEmployeeTasksButton.addActionListener(e -> viewTasksByEmployee());
        calculateWorkDurationButton.addActionListener(e -> calculateEmployeeWorkDuration());
        loadButton.addActionListener(e -> taskService.loadData());
        saveButton.addActionListener(e -> taskService.saveAllData());
        viewEmployeesButton.addActionListener(e -> viewEmployees());
        viewTasksButton.addActionListener(e -> viewTasks());


        // Add panels to frame
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void assignTaskToEmployee() {
        Employee employee = selectEmployee();
        Task task = selectTask();
        if (employee != null && task != null) {
            taskService.assignTaskToEmployee(employee, task);
            JOptionPane.showMessageDialog(this, "Task assigned successfully!");
        }
    }

    private void viewTasksByEmployee() {
        Employee employee = selectEmployee();

        if (employee != null) {
            List<Task> tasks = taskService.getTasksByEmployee(employee);
            StringBuilder message = new StringBuilder();
            for (Task task : tasks) {
                message.append(task.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Employee Tasks", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void calculateEmployeeWorkDuration() {
        Employee employee = selectEmployee();
        if (employee != null) {
            int duration = taskService.calculateEmployeeWorkDuration(employee);
            JOptionPane.showMessageDialog(this, "Total Work Duration: " + duration + " hours", "Work Duration", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Employee selectEmployee() {
        List<Employee> employees = taskService.getAllEmployees();
        Employee[] employeeArray = employees.toArray(new Employee[0]);
        return (Employee) JOptionPane.showInputDialog(this, "Select Employee:", "Choose Employee", JOptionPane.QUESTION_MESSAGE, null, employeeArray, employeeArray[0]);
    }

    private Task selectTask() {
        List<Task> tasks = taskService.getAllTasks();
        Task[] taskArray = tasks.toArray(new Task[0]);
        return (Task) JOptionPane.showInputDialog(this, "Select Task:", "Choose Task", JOptionPane.QUESTION_MESSAGE, null, taskArray, taskArray[0]);
    }

    private void openAddEmployeePanel() {
        String id = JOptionPane.showInputDialog("Enter Employee ID:");
        String name = JOptionPane.showInputDialog("Enter Employee Name:");
        if (id != null && name != null) {
            Employee newEmployee = new Employee(Integer.parseInt(id), name);
            taskService.addEmployee(newEmployee);
            System.out.println("Employee Added: " + newEmployee.getName());
        }
    }

    private void openAddTaskPanel() {
        String id = JOptionPane.showInputDialog("Enter Task ID:");
        String name = JOptionPane.showInputDialog("Enter Task Name:");
        String status = JOptionPane.showInputDialog("Enter Task Status:");
        int taskId = Integer.parseInt(id);

        String[] options = {"Simple", "Complex"};
        int choice = JOptionPane.showOptionDialog(null, "Select Task Type:", "Task Type",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        Task newTask;
        if (choice == 0) { // Simple Task
            int startHour = Integer.parseInt(JOptionPane.showInputDialog("Enter Start Hour:"));
            int endHour = Integer.parseInt(JOptionPane.showInputDialog("Enter End Hour:"));
            newTask = new SimpleTask(taskId, status, name, startHour, endHour);
        } else { // Complex Task
            newTask = new ComplexTask(taskId, status, name);
        }

        taskService.addTask(newTask);
        System.out.println("Task Added: " + newTask.toString());
    }

    private void openModifyTaskStatusPanel() {
        String id = JOptionPane.showInputDialog("Enter Task ID to Modify Status:");
        String newStatus = JOptionPane.showInputDialog("Enter New Status:");
        int taskId = Integer.parseInt(id);

        // Use TaskService to update the task status
        boolean updated = taskService.modifyTaskStatus(taskId, newStatus);

        if (updated) {
            JOptionPane.showMessageDialog(this, "Task status updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Task not found or failed to update.");
        }
    }

    private void viewEmployees() {
        List<Employee> employees = taskRepository.getEmployees();

        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found.", "View Employees", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"Employee ID", "Name"};
        Object[][] data = new Object[employees.size()][2];

        int index = 0;
        for (Employee emp : employees) {
            data[index][0] = emp.getIdEmployee();
            data[index][1] = emp.getName();
            index++;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(this, scrollPane, "Employee List", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewTasks() {
        String[] options = {"Simple Tasks", "Complex Tasks"};
        int choice = JOptionPane.showOptionDialog(null, "Select Task Type to View:", "View Tasks",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        List<Task> tasks = taskRepository.getTasks();
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> (choice == 0 && task instanceof SimpleTask) || (choice == 1 && task instanceof ComplexTask))
                .toList();

        if (filteredTasks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tasks found.", "View Tasks", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"Task ID", "Name", "Status", "Type", "Start Hour", "End Hour"};
        Object[][] data = new Object[filteredTasks.size()][6];

        int index = 0;
        for (Task task : filteredTasks) {
            data[index][0] = task.getIdTask();
            data[index][1] = task.toString();
            data[index][2] = task.getStatusTask();
            data[index][3] = task.getTaskType();

            if (task instanceof SimpleTask simpleTask) {
                data[index][4] = simpleTask.getStartHour();
                data[index][5] = simpleTask.getEndHour();
            } else {
                data[index][4] = "-"; // No start hour for complex tasks
                data[index][5] = "-"; // No end hour for complex tasks
            }
            index++;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(this, scrollPane, "Task List", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI mainPage = new GUI();
            mainPage.setVisible(true);
        });
    }
}

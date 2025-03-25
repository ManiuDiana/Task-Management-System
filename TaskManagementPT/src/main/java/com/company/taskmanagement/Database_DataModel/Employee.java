package com.company.taskmanagement.Database_DataModel;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable{
    /*The JVM associates a version (long) number
     with each serializable class. We use it to verify
     that the saved and loaded objects have the same attributes,
     and thus are compatible on serialization.
     */
    private static final long serialVersionUID = 1L; // ensure compatibility across versions (do i really need it??)

    //private so we ensure encapsulation
    private int idEmployee;
    private String Name;

    //the constructor
    public Employee(int idEmployee, String Name) {
        this.idEmployee = idEmployee;
        this.Name = Name;
    }

    //the getters
    public int getIdEmployee() {return idEmployee;}
    public String getName() {return Name;}

    ///Equal two employee objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return idEmployee == employee.idEmployee && Name.equals(employee.Name);
    }

    @Override
    public String toString() {
        return Name;
    }

}

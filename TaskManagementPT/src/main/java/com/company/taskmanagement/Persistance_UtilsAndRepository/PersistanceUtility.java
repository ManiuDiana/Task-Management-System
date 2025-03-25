package com.company.taskmanagement.Persistance_UtilsAndRepository;

import java.io.*;
import java.util.List;
import java.util.Map;

public class PersistanceUtility {

    //Save data to a file
    //the object can be anything; I assume its type is determined by how the method is called in the business layer
    public static void saveData(Object data, String fileName) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
            System.out.println("Data saved successfully to: " + fileName);
        } catch (IOException e) {
            System.err.println("Error while saving data to " + fileName);
        }
    }

    //Load data from file
    public static Object loadData(String fileName) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while loading data from " + fileName);
            return null;
        }

    }
}

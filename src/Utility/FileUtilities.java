/*
* Authors: Raiyan Islam and Ahnaf Masud
*
* Description:
* Contains File Reading/Writing utility functions that makes it simple
*
*  */

package Utility;

import java.io.*;

public class FileUtilities {
    /**
     * SaveToFile accepts an object and the file to save it to
     *
     * @param object The Object to save
     * @param file The file to save to
     *
     */
    public static <T> void saveToFile(T object, File file) {
        // Creating a FileOutputStream and ObjectOutputStream
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(object);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * loadFromFile accepts a file to read form
     *
     * @param file The file to read from
     *
     * @return The Object read from the class
     */
    public static Object loadFromFile(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                return objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

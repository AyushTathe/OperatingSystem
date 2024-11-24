package org.osproject;


public class Main {
    public static void main(String[] args) {
        System.out.println("Working Directory: " + System.getProperty("user.dir"));

        try {
            OS os = new OS("Input.txt", "Output.txt");
            os.LOAD();
        } catch (Exception e) {
            System.err.println("Error initializing the OS: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/*
Syed Raza
HW 2 - Writing and Reading Files
 */

package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // write your code here
        // create a new object for the myStudent class with some default variables
        myStudent newStudent = new myStudent("Default Name","Default Year",0.0);

        // call printMenu function and set student details
        int choice;
        do {
            choice = printMenu();
            switch (choice) {
                case 1:
                    newStudent.enterStudentName();
                    break;
                case 2:
                    newStudent.enterStudentYear();
                    break;
                case 3:
                    newStudent.enterStudentGPA();
                    break;
                case 4:
                    {
                    clearTer();
                    newStudent.displayStudentInfo(); }
                    break;
                case 5:
                    newStudent.writeToFile();
                    break;
                case 6:
                    {
                    clearTer();
                    newStudent.readFromFile(); }
            }
        }while (choice != 7);
        System.out.println("You have exited the program.");


    }

    //myStudent class
    public static class myStudent{
        String studentName;
        String academicYear;
        Double studentGPA;

        // Constructor
        public myStudent(String name, String year, Double GPA){
            this.studentName = name;
            this.academicYear = year;
            this.studentGPA = GPA;
        }


        public void enterStudentName(){
            Scanner myScanner = new Scanner(System.in);
            System.out.print("Enter the Student Name: ");
            String name = myScanner.nextLine();
            studentName = name;
            System.out.println("***Student Name Changed to: " + name + "***");


        }
        public void enterStudentYear(){
            Scanner myScanner = new Scanner(System.in);
            String year = null;
            boolean valid = false;

            while(!valid){

                System.out.print("Please Enter Student Year:");
                year = myScanner.next();
                if(year.equals("Freshman") || year.equals("Sophomore") ||year.equals("Junior") || year.equals("Senior")){
                    valid = true;
                }else{
                    System.out.println("Student Year Must be either: Freshman, Sophomore, Junior, Senior");
                    System.out.println("Note: It is case sensitive");
                }
            }

            academicYear = year;
            System.out.println("***Academic Year Changed to: " + year + "***");
        }
        public void enterStudentGPA(){
            Scanner myScanner = new Scanner(System.in);
            Double GPA = -1.5;

            do{
                try{
                    System.out.print("Enter the Student GPA(0.0 - 4.0): ");
                    GPA = myScanner.nextDouble();

                }catch (InputMismatchException e) {
                    System.out.println("GPA must be between 0.0 and 4.0");
                }
                myScanner.nextLine();
            }while(GPA <0.0 || GPA > 4.0);
            studentGPA = GPA;
            System.out.println("***Student GPA Changed to: " + GPA + "***");

        }
        public void displayStudentInfo(){
            if(checkIfFull()) {
                System.out.println("\n");
                System.out.println("*** DISPLAYING STUDENT INFORMATION ***");
                System.out.println("The Students Name: " + studentName);
                System.out.println("The Students Academic Year: " + academicYear);
                System.out.println("The Students GPA: " + studentGPA);
                System.out.print("**************************************");
            }
        }

        public boolean checkIfFull(){
            int count = 0;
            boolean nameMissing = false;
            boolean yearMissing = false;
            boolean GPAMissing = false;
            if (studentName.equals("Default Name")){
                nameMissing = true;
                count++;

            }
            if (academicYear.equals("Default Year")){
                yearMissing = true;
                count++;
            }
            if (studentGPA==0.0){
                GPAMissing = true;
                count++;
            }

            if(count>0){
                System.out.println("*** Cannot Write/Display Data ***");
                System.out.println("      Must Add The Following:     ");
                if(nameMissing)
                    System.out.println("Please Add Name.");
                if(yearMissing)
                    System.out.println("Please Add Year.");
                if(GPAMissing)
                    System.out.println("Please Add GPA.");
            }
            if(count==0)
                return true;
            else
                return false;
        }
        public void writeToFile(){
            if(!checkIfFull())
                return;
            // create file
            try{
                File studentFile = new File("StudentFile.txt");
                if(studentFile.createNewFile()){
                    System.out.println("File created: "+studentFile.getName());
                }else{
                    System.out.println("StudentFile.txt File exists.");
                }
            }catch (IOException e){
                System.out.println("An Error Occurred.");
                e.printStackTrace();
            }
            // write to file
            try{
                FileWriter myWriter = new FileWriter("StudentFile.txt");
                myWriter.write(
                        "*** Student Information ***\n"+
                                "Student Name: "+studentName+"\n"+
                                "Student Academic Year: "+academicYear+"\n"+
                                "Student GPA: "+studentGPA+"\n"
                );
                myWriter.close();
                System.out.printf("Successfully wrote to the file.");
            }catch (IOException e){
                System.out.printf("An Error Occurred.");
                e.printStackTrace();
            }
        }
        public void readFromFile(){
            Scanner myScanner = new Scanner(System.in);
            //extra credit portion
            System.out.println("Do you want to enter a custom path or use default?");
            System.out.printf("Enter Yes for custom, anything else for default: ");
            String answer = myScanner.next();
            if(answer.toUpperCase().equals("YES")){
                System.out.printf("Enter custom path:");
                String path = myScanner.next();
                try{
                    File myObj = new File(path);
                    Scanner myReader = new Scanner(myObj);
                    while(myReader.hasNextLine()){
                        String data = myReader.nextLine();
                        System.out.println(data);
                    }
                    myReader.close();
                }catch (FileNotFoundException e){
                    System.out.printf("An Error Occurred.");
                }
            }
            else{

                try {
                    File myObj = new File("StudentFile.txt");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        System.out.println(data);
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.printf("An Error Occurred.");
                    e.printStackTrace();
                }
            }
        }

    }
    private static int printMenu(){
        Scanner myScanner = new Scanner(System.in);
        int value = 0;
        System.out.println("\n");
        System.out.println(" 1. Enter Student Name:");
        System.out.println(" 2. Enter Students Academic Year:");
        System.out.println(" 3. Enter Students GPA:");
        System.out.println(" 4. Print Current Student.");
        System.out.println(" 5. Write Data To File.");
        System.out.println(" 6. Read Data From File.");
        System.out.println(" 7. Exit ");


        //take in only 1 - 7 as input
        do {
            try {
                System.out.print("Please enter which number you want to answer:");
                value = myScanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("ERROR! ");
            }
            myScanner.nextLine();
        }while(value <= 0 || value > 7);

        System.out.printf("\n");

        return(value);
    }

    //clear terminal function
    public static void clearTer(){
        try{
            if(System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }catch(IOException | InterruptedException ex){}
    }



}

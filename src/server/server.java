package server;

import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class server {

    private static ArrayList<Question> questions;
    public static final String SEPARADOR = ";";

    private static Registry startRegistry(Integer port) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(port);
            registry.list();

            return registry;
        } catch (RemoteException ex) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located ");
            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("RMI registry created at port ");
            return registry;
        }
    }

    public static void main(String args[]) throws IOException, AlreadyBoundException, InterruptedException {
        if (args.length != 2) {
            System.out.println("---------------------------------------------------------");
            System.out.println("Please pass two arguments: <inputFile> <outputFile>\n");
            System.out.println("In the <inputFile> field choose between: \n- 'questions' \n- 'football'\n- 'culture'");
            System.out.println("In the <outputFile> field put whatever name you want");
            return;
        }
        Registry registry = startRegistry(1099);
        questions = readQuestions(args[0]);
        ExamImpl obj = new ExamImpl(questions);
        registry.bind("Hello", obj);
        System.out.println("Server ready!!");
        System.out.println("\n");
        startExam(obj);
        finishExam(obj);
        outputExam(obj,args[1]);
    }

    private static void startExam(ExamImpl obj) throws IOException {
        boolean correct_answer = false;
        while (correct_answer == false) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Write 'start' to start the exam for all students in the room: ");
            String is_start = scanner.nextLine();
            if (is_start.equals("start")) {
                obj.startExam();
                correct_answer = true;
            } else {
                System.err.println("This is not the correct command, try again");
            }
        }
    }

    private static void finishExam(ExamImpl obj) throws IOException {
        boolean correct_answer = false;
        while (correct_answer == false) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Write 'finish' to end the exam for all students: ");
            String is_start = scanner.nextLine();
            if (is_start.equals("finish")) {
                obj.finishExam();
                correct_answer = true;
            } else {
                System.err.println("This is not the correct command, try again");
            }
        }

    }
    // IMPLEMENTAR
    private static void outputExam(ExamImpl obj, String filename) {
        System.out.println("Saving students score to the output file...");
    }

    private static ArrayList<Question> readQuestions(String namefile) throws IOException {
        BufferedReader bufferLectura = null;
        ArrayList<Question> all_questions = new ArrayList<>();
        try {
            // Mirar de arreglar manera de ficar el path
            bufferLectura = new BufferedReader(new FileReader("src/"+namefile+".csv"));
            String linea = bufferLectura.readLine();
            System.out.println("Reading " +namefile+".csv file...");
            while (linea != null) {
                List<String> possible_responses = new ArrayList<>();
                String[] campos = linea.split(SEPARADOR);

                for(int i=1;i<campos.length-1;i++){
                    possible_responses.add(campos[i]);
                }
                //System.out.println(possible_responses);
                all_questions.add(new Question(campos[0],possible_responses,campos[campos.length-1]));
                //System.out.println(Arrays.toString(campos));
                linea = bufferLectura.readLine();

            }
        } catch (IOException e) {
            System.out.println(e);
            //e.printStackTrace();
        } finally {

            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return all_questions;
    }
}
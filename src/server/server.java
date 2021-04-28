package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;

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

    public static void main(String args[]) throws IOException, AlreadyBoundException {
        Registry registry = startRegistry(1099);
        questions = readQuestions();
        ExamImpl obj = new ExamImpl(questions);
        registry.bind("Hello", obj);
        System.out.println("Server ready!!");

    }

    private static ArrayList<Question> readQuestions() throws IOException {
        BufferedReader bufferLectura = null;
        ArrayList<Question> all_questions = new ArrayList<>();
        try {
            // Mirar de arreglar manera de ficar el path
            bufferLectura = new BufferedReader(new FileReader("src/questions.csv"));
            String linea = bufferLectura.readLine();

            while (linea != null) {
                String[] campos = linea.split(SEPARADOR);
                all_questions.add(new Question(campos[0],campos[campos.length-1]));

                System.out.println(Arrays.toString(campos));
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
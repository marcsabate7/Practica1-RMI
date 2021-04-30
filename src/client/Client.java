package client;

import common.ExamStatusServer;
import common.StatusClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import static java.lang.Thread.sleep;


public class Client {
    private static Registry registry;
    private static ExamStatusImpl exam_status_impl;

    public static void main(String[] args) {
        try {
            if (exam_status_impl== null) {
                exam_status_impl = new ExamStatusImpl();
            }
            if (registry == null){
                registry = LocateRegistry.getRegistry();
            }
            StatusClient status_client = (StatusClient) registry.lookup("Hello");
            String idStudent = startExam(status_client,exam_status_impl);
            if (idStudent!=null) {
                answerExam(status_client,exam_status_impl,idStudent);
                sleep(5);
                finishExam();
            }
        }
        catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); e.printStackTrace();
        }
    }

    private static void finishExam() {
        System.out.println("The exam has finished, you have a total score of " +exam_status_impl.getCorrectAnswers()+"/"+exam_status_impl.getTotalQuestions());
    }

    private static String startExam(StatusClient status_client,ExamStatusImpl exam_status_impl) throws IOException, InterruptedException {
        String idstudent;
        boolean sortir_totalment = false;
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Put your ID before enter to the room exam: ");
            idstudent = scanner.nextLine();
            status_client.newSession(idstudent, exam_status_impl);
            if(exam_status_impl.isStarted()){
                System.err.println("The exam have been started so you can't enter now, sorry!");
                sortir_totalment = true;
                break;
            }
            if (exam_status_impl.isSameUser()) {
                System.err.println("Another student is registered with the same ID, try again with another...");
            }
            else{
                break;
            }
        }
        if (sortir_totalment == false) {
            synchronized (exam_status_impl) {
                System.out.println("Waiting for the professor to start the exam...");
                while (!exam_status_impl.isStartExam()) {
                    exam_status_impl.wait(1000);    // Checks every second if the server has responded.
                }
            }
            return idstudent;
        }
        return null;
    }

    private static void answerExam(StatusClient status_client, ExamStatusImpl exam_status_impl, String idStudent) throws RemoteException {
        System.out.println("\nWelcome "+idStudent+", the exam has started!!");
        Integer cont_questions = 0;
        while(status_client.hasNext(idStudent) && !exam_status_impl.isExamFinished()){
            cont_questions+=1;
            questionAnswer(status_client,idStudent,cont_questions);
        }
    }

    private static void questionAnswer(StatusClient status_client, String idStudent,Integer cont_questions) throws RemoteException {
        String question = status_client.next(idStudent);
        System.out.println("\nQUESTION "+cont_questions+": ");
        System.out.println(question);
        Integer number = null;
        // Mirar de implemnentar per fer el finish si el client esta pensant
        while(number == null){
            try {
                //System.out.println("HOLA");
                Scanner scanner = new Scanner(System.in);
                number = scanner.nextInt();

            } catch (Exception e) {}
            if(number == null){
                System.err.println("\nThe answer have to be a number!!");
            }
        }
        status_client.answerQuestion(idStudent, number);
    }

}

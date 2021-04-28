package server;

import common.ExamStatusServer;
import common.StatusClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ExamImpl extends UnicastRemoteObject implements StatusClient {

    private boolean examStarted = false;
    private boolean examFinished = false;
    private final HashMap<String, User> users;
    private ArrayList<String> questions;
    private final HashMap<String, ExamStatusServer> clients;


    public ExamImpl(ArrayList<String> questions) throws RemoteException {
        this.questions = questions;
        this.users = new HashMap<>();
        this.clients = new HashMap<>();
        this.examStarted = false;
        this.examFinished = false;
    }


    @Override
    public void newSession(String idStudent, ExamStatusServer client) throws RemoteException {
        if (examStarted == false) {
            synchronized (users) {
                users.put(idStudent, new User());
            }
            synchronized (clients) {
                clients.put(idStudent, client);
                System.out.println("New student have been registered to the room:\nTotal: " + clients.size() + " students");
            }
        }
        else{
            // Mirar de pasarho al client
            System.out.println("Exam has started, you can't enter to the room!");
        }
    }

    @Override
    public void answerQuestion(String idStudent, Integer answer) throws RemoteException {

    }

    @Override
    public boolean hasNext(String idStudent) throws RemoteException {
        User user_session = users.get(idStudent);
        Integer answer = user_session.getActualQuestion();
        if (examFinished == false)
            return false;
        return true;
    }

    @Override
    public String next(String idStudent) throws RemoteException {
        return null;
    }
}

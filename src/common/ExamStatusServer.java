package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ExamStatusServer extends Remote {
    void finishExam(Integer correctAnswer, Integer totalQuestions) throws RemoteException;
    void startExam() throws RemoteException;
    void sameUser(String message) throws RemoteException;
    void examHasStarted(String message) throws RemoteException;
}

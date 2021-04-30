package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StatusClient extends Remote {
    void newSession(String idStudent, ExamStatusServer client) throws RemoteException;
    void answerQuestion(String idStudent, Integer answer) throws  RemoteException;
    boolean hasNext(String idStudent) throws RemoteException;
    String next(String idStudent) throws RemoteException;
}

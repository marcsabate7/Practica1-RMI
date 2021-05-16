/*
*
*   We control the comprovations of the student with this class, if the exam has finished / started etc.
*
* */


package client;

import common.ExamStatusServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ExamStatusImpl extends UnicastRemoteObject implements ExamStatusServer {
    private boolean examFinished = false;
    private boolean startExam = false;
    private Integer correctAnswer;
    private Integer totalQuestions;
    private String message_same_user = null;
    private String message_has_started = null;

    protected ExamStatusImpl() throws RemoteException {
        super();
    }

    @Override
    public void finishExam(Integer correctAnswer, Integer totalQuestions) throws RemoteException {
        this.examFinished = true;
        this.correctAnswer = correctAnswer;
        this.totalQuestions = totalQuestions;
    }

    @Override
    public void startExam() throws RemoteException {
        startExam = true;
    }

    @Override
    public void sameUser(String message) throws RemoteException {
        this.message_same_user = message;
    }

    @Override
    public void examHasStarted(String message) throws RemoteException {
        this.message_has_started = message;
    }

    public boolean isStarted() {
        if (this.message_has_started != null) {
            this.message_has_started = null;
            return true;
        }
        return false;
    }

    public boolean isSameUser() {
        if (this.message_same_user != null) {
            this.message_same_user = null;
            return true;
        }
        return false;
    }

    public boolean isStartExam() {
        return startExam;
    }

    public boolean isExamFinished() {
        return examFinished;
    }

    public Integer getCorrectAnswers() {
        return correctAnswer;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }
}

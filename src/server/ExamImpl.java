package server;

import common.ExamStatusServer;
import common.StatusClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ExamImpl extends UnicastRemoteObject implements StatusClient {

    private boolean examStarted = false;
    private boolean examFinished = false;
    private final HashMap<String, StudentComput> comput;
    private ArrayList<Question> questions;
    private final HashMap<String, ExamStatusServer> clients;


    public ExamImpl(ArrayList<Question> questions) throws RemoteException {
        this.questions = questions;
        this.comput = new HashMap<>();
        this.clients = new HashMap<>();
        this.examStarted = false;
        this.examFinished = false;
    }

    //FALTA COMPROBAR QUE NO HI HAGI EL MATEIX IDENTIFICADOR A LA SESSIÓ
    @Override
    public void newSession(String idStudent, ExamStatusServer client) throws RemoteException {
        if (!examStarted) {
            synchronized (comput) {
                comput.put(idStudent, new StudentComput());
            }
            synchronized (clients) {
                clients.put(idStudent, client);
                System.out.println("---------------------------------------------");
                System.out.println("New student have been registered to the room!\nTotal students: " + clients.size());
            }
        } else {
            // Mirar de pasarho al client
            System.out.println("Exam has started, you can't enter to the room!");
        }
    }

    @Override
    public void answerQuestion(String idStudent, Integer studentAnswer) throws RemoteException {
        /*Integer student_answer2=0;
        Integer currentQuestion = comput.get(idStudent).getCurrentQuestion();
        try{
            student_answer2 = Integer.parseInt(studentAnswer);
            Integer correctAnswer = questions.get(currentQuestion).getAnswer();
            if (student_answer2.equals(correctAnswer)) {
                comput.get(idStudent).nextQuestionCorrect();
            } else {
                comput.get(idStudent).nextQuestion();
            }
        }
        catch(Exception e){
            System.err.println(e);
        }*/

        Integer currentQuestion = comput.get(idStudent).getCurrentQuestion();
        Integer correctAnswer = questions.get(currentQuestion).getAnswer();
        if (studentAnswer.equals(correctAnswer)) {
            comput.get(idStudent).nextQuestionCorrect();
        } else {
            comput.get(idStudent).nextQuestion();
        }
    }

    @Override
    public boolean hasNext(String idStudent) throws RemoteException {
        StudentComput user_session = comput.get(idStudent);
        Integer question = user_session.getCurrentQuestion();
        boolean result = !examFinished && question < questions.size();
        if (!result)
            studentEndExam(idStudent);
        return result;
    }

    public void studentEndExam(String idStudent) throws RemoteException {
        Integer score = comput.get(idStudent).getCorrectAnswers();
        clients.get(idStudent).finishExam(score, questions.size());
    }


    @Override
    public String next(String idStudent) throws RemoteException {
        Integer num_question = 0;
        if (hasNext(idStudent)) {
            num_question = comput.get(idStudent).getCurrentQuestion();
            return questions.get(num_question).presentPossibleAnswers();
        }
        return null;
    }

    public void startExam() throws RemoteException {
        System.out.println("Exam started!");
        setExamStarted();
        Set<String> idStudents = clients.keySet();
        for (String idStudent : idStudents) {
            clients.get(idStudent).startExam();
        }
    }

    protected void finishExam() throws RemoteException {
        System.out.println("S'ha finalitzat el examen");
        setExamFinished();
        Set<String> idStudents = clients.keySet();
        for (String idStudent : idStudents) {
            studentEndExam(idStudent);
        }
    }

    public void setExamStarted() {
        this.examStarted = true;
    }

    public void setExamFinished() {
        this.examFinished = true;
    }
}
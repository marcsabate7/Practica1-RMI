package server;

import java.util.Objects;

public class User {
    private final Integer correctAnswers;
    private final Integer currentQuestion;
    private String idStudent;

    public User(String idStudent, Integer correctAnswers, Integer currentQuestion) {
        this.idStudent = idStudent;
        this.correctAnswers = correctAnswers;
        this.currentQuestion = currentQuestion;
    }

    public User(String idStudent) {
        this.idStudent = idStudent;
        this.correctAnswers = 0;
        this.currentQuestion = 0;
    }

    public User nextQuestionCorrect() {
        return new User(correctAnswers + 1, actualQuestion + 1);
    }

    public User nextQuestion() {
        return new User(correctAnswers, actualQuestion + 1);
    }

    public Integer getCurrentQuestion() {
        return actualQuestion;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }
}

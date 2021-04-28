package server;

import java.util.Objects;

public class User {
    private final Integer correctAnswers;
    private final Integer actualQuestion;

    public User(Integer correctAnswers, Integer actualQuestion) {
        this.correctAnswers = correctAnswers;
        this.actualQuestion = actualQuestion;
    }

    public User() {
        this(0, 0);
    }

    public User nextQuestionCorrect() {
        return new User(correctAnswers + 1, actualQuestion + 1);
    }

    public User nextQuestion() {
        return new User(correctAnswers, actualQuestion + 1);
    }

    public Integer getActualQuestion() {
        return actualQuestion;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }
}

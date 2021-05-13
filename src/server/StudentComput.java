package server;


public class StudentComput {

    private int correctAnswers;
    private int currentQuestion;

    public StudentComput(int correctAnswers, int currentQuestion) {

        this.correctAnswers = correctAnswers;
        this.currentQuestion = currentQuestion;
    }

    public StudentComput() {
        this.correctAnswers = 0;
        this.currentQuestion = 0;
    }
    public void nextToFinish(){

    }

    public void nextQuestionCorrect() {
        this.correctAnswers += 1;
        this.currentQuestion += 1;
    }

    public void nextQuestion() {
        this.currentQuestion += 1;
    }

    public Integer getCurrentQuestion() {
        return currentQuestion;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }
}

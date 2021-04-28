package server;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String[] question;
    private List<String> possible_answers;


    public Question(String question, List<String> possible_answers, String answer) {
        this.question = new String[]{question, answer};
        this.possible_answers = possible_answers;
    }

    public Integer getAnswer() {
        return Integer.parseInt(this.question[1]);
    }

    public String getQuestion() {
        return this.question[0];
    }
    public List<String> getPossibleAnswers() {
        return this.possible_answers;
    }
    public String presentPossibleAnswers() {
        String presentation = this.question[0];
        for (int i = 0; i < possible_answers.size(); i++) {
            presentation+=("\n" + "\t"+(i+1)+". " +possible_answers.get(i));
        }
        return presentation;
    }
}


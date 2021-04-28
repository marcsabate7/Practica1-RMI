package server;

public class Question {

    private String [] question;


    public Question(String question, String answer){
        this.question = new String[]{question,answer};
    }
    public Integer getAnswer(){
        return Integer.parseInt(this.question[1]);
    }
    public String getQuestion(){
        return this.question[0];
    }
}


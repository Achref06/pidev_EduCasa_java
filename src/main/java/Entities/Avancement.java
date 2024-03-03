package Entities;

public class Avancement{
    private int id;
    private int idquiz;
    private int noteQuiz;

    private int idquestion;

    public Avancement() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdquiz() {
        return idquiz;
    }

    public void setIdquiz(int idquiz) {
        this.idquiz = idquiz;
    }

    public int getNoteQuiz() {
        return noteQuiz;
    }

    public void setNoteQuiz(int noteQuiz) {
        this.noteQuiz = noteQuiz;
    }

    public int getIdquestion() {
        return idquestion;
    }

    public void setIdquestion(int idquestion) {
        this.idquestion = idquestion;
    }

    public Avancement(int id, int idquiz, int idquestion, int noteQuiz) {
        this.id = id;
        this.idquiz = idquiz;
        this.idquestion=idquestion;
        this.noteQuiz = noteQuiz;
    }
}

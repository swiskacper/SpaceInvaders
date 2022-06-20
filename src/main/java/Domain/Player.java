package Domain;

public class Player {


    private String name;
    private String id;
    private String score;

    public Player(String id, String name,String score) {
        this.name = name;
        this.id = id;
        this.score = score;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }
}

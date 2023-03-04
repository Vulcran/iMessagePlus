public class Message {
    private String recipiant;
    private String content;
    boolean mode;
    int pace;
    private Thread curThread;
    private boolean done;

    public String getRecipiant() {
        return recipiant;
    }

    public String getContent() {
        return content;
    }

    public boolean isMode() {
        return mode;
    }

    public int getPace() {
        return pace;
    }

    public Thread getCurThread() {
        return curThread;
    }

    public Message(String content, String recipiant, boolean mode, int pace, Thread curThread) {
        this.recipiant = recipiant;
        this.content = content;
        this.mode = mode;
        this.pace = pace;
        this.curThread = curThread;
        done = false;
    }
    public void finished(){
        done=true;
    }
    public boolean equals(Message other){
        return
            this.recipiant.equals(other.recipiant) &
            this.content.equals(other.content) &
            this.mode == other.mode &
            this.pace == other.pace &
            this.curThread == other.curThread;
    }
}

package model;

public class AudioBook extends Book{

    private int runTime;

    public int getFormat() {
        return runTime;
    }

    public void setRunTime(int runTime){
        this.runTime = runTime;
    }

    public String toString(){
        return super.toString() + String.format(" | Run Time: %d", this.runTime);
    }
}

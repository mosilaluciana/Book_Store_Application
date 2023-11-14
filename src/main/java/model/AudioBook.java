package model;

public class AudioBook extends Book{

    public int runTime;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format){
        this.format=format;
    }

    public String toString(){
        return super.toString()+String.format(" | Format %s", this.format);
    }
}

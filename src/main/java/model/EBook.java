package model;

public class EBook extends Book{

    private String format;

    public void setFormat(String format){
        this.format = format;
    }

    public String getFormat(){
        return  this.format;
    }

    public String toString(){
        return super.toString() + String.format(" | Format: %d", this.format);
    }

}

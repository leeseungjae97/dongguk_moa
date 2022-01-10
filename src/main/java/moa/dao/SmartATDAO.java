package moa.dao;

public class SmartATDAO {
    String green;
    String red;
    String blue;
    String gray1;
    String gray2;

    public SmartATDAO(String green, String red, String blue, String gray1, String gray2) {
        this.green = green;
        this.red = red;
        this.blue = blue;
        this.gray1 = gray1;
        this.gray2 = gray2;
    }

    @Override
    public String toString() {
        return "SmartATDAO{" +
                "green='" + green + '\'' +
                ", red='" + red + '\'' +
                ", blue='" + blue + '\'' +
                ", gray1='" + gray1 + '\'' +
                ", gray2='" + gray2 + '\'' +
                '}';
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public String getGray1() {
        return gray1;
    }

    public void setGray1(String gray1) {
        this.gray1 = gray1;
    }

    public String getGray2() {
        return gray2;
    }

    public void setGray2(String gray2) {
        this.gray2 = gray2;
    }
}

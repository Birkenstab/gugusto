package util;

public class Size {

    private double width;
    private double height;

    public Size(){
        width = 0;
        height = 0;
    }

    public Size(double width, double height){
        this.width = width;
        this.height = height;
    }

    public Size(Vector v){
        width = v.getX();
        height = v.getY();
    }

    public void set(double width, double height){
        this.width = width;
        this.height = height;
    }

    public void set(Size size){
        width = size.width;
        height = size.height;
    }

    public void setWidth(double width){
        this.width = width;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public Vector toVector() {
        return new Vector(width, height);
    }

    @Override
    public String toString(){
        return String.format("Width: %.4f, Height: %.4f", width, height);
    }

}

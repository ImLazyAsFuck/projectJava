package ra.edu.business.model;

public class LengthContain{
    private int minLength;
    private int maxLength;

    public int getMinLength(){
        return minLength;
    }

    public int getMaxLength(){
        return maxLength;
    }

    public LengthContain(int minLength, int maxLength){
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
}

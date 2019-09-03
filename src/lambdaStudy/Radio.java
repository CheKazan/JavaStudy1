package lambdaStudy;

public class Radio implements ElectricityInterface {
    @Override
    public void electricityOn() {
        System.out.println("Radio is on");
    }
}

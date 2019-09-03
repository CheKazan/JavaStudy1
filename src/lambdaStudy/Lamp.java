package lambdaStudy;

public class Lamp implements ElectricityInterface {
    @Override
    public void electricityOn() {
        System.out.println("Lamp is on");
    }
}

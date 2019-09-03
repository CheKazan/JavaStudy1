package lambdaStudy;

public class main
{
    public static void testmethod(int x)
    {
        System.out.println("вызов метода с точным набором параметров как улямбды" +x);
    }
    public static void main(String[] args) {
        SwitcherButton sw= new SwitcherButton();
        sw.addListener(new Lamp());
        sw.addListener(new Radio());
        sw.addListener(new ElectricityInterface() {
            @Override
            public void electricityOn() {
                System.out.println("Включаем анонимный источник ");
            }
        });
        sw.addListener(()->System.out.println("Включаем лямбда источник"));
        sw.switchOn();

        int x=5;
        SwitcherButton2 sw2= new SwitcherButton2();
        sw2.addListener(x2-> System.out.println("лябмда с параметром - " + x2));

       // sw2.addListener(x3->main.testmethod(x3));
        sw2.addListener(main::testmethod);
        sw2.switchOn(x);
    }
}

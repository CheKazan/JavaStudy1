package lambdaStudy;

import java.util.ArrayList;

public class SwitcherButton2 {
    private ArrayList<ElectricityInterface2> listeners = new ArrayList<>();
    public void addListener(ElectricityInterface2 listener)
    {
        listeners.add(listener);
    }
    public void removeListener(ElectricityInterface2 listener)
    {
        listeners.remove(listener);
    }
    public void switchOn(int x)
    {
        System.out.println("Нажали кнопку выключателя");
        for (ElectricityInterface2 e:listeners)
        {
          e.electricityOn(x);
        }
    }

}

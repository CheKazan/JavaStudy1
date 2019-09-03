package lambdaStudy;

import java.util.ArrayList;

public class SwitcherButton {
    private ArrayList<ElectricityInterface> listeners = new ArrayList<>();
    public void addListener(ElectricityInterface listener)
    {
        listeners.add(listener);
    }
    public void removeListener(ElectricityInterface listener)
    {
        listeners.remove(listener);
    }
    public void switchOn()
    {
        System.out.println("Нажали кнопку выключателя");
        for (ElectricityInterface e:listeners)
        {
         e.electricityOn();
        }
    }

}

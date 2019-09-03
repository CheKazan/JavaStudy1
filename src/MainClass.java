import java.util.*;

public class MainClass {
    public static int binarycount=0;
    public static void main(String[] args) throws Exception
    {
       // binaresearchController();
       // recursivesumcontroller();
        binarysearchcontroller();

    }
    public static void binaresearchController()
    {
        int[] massiveforsearch=new int[10000000];
        for (int i = 0; i < massiveforsearch.length ; i++)  massiveforsearch[i]=i+1;
        Date dstart=new Date();
        int[] result =binarySearch(massiveforsearch,210);
        //Thread.sleep(100);
        Date dstop=new Date();
        System.out.println("№ ячейки с искомым значением: "+ result[0] + "   О(log(n)): " + result[1] + " Время выполнения в мсек:" + (dstop.getTime()-dstart.getTime()));

    }

    public static int[] binarySearch(int[] massive, int item)
    {
        // item -значение которое ищем
        int Opcount=0; //количество операции для О большого
        int low=0;
        int hight=massive.length-1;
        int mid;
        int guess=0;// текущее значение
        int[] retvalue=new int[2];
        while(low<=hight)
        {
            mid=(low+hight)/2;
            guess=massive[mid];
            Opcount++;
            if(guess==item) { retvalue[0]=mid; retvalue[1]=Opcount; return retvalue;}
            if(guess>item) hight=mid-1;
            else low=mid+1;
        }
        return null;
    }

    public static void recursivesumcontroller()
    {
        Integer[] arr={2,5,6,7,8,0,10,12,100,150};
        ArrayList<Integer> arrayList= new ArrayList<Integer>(Arrays.asList(arr));
        System.out.println(recursive(arrayList));
    }
    public static int recursive(List<Integer> arr)
    {
        if(arr.size()==1) return arr.get(0);
        else
        {   int i=arr.get(0);
            arr.remove(0);
            return i+recursive(arr);
        }
    }
    public static void binarysearchcontroller()
    {

        //Integer[] arr={2,5,6,7,8,0,10,12,100,150,34,18,46,32,78,11,176,132,348,101,19,13,3};
        //ArrayList<Integer> arrayList= new ArrayList<Integer>(Arrays.asList(arr));
        ArrayList<Integer> arrayList= new ArrayList<Integer>();
        for (int i = 0; i <50 ; i++) {
                arrayList.add(i);
        }
        Collections.shuffle(arrayList);
        System.out.println(arrayList);
        System.out.println(binarysearch(arrayList));

    }
    public static ArrayList<Integer> binarysearch(ArrayList<Integer> arrayList)
    {
        if(arrayList.size()<2) return arrayList;
        else
        {
            //int opornindex=(int) (Math.random()*arrayList.size());
            int opornindex=(int) (arrayList.size()/2);
            int opornpoint= arrayList.get(opornindex);

            ArrayList<Integer> hilist=new ArrayList<Integer>();
            ArrayList<Integer> lowlist=new ArrayList<Integer>();
           // System.out.println(opornpoint +" " + opornindex);
            for(int i=0; i<arrayList.size();i++)
                if(i!=opornindex) {
                    if (arrayList.get(i) < opornpoint) lowlist.add(arrayList.get(i));
                    else hilist.add(arrayList.get(i));
                }
            System.out.println(++binarycount+":   "+lowlist + "<<<"+ opornpoint+">>>>" + hilist);

            ArrayList<Integer> returnint=new ArrayList<>();
            returnint.addAll(binarysearch(lowlist));
            returnint.add(opornpoint);
            returnint.addAll(binarysearch(hilist));
            return returnint;

        }

    }

}

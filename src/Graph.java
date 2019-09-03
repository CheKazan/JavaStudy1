import javax.sound.midi.Soundbank;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class Graph
{
    //ненаправленный граф
//    static int[][] graph= {
//            {0,1,1,0,0,0,0,0},
//            {1,0,0,1,0,1,1,0},
//            {1,0,0,0,0,1,0,0},
//            {0,1,0,0,1,0,0,0},
//            {0,0,0,1,0,1,0,1},
//            {0,1,1,0,1,0,1,1},
//            {0,1,0,0,0,1,0,1},
//            {0,0,0,0,1,1,1,0}
//    };
   // направленный граф
    static int[][] graph= {
            {0,1,1,0,0,0,0,0},
            {0,0,0,1,0,1,1,0},
            {0,0,0,0,0,1,0,0},
            {0,0,0,0,1,0,0,0},
            {0,0,0,0,0,1,0,1},
            {0,0,0,0,0,0,1,1},
            {0,0,0,0,0,1,0,1},
            {0,0,0,0,0,0,0,0}
    };
    //список вершин с указанием посещены ли они и кто их родитель


    public static void main(String[] args)
    {
        //список вершин с указанием посещены ли они и кто их родитель
         ArrayList<Vertex> vertexes= new ArrayList<>();
        // забиваем точки откуда куда ищем путь
        int startvert=3;
        int endvert=7;

        //список вершин с указанием посещены ли они и кто их родитель
        for (int i = 0; i < graph.length ; i++) vertexes.add(new Graph.Vertex(false,i,0));
        vertexes.get(startvert).isVisited=true;
        vertexes.get(startvert).parent=-1;

        //очередь для поиска в ширину. заполняем соседями 1 уровня
        ArrayDeque<Vertex> deque= new ArrayDeque<>();
        for (int i = 0; i < graph.length ; i++)
        {
            if(graph[startvert][i]==1) // все соседи 1 уровня у стартовой вершины
            { deque.add(vertexes.get(i));
               vertexes.get(i).parent=startvert;
            }
        }
        System.out.println("------------");
        //for(Vertex v:deque) System.out.println(v.number +"/"+ v.isVisited+"/" + v.parent);

        //алгоритм поиска пути
        while (!deque.isEmpty())
        {
            // взяли вершину из очереди
            Vertex currv=deque.pop();
            System.out.printf("Взяли вершину %d из очереди %n",currv.number+1);
            if(!currv.isVisited)
            {
                //если вершина не та, добавляем всех ее соседей в очередь
                if (currv.number != endvert)
                {
                    for (int i = 0; i < graph.length; i++)
                    {
                        if (graph[currv.number][i] == 1)// все соседи 1 уровня у текущей вершины
                        {
                            deque.add(vertexes.get(i));
                            if(vertexes.get(i).parent==0)vertexes.get(i).parent=currv.number;// если родителя еще не присваивали
                        }
                    }
                    currv.isVisited=true;
                    System.out.printf("Зашли в вершину %d . Это не искомая вершина. Пошли дальще %n",currv.number+1);
                }
                else
                {
                    System.out.printf("Нашли конец! Вершина %d найдена.Путь есть! %n",currv.number+1);
                    break;
                }
            } else {
                System.out.printf("Зашли в вершину %d . Здесь мы уже были. Пошли дальше %n",currv.number+1);
            }
        }
        //
        System.out.printf("Путь из вершины %d в вершину %d:%n",startvert+1,endvert+1);
        int needeednumber=vertexes.get(endvert).number;
        for (int i = vertexes.size()-1 ; i >=0 ; i--)
        {
            if(vertexes.get(i).number==needeednumber) { System.out.print((vertexes.get(i).number+1)+ "->"); needeednumber=vertexes.get(i).parent;}
        }

        //for(Vertex v:vertexes) System.out.println((v.number+1) +"/"+ v.isVisited+"/" + (v.parent+1));

    }

    public static class  Vertex
    {
        boolean isVisited; //посещена ли вершина
        int number; //номер-название
        int parent; // родитель
        public Vertex(boolean isVisited, int number, int parent) {
            this.isVisited = isVisited;
            this.number = number;
            this.parent = parent;
        }
    }



}

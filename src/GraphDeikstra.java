import java.util.ArrayDeque;
import java.util.ArrayList;

public class GraphDeikstra
{
    static int[][] graph= {
            {-1,5,0,-1,-1,-1,-1,-1},
            {-1,-1,-1,6,-1,8,7,-1},
            {-1,-1,-1,-1,-1,3,-1,-1},
            {-1,-1,-1,-1,15,-1,-1,-1},
            {-1,-1,-1,-1,-1,5,-1,3},
            {-1,-1,-1,-1,-1,-1,2,26},
            {-1,-1,-1,-1,-1,1,-1,16},
            {-1,-1,-1,-1,-1,-1,-1,-1}
    };
    // грокаем пример
//    static int[][] graph= {
//            {-1,5,0,-1,-1,-1},
//            {-1,-1,-1,15,20,-1},
//            {-1,-1,-1,30,35,-1},
//            {-1,-1,-1,-1,-1,20},
//            {-1,-1,-1,-1,-1,10},
//            {-1,-1,-1,-1,-1,-1}
//    };
    static ArrayList<Vertex> vertexes= new ArrayList<>();
    public static void main(String[] args)
    {
        // забиваем точки откуда куда ищем путь
        int startvert=0; //-1
        int endvert=7;  //-1

        //список вершин с указанием посещены ли они и кто их родитель, стоимость посещения неизвестна так что бесконечность
        for (int i = 0; i < graph.length ; i++) vertexes.add(new GraphDeikstra.Vertex(false,i,-1,Integer.MAX_VALUE));
        //задаем стартовой вершине что ее мы посмотрели и родителей у нее нет
        vertexes.get(startvert).isVisited=true;
        vertexes.get(startvert).parent=-1;
        vertexes.get(startvert).costs=0;

        //заполняем таблицу вершин начальными значениями и стоимость доступа соседям первого уровня
        for (int i = 0; i < graph.length ; i++)
        {
            if(graph[startvert][i]>=0) // соседи 1 уровня у стартовой вершины
            {
                vertexes.get(i).costs=graph[startvert][i];
                vertexes.get(i).parent=startvert;
            }
        }
        // выводим текущее состояние таблицы
//        System.out.println("Вершина|Посещена?|Стоимость|Родитель");
//        for(Vertex v:vertexes)
//        {
//            if(v.costs!=Integer.MAX_VALUE) System.out.printf("%7d|%9b|%9d|%8d%n",v.number,v.isVisited,v.costs,v.parent);
//            else System.out.printf("%7d|%9b| Infinity|%n",v.number,v.isVisited);
//        }

        //алгоритм поиска пути
        Vertex curr_vertex= find_lowest_costs_vertex(); // прошли по скиску выбрали непосещенную вершину с наименьшей стоимостью доступа
        // смотрим всех соседей выбранной вершины и обновляем стоимости если они меньше через эту вершину
        while(curr_vertex!=null) //по сути на вернется нулл когда все вершины посетяться
        {
            for (int i = 0; i < graph.length; i++) {
                if (graph[curr_vertex.number][i] >= 0) // если есть ребро и сосед
                {
                    //если (стоимость доступа к текущей вершине + стоимость ребра) дешевле
                    // чем старая стоимость доступа к соседу  обновим его стоимость и его родителя
                    if ((curr_vertex.costs + graph[curr_vertex.number][i]) < vertexes.get(i).costs) {
                        vertexes.get(i).costs = curr_vertex.costs + graph[curr_vertex.number][i];
                        vertexes.get(i).parent = curr_vertex.number;
                    }
                }
            }
            curr_vertex.isVisited=true;
            curr_vertex=find_lowest_costs_vertex();
        }

        System.out.println("Вершина|Посещена?|Стоимость|Родитель");
        for(Vertex v:vertexes)
        {
            if(v.costs!=Integer.MAX_VALUE) System.out.printf("%7d|%9b|%9d|%8d%n",v.number+1,v.isVisited,v.costs,v.parent+1);
            else System.out.printf("%7d|%9b| Infinity|%n",v.number+1,v.isVisited);
        }
    }
    public static Vertex find_lowest_costs_vertex() //ищем в списке вершин вершину с наименьшей ценой не посещеную
    {
        int lowest_costs=Integer.MAX_VALUE;
        Vertex lowest_costs_vertex=null;
        for(Vertex v: vertexes)
        {
            if(!v.isVisited && v.costs<lowest_costs)
            {
                lowest_costs=v.costs;
                lowest_costs_vertex=v;
            }
        }
        return lowest_costs_vertex;
    }

    public static class  Vertex
    {
        boolean isVisited; //посещена ли вершина
        int number; //номер-название
        int parent; // родитель
        int costs; //сумма доступа

        public Vertex(boolean isVisited, int number, int parent, int costs) {
            this.isVisited = isVisited;
            this.number = number;
            this.parent = parent;
            this.costs = costs;
        }
    }




}

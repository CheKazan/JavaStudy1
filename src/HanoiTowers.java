public class HanoiTowers {

        public static void main(String[] args) {
            int numRings = 3;
            moveRing('A', 'B', 'C', numRings);
        }

        public static void moveRing(char a, char b, char c, int numRings) {
            //напишите тут ваш код
            if(numRings==0) return;
            moveRing(a, c, b, numRings-1);
            System.out.println(String.format("from %c to %c",a,b));
            moveRing(c, b, a, numRings-1 );
        }
    }

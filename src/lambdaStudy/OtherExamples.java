package lambdaStudy;

import java.util.concurrent.RecursiveTask;

public class OtherExamples {
// многопоточный пример рекурсии перевода в двоизчный

    public class BinaryRepresentationTask extends RecursiveTask<String> {
        private int x;

        public BinaryRepresentationTask(int x) {
            this.x = x;
        }

        @Override
        protected String compute() {
            if(x<=0) return "";
            int a = x % 2;
            int b = x / 2;
            BinaryRepresentationTask task = new BinaryRepresentationTask(b);
            task.fork();

            return task.join()+a;

        }

}}


import java.text.DecimalFormat;

public class Main{
    public static void main(String[] args){

        // task 1
        System.out.println("Task 1");
        long[] task_1_answer = task_1(2, 18);
        for (int i=0; i<task_1_answer.length; i++){
            System.out.print(task_1_answer[i] + " ");
        }
        System.out.println();

        // task 2
        System.out.println("Task 2");
        float[] task_2_answer = task_2(-14.0, 9.0, 16);
        for (int i=0; i<task_2_answer.length; i++){
            System.out.print(task_2_answer[i] + " ");
        }
        System.out.println();

        // task 3
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println("Task 3");
        double[][] task_3_answer = task_3(9, 16, task_1_answer, task_2_answer);
        for (int i=0; i<9; i++){
            for (int j=0; j<16; j++){
                System.out.print(df.format(task_3_answer[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public static long[] task_1(int arg_1, int arg_2){
        // counting length of new array
        int len = (arg_2 - arg_1)/2 + 1;
        // creating array
        long[] even_numbers = new long[len];
        // start even number
        int j = arg_1;
        for(int i = 0; i<len; i++){
            even_numbers[i] = (long)j;
            j += 2;
        }
        return even_numbers;
    }

    public static float[] task_2(double min, double max, int len){

        float[] random_numbers = new float[len];

        for (int i=0; i<len; i++){
            float random_number = (float)Math.floor(Math.random() * (max - min + 1) + min);
            random_numbers[i] = random_number;
        }
        

        return random_numbers;
    }
    
    public static double[][] task_3(int len, int wid, long[] c, float[] x_arr){

        double[][] arr = new double[len][wid];

        for (int i=0; i < len; i++){
            for (int j=0; j < wid; j++){
                float x = x_arr[j];
                
                switch ((int)c[i]){
                    case 12:
                        arr[i][j] = Math.pow((Math.pow((x * Math.pow(x - 0.25, 3)), 
                            Math.pow(x, (x / (3/4 + x)))))*(Math.pow(Math.E, Math.pow(Math.E, x)) - 1), 3);
                    case 6, 8, 10, 18:
                        arr[i][j] = Math.log(Math.pow(3/(Math.pow(((5 + Math.abs(x))/5), x) + 1),2));
                    default:
                        arr[i][j] = Math.cbrt(Math.tan(Math.pow(Math.cos(x), Math.pow(Math.E, x))));  
                }

            }
        }

        return arr;
    }
}

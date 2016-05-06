import java.util.Scanner;

/**
 * Created by Kida on 05.05.2016.
 */
public class Main {

    public static void main(String[] args) {
        double eps = 0, r = 1;

        Scanner in = new Scanner(System.in);
        System.out.println("Введите eps");
        eps = in.nextDouble();
        System.out.println("Введите r");
        r = in.nextDouble();
        System.out.println("Введите стартовую точку u0:");
        double [] u = {in.nextDouble(), in.nextDouble()};
        double[] uMin;
        System.out.println(Double.toString(eps) + Double.toString(u[0])+Double.toString(u[1]) + Double.toString(r));
        uMin = Newton.search(u, eps);
        show("Метод Ньютона:", uMin, Newton.I(uMin));

        uMin = Penalty.search(u, eps, r);
        show("Метод штрафных функций:", uMin, Penalty.I(uMin));

        uMin = GradientDescent.findByStepFractionation(u, eps);
        show("Метод деления отрезка:", uMin, GradientDescent.I(uMin));

        uMin = GradientDescent.findBySteepestDescent(u, eps);
        show("Метод наискорейшего спуска:", uMin, GradientDescent.I(uMin));

    }

    private static void show(String methodName, double[] u, double I) {
        System.out.println(methodName);
        System.out.println("u = ("+u[0]+" , "+ u[1]+")");
        System.out.println("I(u) = "+I);
    }


    private static void getData(double eps, double[] u, double r) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите eps");
        eps = in.nextDouble();
        System.out.println("Введите r");
        r = in.nextDouble();
        System.out.println("Введите стартовую точку u0:");
        double[] u1 = {in.nextDouble(), in.nextDouble()};
        u = u1;
    }
}

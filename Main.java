package Om;

import java.util.Scanner;

/**
 * Created by Kida on 05.05.2016.
 */
public class Main {
    public static void main(String[] args) {
        double eps = 0, r = 1;
        double c = 5;
        double [] u = {0,0};
        getData(eps, u, r);

        Newton.search(u, eps);
        show("Метод Ньютона:", Newton.search(u, eps), Newton.I(u));
        show("Метод штрафных функций:", Penalty.search(u, eps, r), Penalty.I(u));
        show("Метод наискорейшего спуска:", SteepestDescent.search(u, eps), SteepestDescent.I(u));
        show("Метод деления отрезка:", Newton.search(u, eps), Newton.I(u));

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

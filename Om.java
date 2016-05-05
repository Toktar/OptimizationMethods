import java.util.Scanner;

/**
 * Created by Kida on 24.03.2016.
 */
public class Om {
    public static final void invert(double A[][]) {
        int n = A.length;
        int row[] = new int[n];
        int col[] = new int[n];
        double temp[] = new double[n];
        int hold, I_pivot, J_pivot;
        double pivot, abs_pivot;

        if (A[0].length != n) {
            System.out.println("Error in Matrix.invert, inconsistent array sizes.");
        }
        // установиим row и column как вектор изменений.
        for (int k = 0; k < n; k++) {
            row[k] = k;
            col[k] = k;
        }
        // начало главного цикла
        for (int k = 0; k < n; k++) {
            // найдем наибольший элемент для основы
            pivot = A[row[k]][col[k]];
            I_pivot = k;
            J_pivot = k;
            for (int i = k; i < n; i++) {
                for (int j = k; j < n; j++) {
                    abs_pivot = Math.abs(pivot);
                    if (Math.abs(A[row[i]][col[j]]) > abs_pivot) {
                        I_pivot = i;
                        J_pivot = j;
                        pivot = A[row[i]][col[j]];
                    }
                }
            }
            if (Math.abs(pivot) < 1.0E-10) {
                System.out.println("Matrix is singular !");
                return;
            }
            //перестановка к-ой строки и к-ого столбца с стобцом и строкой, содержащий основной элемент(pivot основу)
            hold = row[k];
            row[k] = row[I_pivot];
            row[I_pivot] = hold;
            hold = col[k];
            col[k] = col[J_pivot];
            col[J_pivot] = hold;
            // k-ую строку с учетом перестановок делим на основной элемент
            A[row[k]][col[k]] = 1.0 / pivot;
            for (int j = 0; j < n; j++) {
                if (j != k) {
                    A[row[k]][col[j]] = A[row[k]][col[j]] * A[row[k]][col[k]];
                }
            }
            // внутренний цикл
            for (int i = 0; i < n; i++) {
                if (k != i) {
                    for (int j = 0; j < n; j++) {
                        if (k != j) {
                            A[row[i]][col[j]] = A[row[i]][col[j]] - A[row[i]][col[k]] *
                                    A[row[k]][col[j]];
                        }
                    }
                    A[row[i]][col[k]] = -A[row[i]][col[k]] * A[row[k]][col[k]];
                }
            }
        }
        // конец главного цикла

        // переставляем назад rows
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                temp[col[i]] = A[row[i]][j];
            }
            for (int i = 0; i < n; i++) {
                A[i][j] = temp[i];
            }
        }
        // переставляем назад columns
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[row[j]] = A[i][col[j]];
            }
            for (int j = 0; j < n; j++) {
                A[i][j] = temp[j];
            }
        }
    }

    public static double I(double[] u) {
        return 5 * u[0] * u[0] + 4 * u[0] * u[1] + u[1] * u[1] - 16 * u[0] - 12 * u[1];
    }
    public static double I2(double[] u) {
        return 5 * u[0] * u[0] + 4 * u[0] * u[1] + u[1] * u[1];
    }
    public static double p(double[] u, double r) {
        return r*(u[0] + u[1] - 1)*(u[0] + u[1] - 1);
    }


    public static double[] gradIinPoint(double[] u) {
        double[] gradMatrix = {10 * u[0] + 4 * u[1] - 16,
                4 * u[0] + 2 * u[1] - 12};
        return gradMatrix;

    }

    public static double[][] createHesseMatrix() {
        double[][] H = {{10, 4},
                {4, 2}};
        return H;
    }

    public static double getNorm(double[] vector) {
        double result = 0;
        for (int i = 0; i < vector.length; i++) {
            result += vector[i];
        }
        return result;
    }

    public static double[][] mul(double[][] matrix1, double[][] matrix2) {
        int m = matrix1.length;
        int n = matrix1[0].length;
        int k = matrix2.length;
        int l = matrix2[0].length;
        double[][] res = new double[m][l];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < l; j++) {
                res[i][j] = 0;
                for (int s = 0; s < n; s++) {
                    res[i][j] += matrix1[i][s] * matrix2[s][j];
                }
            }
        }
        return res;
    }

    public static double[][] sub(double[][] matrix1, double[][] matrix2) {
        int m = matrix1.length;
        int l = matrix2[0].length;
        double[][] res = new double[m][l];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < l; j++) {

                res[i][j] = matrix1[i][j] - matrix2[i][j];

            }
        }
        return res;
    }

    public static double[][] vectorToMatrix(double[] vector) {
        double[][] matrix = {vector};
//        double[][] matrix = new double[vector.length][1];
//        for (int i = 0; i < vector.length; i++) {
//            matrix[i][0] = vector[i];
//        }
        return matrix;
    }
    public static void Newton() {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите eps");
        double eps = in.nextDouble();
        System.out.println("Введите стартовую точку u0:");
        double[] u = {in.nextDouble(), in.nextDouble()};


        u = NewtonSearch(u, eps);

        System.out.println("u = ("+u[0]+" , "+ u[1]+")");
        System.out.println("I(u) = "+I(u));


    }
    public static double[] NewtonSearch(double[] u, double eps) {
        double[][] invertH = createHesseMatrix();
        invert(invertH);
        while (getNorm(gradIinPoint(u)) > eps) {
            double [][] newU = sub(vectorToMatrix(u), mul(vectorToMatrix(gradIinPoint(u)), invertH));
            u = newU[0];

        }
        return u;
    }

    public static void main(String[] args) {
        //Newton();
        //Penalty();
        steepestDescent();
        stepFractination();
    }

    private static void Penalty() {
        double eps = 0, r = 1;
        double c = 5;
        double [] u = {0,0};
        getData(eps, u, r);
        int iter = 0;
        double uk = 0;
        int k = 0;
        while (r>eps) {
            u = NewtonSearch(u, eps);
            r = r/c;
        }
        System.out.println("u = (" + u[0] + " , " + u[1] + ")");


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


    //TODO: Проверить бы это ещё
    //По аналогии многомерную точку представляем в виде массива double
    //Точка u - начальное приближение
    private static void steepestDescent() {
        double eps = 0.0001;
        double[] u = {0, 0};
        //getData(eps, u, 0);
        double[] gradI0 = gradIinPoint(u);
        while (Math.abs(getNorm(gradI0)) >= eps) {

            // В методичке сказано только, что альфа > 0,
            // про правую границу - ничгео, так что b - случайное
            double b = 10;
            double alpha = dichotomy(0, b, u, eps);
            for (int i = 0; i < u.length; i++) {
                u[i] -= alpha * gradI0[i];
            }
            gradI0 = gradIinPoint(u);
        }

        System.out.println(String.format("u = (%s , %s)", u[0], u[1]));
        System.out.printf("I(u) = %s%n", I(u));
    }

    private static double dichotomy(double a, double b, double[] u, double e) {
        double del = e / 10;
        int n = 0;
        double x1, x2;
        do {
            n++;
            x1 = (b + a - del) / 2;
            x2 = (b + a + del) / 2;
            double i1 = f(x1, u);
            double i2 = f(x2, u);
            if (i1 < i2) {
                b = x2;
            } else if (i1 > i2) {
                a = x1;
            } else {
                a = x1;
                b = x2;
            }
        } while (Math.abs(b - a) >= e);

        return (b + a) / 2;
    }

    // Вспомогательная функция для выбора направления спуска методом дихотомии
    private static double f(double alpha, double[] u0) {
        double[] gradI0 = gradIinPoint(u0);

        double[] u = new double[2];
        for (int i = 0; i < u.length; i++) {
            u[i] = u0[i] - alpha * gradI0[0];
        }

        return I(u);
    }


    private static void stepFractination() {
        double eps = 0.0001;
        double[] u = {-5.3, 12.2};
        double alpha = eps;

        double[] gradI0 = gradIinPoint(u);
        double I = I(u);
        int j = 0;
        while (Math.abs(getNorm(gradI0)) >= eps) {
            ++j;
            double[] u1 = new double[u.length];
            for (int i = 0; i < u.length; i++) {
                u1[i] = u[i] - alpha * gradI0[i];
            }

            double I1 = I(u1);
            if (I1 < I) {
                u = u1;
                I = I1;
            } else {
                alpha /= 2;
            }

            if (j > 3) {
                alpha *= 3;
                j = 0;
            }
            gradI0 = gradIinPoint(u);
        }

        System.out.println(String.format("u = (%s , %s)", u[0], u[1]));
        System.out.printf("I(u) = %s%n", I);
    }
}

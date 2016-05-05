package Om;

import java.util.Scanner;

public class Newton {
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

    public static double[] search(double[] u, double eps) {
        double[][] invertH = createHesseMatrix();
        invert(invertH);
        while (getNorm(gradIinPoint(u)) > eps) {
            double [][] newU = sub(vectorToMatrix(u), mul(vectorToMatrix(gradIinPoint(u)), invertH));
            u = newU[0];

        }
        return u;
    }

}

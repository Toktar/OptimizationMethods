package Om;

class GradientDescent {

    static double I(double[] u) {
        return 5 * u[0] * u[0] + 4 * u[0] * u[1] + u[1] * u[1] - 16 * u[0] - 12 * u[1];
    }

    private static double[] gradIinPoint(double[] u) {
        return new double[]{10 * u[0] + 4 * u[1] - 16, 4 * u[0] + 2 * u[1] - 12};
    }

    private static double getNorm(double[] vector) {
        double result = 0;
        for (double aVector : vector) {
            result += aVector;
        }
        return result;
    }

    //По аналогии многомерную точку представляем в виде массива double
    //Точка u - начальное приближение
    static double[] findBySteepestDescent(double[] u, double eps) {
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

        return u;
    }

    private static double dichotomy(double a, double b, double[] u, double e) {
        double del = e / 10;
        double x1, x2;
        do {
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


    static double[] findByStepFractionation(double[] u, double eps) {
        double alpha = eps / 5;

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
                //alpha *= 3;
                j = 0;
            }
            gradI0 = gradIinPoint(u);
        }

        return u;
    }
}

package experiment;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class FloatOrderMatrixMultiplication {

    /**
     * Suggested values to try: 30, 80, 200
     */
    static int DIM = 30;

    public static void main(final String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder().include(FloatOrderMatrixMultiplication.class.getSimpleName()).forks(1).warmupIterations(3)
                .measurementIterations(3).build();
        new Runner(opt).run();
    }

    float[][] left;
    float[][] prod;
    float[][] right;

    @Benchmark
    public float[][] faster() {

        for (int i = 0; i < DIM; i++) {
            float[] prodI = prod[i];

            for (int c = 0; c < DIM; c++) {
                float[] rightC = right[c];
                float scal = left[i][c];

                for (int j = 0; j < DIM; j++) {
                    prodI[j] += scal * rightC[j];
                }
            }
        }

        return prod;
    }

    @Benchmark
    public float[][] loopCIJ() {

        for (int c = 0; c < DIM; c++) {
            for (int i = 0; i < DIM; i++) {
                for (int j = 0; j < DIM; j++) {
                    prod[i][j] += left[i][c] * right[c][j];
                }
            }
        }

        return prod;
    }

    @Benchmark
    public float[][] loopCJI() {

        for (int c = 0; c < DIM; c++) {
            for (int j = 0; j < DIM; j++) {
                for (int i = 0; i < DIM; i++) {
                    prod[i][j] += left[i][c] * right[c][j];
                }
            }
        }

        return prod;
    }

    @Benchmark
    public float[][] loopICJ() {

        for (int i = 0; i < DIM; i++) {
            for (int c = 0; c < DIM; c++) {
                for (int j = 0; j < DIM; j++) {
                    prod[i][j] += left[i][c] * right[c][j];
                }
            }
        }

        return prod;
    }

    @Benchmark
    public float[][] loopIJC() {

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int c = 0; c < DIM; c++) {
                    prod[i][j] += left[i][c] * right[c][j];
                }
            }
        }

        return prod;
    }

    @Benchmark
    public float[][] loopJCI() {

        for (int j = 0; j < DIM; j++) {
            for (int c = 0; c < DIM; c++) {
                for (int i = 0; i < DIM; i++) {
                    prod[i][j] += left[i][c] * right[c][j];
                }
            }
        }

        return prod;
    }

    @Benchmark
    public float[][] loopJIC() {

        for (int j = 0; j < DIM; j++) {
            for (int i = 0; i < DIM; i++) {
                for (int c = 0; c < DIM; c++) {
                    prod[i][j] += left[i][c] * right[c][j];
                }
            }
        }

        return prod;
    }

    @Setup
    public void setup() {
        left = new float[DIM][DIM];
        right = new float[DIM][DIM];
        prod = new float[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                left[i][i] = (float) Math.random();
                right[i][i] = (float) Math.random();
            }
        }
    }

}

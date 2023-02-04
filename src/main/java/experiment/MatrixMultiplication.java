package experiment;

import static org.ojalgo.function.constant.PrimitiveMath.ZERO;

import java.util.concurrent.TimeUnit;

import org.ojalgo.array.operation.FillAll;
import org.ojalgo.concurrent.DivideAndConquer.Divider;
import org.ojalgo.concurrent.Parallelism;
import org.ojalgo.concurrent.ProcessingService;
import org.ojalgo.random.Uniform;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Benchmark)
public class MatrixMultiplication {

    static final Divider CONQUERER = ProcessingService.INSTANCE.divider().threshold(8).parallelism(Parallelism.THREADS);

    /**
     * Suggested values to try: 30, 100, 500, 1000
     */
    static final int DIM = 1000;
    static final TimeValue TIME = new TimeValue(1, TimeUnit.MINUTES);
    static final Uniform UNIFORM = new Uniform();

    public static void main(final String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(MatrixMultiplication.class.getSimpleName()).forks(1).measurementTime(TIME).warmupTime(TIME)
                .timeUnit(TimeUnit.MINUTES).build();
        new Runner(opt).run();
    }

    //    @Param({ "1", "2", "4", "8", "16", "32" })
    //    public int parallelism;

    double[][] left = new double[DIM][DIM];
    double[][] prod = new double[DIM][DIM];
    double[][] right = new double[DIM][DIM];

    @Benchmark
    public double[][] loopCIJ() {

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
    public double[][] loopCJI() {

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
    public double[][] loopICJ() {

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
    public double[][] loopIJC() {

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
    public double[][] loopJCI() {

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
    public double[][] loopJIC() {

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

        for (int i = 0; i < DIM; i++) {
            FillAll.fill(left[i], 0, DIM, 1, UNIFORM);
            FillAll.fill(right[i], 0, DIM, 1, UNIFORM);
            FillAll.fill(prod[i], 0, DIM, 1, ZERO);
        }
    }

}

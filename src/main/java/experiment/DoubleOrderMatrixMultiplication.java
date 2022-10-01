package experiment;

import org.ojalgo.matrix.store.RawStore;
import org.ojalgo.random.Uniform;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class DoubleOrderMatrixMultiplication {

	/**
	 * Suggested values to try: 30, 80, 200
	 */
	static int DIM = 200;

	public static void main(final String[] args) throws RunnerException {
		final Options opt = new OptionsBuilder().include(DoubleOrderMatrixMultiplication.class.getSimpleName()).forks(1)
				.warmupIterations(3).measurementIterations(3).build();
		new Runner(opt).run();
	}

	double[][] left;
	double[][] prod;
	double[][] right;

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
	public double[][] faster() {

		for (int i = 0; i < DIM; i++) {
			double[] prodI = prod[i];
			
			for (int c = 0; c < DIM; c++) {
				double[] rightC = right[c];
				double scal = left[i][c];

				for (int j = 0; j < DIM; j++) {
					prodI[j] += scal * rightC[j];
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
		Uniform rndm = new Uniform();
		left = RawStore.FACTORY.makeFilled(DIM, DIM, rndm).data;
		right = RawStore.FACTORY.makeFilled(DIM, DIM, rndm).data;
		prod = new double[DIM][DIM];
	}

}

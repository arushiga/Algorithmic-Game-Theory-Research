import java.util.Random;

public class PolyWeights {
    double[] weights;

    double[][] probDistOutput;

    double totalAlgorithmLoss = 0;

    int T;
    public PolyWeights(double[][] lossarray, double epsilon) {
        if (lossarray == null) {
            throw new NullPointerException();
        }
        if (lossarray.length == 0) {
            throw new IllegalArgumentException();
        }
        if (lossarray[0].length == 0) {
            throw new IllegalArgumentException();
        }
        weights = new double[lossarray[0].length];
        probDistOutput = new double[lossarray.length][lossarray[0].length];
        T = lossarray.length;
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 1;
        }
        for (int i = 0; i < T; i++) {
            //add the weights of all action options
            double totalweight = 0;
            for (int j = 0; j < weights.length; j++) {
                totalweight = totalweight + weights[j];
            }
            //update probDistOutput
            for (int j = 0; j < weights.length; j++) {
                probDistOutput[i][j] = weights[j]/totalweight;
            }
            //randomly generate a double between 0 and 1
            Random rand = new Random();
            double myRandNumber = (double)Math.abs(rand.nextInt())/Integer.MAX_VALUE;
            //randomly select an action
            int actionselected = 0;
            for (int j = 0; j < weights.length; j++) {
                if (myRandNumber <= weights[j]) {
                    actionselected = j;
                    break;
                } else {
                    myRandNumber = myRandNumber - weights[j];
                }
            }
            //update weights and totalAlgorithmLoss
            for (int j = 0; j < weights.length; j++) {
                if (j == actionselected) {
                    totalAlgorithmLoss = totalAlgorithmLoss + lossarray[i][j];
                }
                weights[j] = weights[j] * (1 - (epsilon * lossarray[i][j]));
            }
        }
    }

    public double[][] getProbDistOutput() {
        return probDistOutput;
    }

    public double getTotalAlgorithmLoss() {
        return totalAlgorithmLoss;
    }
}

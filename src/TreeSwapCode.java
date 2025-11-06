import java.util.ArrayList;
import java.util.Random;

public class TreeSwapCode {

    double[][] internalLossArray;
    double[][] weights;

    //T is the number of rounds
    int T;
    int numActions;

    int currRound = 0;

    double[][] probDist;

    ArrayList<Integer>[] roundsPerAction;

    int[] lastUpdateRound;
    public TreeSwapCode(double M, double[][] lossArray, double epsilon){
        if (lossArray == null) {
            throw new NullPointerException();
        }
        if (lossArray.length == 0) {
            throw new IllegalArgumentException();
        }
        if (lossArray[0].length == 0) {
            throw new IllegalArgumentException();
        }
        T = lossArray.length;
        numActions = lossArray[0].length;
        internalLossArray = lossArray;

        //prework for calculating swap regret function
        roundsPerAction = (ArrayList<Integer>[]) new ArrayList[numActions];
        for (int i = 0; i < roundsPerAction.length; i++) {
            roundsPerAction[i] = new ArrayList<>();
        }

        //log base M of T is the total number of layers in the tree
        int totallayers = (int)((double)Math.log(T)/(double)Math.log(M)) + 1;
        lastUpdateRound = new int[totallayers];
        weights = new double[totallayers][numActions];
        probDist = new double[totallayers][numActions];
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = 1;
            }
        }

        for (int i = 1; i < T; i++) {
            //update probDist matrix using updated weights matrix
            for (int j = 0; j < weights.length; j++) {
                //calculate total weight of a row
                System.out.println("Round 1:");
                double totalweight = 0;
                for (int k = 0; k < weights[0].length; k++) {
                    System.out.print(weights[j][k]);
                    totalweight = totalweight + weights[j][k];
                }
                for (int k = 0; k < weights[0].length; k++) {
                    probDist[j][k] = weights[j][k] / totalweight;
                }
                System.out.println("");
            }
            //choose an action
            double[] probDistSum = new double[numActions];
            for (int j = 0; j < numActions; j++) {
                for (int k = 0; k < probDist.length; k++){
                    probDistSum[j] = probDistSum[j] + probDist[k][j];
                }
            }
            double[] realDist = new double[numActions];
            double totalValue = 0;
            for (int j = 0; j < probDistSum.length; j++){
                totalValue = totalValue + probDistSum[j];
            }
            for (int j = 0; j < realDist.length; j++) {
                realDist[j] = probDistSum[j]/totalValue;
            }

            // alternatively, instead of steps 1 and 2, you can do this maybe?
            // Edit: Actually, I don't think this works
//            double[] realDist = new double[numActions];
//            double totalValue = 0;
//            for (int j = 0; j < weights.length; j++){
//                totalValue = totalValue + weights[j];
//            }
//            for (int j = 0; j < realDist.length; j++) {
//                realDist[j] = weights[j]/totalValue;
//            }

            System.out.print("For round " + i + ", the distribution is as follows: ");
            for(int j = 0; j < realDist.length; j++){
                System.out.print(realDist[j] + " ");
            }
            System.out.println("");

            Random rand = new Random();
            double myRandNumber = (double)Math.abs(rand.nextInt())/Integer.MAX_VALUE;
            //randomly select an action
            int actionselected = 0;
            for (int j = 0; j < realDist.length; j++) {
                if (myRandNumber <= realDist[j]) {
                    actionselected = j;
                    break;
                } else {
                    myRandNumber = myRandNumber - realDist[j];
                }
            }

            roundsPerAction[actionselected].add(i);

            System.out.println("Total Layers: " + totallayers);
            //make the necessary updates to weights matrix
            for (int j = 0; j < totallayers; j++) {
                boolean update = false;
                boolean reset = false;

                //determine updateComparison for this round
                int updateComparison;
                int powerOfM = (int) Math.pow(M, (j+1));
                updateComparison = T/powerOfM;
                System.out.println("Update Comparison" + updateComparison + " for layer "+ j);

                //determine resetComparison for this round
                int resetComparison;
                int powerOfM2 = (int) Math.pow(M, (j));
                resetComparison = T/powerOfM2;

                //first check if we reset, since then we don't need to update
                if (i % resetComparison == 0) {
                    System.out.println("Successful reset when i is " + i + " and reset Comparison is " + resetComparison + " and j is " + j);
                    reset = true;
                } else if (i % updateComparison == 0) {
                    update = true;
                    System.out.println("update is true.");
                }

                if (reset) {
                    for (int k = 0; k < weights[j].length; k++){
                        weights[j][k] = 1;
                        System.out.println("Just set them to 1");
                    }
                    lastUpdateRound[j] = i;
                    System.out.println("For round " + i + " and layer " + j + " we are resetting.");
                } else if (update) {
                    System.out.println("For round " + i + " and layer " + j + " we are updating.");
                    for (int k = lastUpdateRound[j]; k < i; k++) {
                        for (int m = 0; m < weights[j].length; m++) {
                            weights[j][m] = weights[j][m] * (1 - (0.05 * lossArray[k][m]));
//                            weights[j][m] = weights[j][m] * Math.exp((-0.5) * lossArray[k][m]);
                        }
                    }
                    lastUpdateRound[j] = i;
                }
            }
        }
    }
    public double getswapRegret() {
        double totalswapregret = 0;
        for (int outerloop = 0; outerloop < roundsPerAction.length; outerloop++) {
            double mytotalloss = 0;
            for (int inner = 0; inner < roundsPerAction[outerloop].size(); inner++){
                mytotalloss = mytotalloss + internalLossArray[roundsPerAction[outerloop].get(inner)][outerloop];
            }
            double minexternalsum = 10000000;
            for (int iterator = 0; iterator < internalLossArray[0].length; iterator++) {
                double currentsum = 0;
                for (int inner = 0; inner < roundsPerAction[outerloop].size(); inner++) {
                    currentsum = currentsum + internalLossArray[roundsPerAction[outerloop].get(inner)][iterator];
                }
                if (currentsum < minexternalsum) {
                    minexternalsum = currentsum;
                }
            }
            totalswapregret = totalswapregret + (mytotalloss - minexternalsum);
        }
        return totalswapregret;
    }

}

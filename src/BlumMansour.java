import java.util.ArrayList;
import java.util.Random;

import org.ejml.simple.SimpleEVD;
import org.ejml.simple.SimpleMatrix;
import org.ejml.data.Complex_F64;

public class BlumMansour {
    double[][] weights;

    double[][] probDistOutput;

    double totalAlgorithmLoss = 0;

    int T;

    ArrayList<Integer>[] roundsPerAction;

    double totalswapregret;

    double[][] internalLossArray;

    public BlumMansour(double[][] lossarray, double epsilon) {
        if (lossarray == null) {
            throw new NullPointerException();
        }
        if (lossarray.length == 0) {
            throw new IllegalArgumentException();
        }
        if (lossarray[0].length == 0) {
            throw new IllegalArgumentException();
        }

        internalLossArray = lossarray;

        //prework for my calculate swap regret method
        roundsPerAction = (ArrayList<Integer>[]) new ArrayList[lossarray[0].length];
        for (int i = 0; i < roundsPerAction.length; i++) {
            roundsPerAction[i] = new ArrayList<>();
        }

        weights = new double[lossarray[0].length][lossarray[0].length];
        probDistOutput = new double[lossarray.length*lossarray[0].length][lossarray[0].length];
        // T is the number of rounds we play
        // initialize everything in th n x n to 1's
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights.length; j++) {
                weights[i][j] = 1;
            }
        }
        T = lossarray.length;
        for (int i = 0; i < T; i++) {
            
            ///////////////////////////////////////
            // Notes Block ////////////////////////
            ///////////////////////////////////////
            /// 
            /// 
            // Find the eigenvector; this is a placeholder - the first eigenvector which is associated with the smallest
            // eigenvalue
            // Normally the way that the code works for eigenvectors is return them in an order of increasing eigenvalue
            // The eigenvector of a matrix - when you multiply it by the vector, you end up with a scalar times an 
            // original matrix - that scalar is the eigenvalue
            // Smallest possible eigenvalue is 1 (?) - double check this before implementing
            // We're trying to find the fixed point of a markov chain - if you take a matrix and you think about the 
            // rows and the columns as different actions, and you look at the probability in the first row, then
            // if you come up with some particular markov chain and you multiply it some vector, you can think about 
            // that as taking a step on a markov chain - if the original matrix is the probabilities from one state to 
            // another state
            // If you take a step, its not deterministic - double check this before implementing
            // Taking a step, and you end up with the same matrix as the beginning, you end up at a fixed point
            // Whats the connection between an eigenvector and computing a fixed point? If you have an eigenvector 
            // with an eigenvalue of 1, then that means you've found the fixed point
            // This is because of the way the matrices are constructed, there will always be an eigenvector with 
            // eigenvalue 1
            // Before we had a single vector of weights, now we have a matrix of weights
            // Its an n by n matrix - each row is a weight contained by a particular no regret algorithm like we 
            // coded before
            // Row i column j will be the weight maintained for action j by the i'th external regret.

            // I will experimentally write it by taking weights and fraction-ifying each
            // row separately and then finding the eigenvector


            // This newmatrix is used to store the probabilities - each row is summed and then
            // you take each value and divide the by sum of the row
            // you get an n by n matrix where the sum of each row is 1
            double[][] newmatrix = new double[lossarray[0].length][lossarray[0].length];

            for (int j = 0; j < weights.length; j++) {
                //calculate total weight of a row
                double totalweight = 0;
                for (int k = 0; k < weights[0].length; k++) {
                    totalweight = totalweight + weights[j][k];
                }
                for (int k = 0; k < weights[0].length; k++) {
                    newmatrix[j][k] = weights[j][k] / totalweight;
                }
            }
            System.out.println("This is the beginning of round" + i);
            for (int type1 = 0; type1 < newmatrix.length; type1++){
                for (int type2 = 0; type2 < newmatrix.length; type2++) {
                    System.out.print(newmatrix[type1][type2]+", ");
                }
                System.out.println("");
            }
            System.out.println("------------------------");

            // above total weight is being calculated for each row, and
            // we essentially scale it down to get the probabilities
            // at this point, newmatrix is our matrix of interest.
            // newmatrix is a matrix where the rows are the probabilities of selecting
            // each action for the set of probabilities for a specific action

            // calculating eigenvector using external packages
            SimpleMatrix ogmatrix = new SimpleMatrix(newmatrix);
            SimpleMatrix matrix = ogmatrix.transpose();
            SimpleEVD<SimpleMatrix> evd = matrix.eig();
            int numEigenvalues = evd.getNumberOfEigenvalues();
            SimpleMatrix zeroEigenvector = null;
            for (int iter = 0; iter < numEigenvalues; iter++) {
                //real part of eigenvalue
                double eigenvalue = evd.getEigenvalue(iter).getReal();
                // Check if the eigenvalue is approximately 0
                if (Math.abs(eigenvalue-1) < 1e-10) {
                    zeroEigenvector = evd.getEigenVector(iter);
                    break;
                }
            }

            if (zeroEigenvector == null) {
                System.out.println("No eigenvector corresponding to eigenvalue 1");
            }


            //another thing to do before hand - gather the values in the zeroEigenvector
            boolean columnvec = true;
            if (zeroEigenvector.numRows() == 1) {
                columnvec = false;
            }
            int row = 0;
            int col = 0;
            double[] eigenvector = new double[zeroEigenvector.getNumElements()];
            for (int myi = 0; myi < eigenvector.length; myi++) {
                eigenvector[myi] = zeroEigenvector.get(row, col);
                if (columnvec) {
                    row++;
                } else {
                    col++;
                }
            }

            double[] eigenvectorNorm = new double[eigenvector.length];
            double mysum = 0;
            for (int eigeniter = 0; eigeniter < eigenvector.length; eigeniter++) {
                mysum += Math.abs(eigenvector[eigeniter]);
            }
            for (int eigeniter = 0; eigeniter < eigenvector.length; eigeniter++) {
                eigenvectorNorm[eigeniter] = Math.abs(eigenvector[eigeniter]) / mysum;
            }

            for (int x = 0; x < eigenvectorNorm.length; x++){
                System.out.print(eigenvectorNorm[x] + ", ");
            }

            System.out.println("------------round_end------------");

            //randomly generate a double between 0 and 1
            Random rand = new Random();
            double myRandNumber = (double)Math.abs(rand.nextInt())/Integer.MAX_VALUE;
            //randomly select an action
            int actionselected = 0;

            // first determine whether you have a row or column vector
            // set some boolean columnvec = true or false. true if numcols = 0/1 (idk), false otherwise
            // then you set 2 variables: myrow and mycol (initialize to 0)
            // while loop on j until getNumElements()
            // if randomnum <= zeroEigenvector(myrow, mycol), then actionselected = j
            // condition at the bottom = j++ AND if columnvec = true, then myrow++ else mycol++

//            int myrow = 0;
//            int mycol = 0;
//            int j = 0;
//            while (j < zeroEigenvector.getNumElements()) {
//                if (myRandNumber <= zeroEigenvector.get(myrow, mycol)) {
//                    actionselected = j;
//                } else {
//                    myRandNumber = myRandNumber - zeroEigenvector.get(myrow, mycol);
//                }
//                j++;
//                if (columnvec) {
//                    myrow++;
//                } else {
//                    mycol++;
//                }
//            }

            int j = 0;
            while (j < /*zeroEigenvector*/eigenvectorNorm.length) {
                if (myRandNumber <= /*zeroEigenvector*/eigenvectorNorm[j]) {
                    actionselected = j;
                    break;
                } else {
                    myRandNumber = myRandNumber - eigenvectorNorm[j];
                }
                j++;
            }
            System.out.println("Action Selected: " + j);



            totalAlgorithmLoss = totalAlgorithmLoss + lossarray[i][actionselected];

            //another thing to do before hand - gather the values in the zeroEigenvector
//            int row = 0;
//            int col = 0;
//            double[] eigenvector = new double[zeroEigenvector.getNumElements()];
//            for (int myi = 0; myi < eigenvector.length; myi++) {
//                eigenvector[myi] = zeroEigenvector.get(row, col);
//                if (columnvec) {
//                    myrow++;
//                } else {
//                    mycol++;
//                }
//            }

//            for (int eigeniter = 0; eigeniter < eigenvector.length; eigeniter++) {
//                System.out.print(eigenvector[eigeniter]+", ");
//            }
//            System.out.println("------------round_end------------");


            //update weights
            double[] newlosses = lossarray[i];
            for (int k = 0; k < weights.length; k++) {
                for (int l = 0; l < weights[k].length; l++) {
                    weights[k][l] = weights[k][l] * (1 - (epsilon * eigenvectorNorm[k] * newlosses[l]));

                }
            }

            roundsPerAction[actionselected].add(i);

            ///////////////////////////////////////
            // Notes Block ////////////////////////
            ///////////////////////////////////////

            // Calculating swap regret
            // The following function is going to take in losses

            // External regret is the difference between the regret that we did have and all the loss vectors
            // We have to figure out how you actually did - total loss
            // We must find the total loss for ach of the possible actions
            // Subtract the total loss of our particular output

            // For swap regret we look at subsequence where we played action 1 
            // Find all the days i actually picked action 1 and compute the external regret on that sequence
            // Compute the external regret on that sequence
            // It is entirely possible that for each subsequence you can get 0
            // Slopes tell you what you should have worked. Previous algorithm worked using the total loss and reward,
            // which is the position on the y axis
            // So in the second half, even though action 3 is preferable, its way behind in terms of how its doing 
            // historically


            // A new markov chain is generated each round, and the fixed point of that markov chain directs the next 
            // action that we select
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

import org.junit.Test;

public class BlumMansourTest {
    @Test
    public void testBlumMansourSwap() {
        double[][] loss = {
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1}
        };
        //0.2231874043884571, 0.24798600487606343, 0.5288265907354794,
        BlumMansour tester = new BlumMansour(loss, 0.1);
        double swapregret = tester.getswapRegret();
        System.out.print(swapregret);
//        System.out.println(tester.getTotalAlgorithmLoss());
//        for (int i = 0; i < probdist.length; i++) {
//            for (int j = 0; j < probdist[0].length; j++) {
//                System.out.print(probdist[i][j] + ", ");
//            }
//            System.out.println("");
//        }
    }

    @Test
    public void testSwapRegret2() {
        double[][] loss = {
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {1, 0, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1},
                {0, 1, 0.1}
        };
        BlumMansour tester = new BlumMansour(loss, 0.1);
        double swapregret = tester.getswapRegret();
        System.out.print(swapregret);
//        System.out.println(tester.getTotalAlgorithmLoss());
//        for (int i = 0; i < probdist.length; i++) {
//            for (int j = 0; j < probdist[0].length; j++) {
//                System.out.print(probdist[i][j] + ", ");
//            }
//            System.out.println("");
//        }
    }

    @Test
    public void testBlumMansour2() {
        double[][] lossMatrix = {
                {0.1, 0.9, 0.4, 0.5},
                {0.2, 0.8, 0.3, 0.6},
                {0.3, 0.7, 0.2, 0.7},
                {0.4, 0.6, 0.1, 0.8},
                {0.5, 0.5, 0.2, 0.9},
                {0.6, 0.4, 0.3, 0.1},
                {0.7, 0.3, 0.4, 0.2},
                {0.8, 0.2, 0.5, 0.3},
                {0.9, 0.1, 0.6, 0.4},
                {0.1, 0.9, 0.7, 0.5}
        };
        BlumMansour tester = new BlumMansour(lossMatrix, 0.1);
        double swapregret = tester.getswapRegret();
        System.out.print(swapregret);
    }


}

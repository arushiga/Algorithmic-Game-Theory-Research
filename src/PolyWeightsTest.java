import org.junit.Test;

public class PolyWeightsTest {
    @Test
    public void testPolyWeights() {
        double[][] loss = {
            {1, 0},
            {0, 1},
            {0.5, 1}
        };
        PolyWeights tester = new PolyWeights(loss, 0.1);
        double[][] probdist = tester.getProbDistOutput();
        System.out.println(tester.getTotalAlgorithmLoss());
        for (int i = 0; i < probdist.length; i++) {
            for (int j = 0; j < probdist[0].length; j++) {
                System.out.print(probdist[i][j] + ", ");
            }
            System.out.println("");
        }
    }

    @Test
    public void testPolyWeights3() {
        double[][] loss = {
                {1.1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0},
                {0, 1},
                {1, 0}
        };
        PolyWeights tester = new PolyWeights(loss, 0.1);
        double[][] probdist = tester.getProbDistOutput();
        System.out.println(tester.getTotalAlgorithmLoss());
        for (int i = 0; i < probdist.length; i++) {
            for (int j = 0; j < probdist[0].length; j++) {
                System.out.print(probdist[i][j] + ", ");
            }
            System.out.println("");
        }
    }

    @Test
    public void testPolyWeightsSwap() {
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
        PolyWeights tester = new PolyWeights(loss, 0.1);
        double[][] probdist = tester.getProbDistOutput();
        System.out.println(tester.getTotalAlgorithmLoss());
        for (int i = 0; i < probdist.length; i++) {
            for (int j = 0; j < probdist[0].length; j++) {
                System.out.print(probdist[i][j] + ", ");
            }
            System.out.println("");
        }

    }

    @Test
    public void testPolyWeights5() {
        double[][] loss = {
                {1, 0},
                {0, 1},
                {0.5, 1}
        };
        PolyWeights tester = new PolyWeights(loss, 0.1);
        double[][] probdist = tester.getProbDistOutput();
        System.out.println(tester.getTotalAlgorithmLoss());
        for (int i = 0; i < probdist.length; i++) {
            for (int j = 0; j < probdist[0].length; j++) {
                System.out.print(probdist[i][j] + ", ");
            }
            System.out.println("");
        }

    }

    @Test
    public void testPolyWeights2() {
        double[][] loss = {
                {1, 0},
                {0, 1},
                {0.5, 1}
        };
        PolyWeights tester = new PolyWeights(loss, 0.1);
        double[][] probdist = tester.getProbDistOutput();
        System.out.println(tester.getTotalAlgorithmLoss());
        for (int i = 0; i < probdist.length; i++) {
            for (int j = 0; j < probdist[0].length; j++) {
                System.out.print(probdist[i][j] + ", ");
            }
            System.out.println("");
        }

    }
}

import org.junit.Test;

public class TreeSwapTest {
    @Test
    public void testTreeSwap() {
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
        //0.2231874043884571, 0.24798600487606343, 0.5288265907354794,
        TreeSwapCode tester = new TreeSwapCode(5, loss, 0.1);
        double swapregret = tester.getswapRegret();
        System.out.print(swapregret);
    }
}

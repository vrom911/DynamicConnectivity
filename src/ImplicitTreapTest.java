import org.junit.Before;
import org.testng.annotations.Test;

public class ImplicitTreapTest {
    ImplicitTreap L, R, L1, R1, testTreap, testTreap1;

    @Before
    public void setup(){
        L = new ImplicitTreap(7);
        R = new ImplicitTreap(6);
        testTreap = new ImplicitTreap(10, L, R);

        L1 = new ImplicitTreap(2);
        R1 = new ImplicitTreap(3);
        testTreap1 = new ImplicitTreap(9, L1, R1);
    }

    @Test
    public void testMerge() {
        ImplicitTreap.inOrderPrint(testTreap);
        System.out.println("---");
        ImplicitTreap.inOrderPrint(testTreap.split(testTreap.merge(testTreap, testTreap1), 3).getSecond());
//        testTreap.
        System.out.println("--");
//        ImplicitTreap.inOrderPrint(testTreap.add(8));
        ImplicitTreap.inOrderPrint(testTreap.add(8, 14).remove(2));
    }
}
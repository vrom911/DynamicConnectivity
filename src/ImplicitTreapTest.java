import org.junit.Before;
import org.testng.annotations.Test;

public class ImplicitTreapTest {

    @Before
    public void setup(){
        ImplicitTreap<Integer> L = new ImplicitTreap<Integer>(7);
        ImplicitTreap<Integer> R = new ImplicitTreap<Integer>(6);
        ImplicitTreap<Integer> testTreap = new ImplicitTreap<Integer>(10, L, R);

        ImplicitTreap<Integer> L1 = new ImplicitTreap<Integer>(2);
        ImplicitTreap<Integer> R1 = new ImplicitTreap<Integer>(3);
        ImplicitTreap<Integer> testTreap1 = new ImplicitTreap<Integer>(9, L1, R1);
    }
    @Test
    public void testMerge() {
        

    }

}
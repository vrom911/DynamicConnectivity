package ru.ifmo.ads.romashkina.dynamicConnectivity;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.ifmo.ads.romashkina.graph.Edge;

import static org.junit.Assert.assertEquals;
import static ru.ifmo.ads.romashkina.dynamicConnectivity.DCTestUtility.*;

@FixMethodOrder(MethodSorters.JVM)
public class DynamicConnectivityTest {

    @BeforeClass
    public static void setUp() {
        setting();
    }

    @Test
    public void initTest() {
        settingUp(10, 50);
        assertEquals(makeEdgeListDC(ndc), makeEdgeListDC(fdc));
    }

    @Test
    public void checkFiveVertexTest() {
        checkFromMap(5, 100000);
    }

    @Test
    public void check10VertexTest() {
        checkFromMap(10, 25000);
    }

    @Test
    public void fewOperations() {
        settingUp(100, 500);
        for (int i = 0; i < 20000; i++) {
            Edge e = edges.get(i % edges.size());
            if (e.getTo().equals("")) nextOperationBoth(e.getFrom(), e.getTo());
        }
        assertEquals(makeEdgeListDC(ndc), makeEdgeListDC(fdc));
    }

    @Test
    public void checkThousandVertexTest() {
        checkFromMap(1000);
    }
}
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.inOrderPrint;

public class Main {

    private static ImplicitTreap testNode() {
        ImplicitTreap L, R, L1, R1, testTreap, testTreap1;
        L = new ImplicitTreap(7);
        R = new ImplicitTreap(6);
        testTreap = new ImplicitTreap(10, L, R);

        L1 = new ImplicitTreap(2);
        R1 = new ImplicitTreap(3);
        testTreap1 = new ImplicitTreap(9, L1, R1);

        inOrderPrint(testTreap);
        System.out.println("---");
        inOrderPrint(testTreap1);
        System.out.println("---");

        return ImplicitTreap.merge(testTreap, testTreap1);
    }

    public static void main(String[] args) {
        ImplicitTreap t = new ImplicitTreap(100);
        ImplicitTreap t1 = new ImplicitTreap(99);
        inOrderPrint(ImplicitTreap.merge(t, t1));
        ImplicitTreap node1 = testNode();

        System.out.println("NODE 1");
        inOrderPrint(node1);
        System.out.println(";;");
        inOrderPrint(ImplicitTreap.split(node1, 5).getFirst());

        ImplicitTreap node2 = testNode();
        System.out.println("NODE 2");
        inOrderPrint(ImplicitTreap.split(node2.getRight().getLeft()).getFirst());
////        ru.ifmo.ads.romashkina.treap.ImplicitTreap.inOrderPrint(testTreap.add(8));
//        ru.ifmo.ads.romashkina.treap.ImplicitTreap.inOrderPrint(testTreap.add(8, 14).remove(2));

//        ru.ifmo.ads.romashkina.graph.Graph et = ru.ifmo.ads.romashkina.graph.GraphUtility.readGraph("graphTest.txt");
//        System.out.println(et);
//        ru.ifmo.ads.romashkina.euler.EulerTourTree etTree = new ru.ifmo.ads.romashkina.euler.EulerTourTree(et);
//        System.out.println(etTree);
    }
}

public class Main {

    private static ImplicitTreap testNode() {
        ImplicitTreap L, R, L1, R1, testTreap, testTreap1;
        L = new ImplicitTreap(7);
        R = new ImplicitTreap(6);
        testTreap = new ImplicitTreap(10, L, R);

        L1 = new ImplicitTreap(2);
        R1 = new ImplicitTreap(3);
        testTreap1 = new ImplicitTreap(9, L1, R1);

        ImplicitTreap.inOrderPrint(testTreap);
        System.out.println("---");
        ImplicitTreap.inOrderPrint(testTreap1);
        System.out.println("---");

        return testTreap.merge(testTreap, testTreap1);
    }

    public static void main(String[] args) {
        ImplicitTreap node1 = testNode();

        System.out.println("NODE 1");
        ImplicitTreap.inOrderPrint(node1);
        System.out.println(";;");
        ImplicitTreap.inOrderPrint(ImplicitTreap.split(node1, 5).getFirst());

        ImplicitTreap node2 = testNode();
        System.out.println("NODE 2");
        ImplicitTreap.inOrderPrint(ImplicitTreap.split(node2.getRight().getLeft()).getFirst());
////        ImplicitTreap.inOrderPrint(testTreap.add(8));
//        ImplicitTreap.inOrderPrint(testTreap.add(8, 14).remove(2));

//        Graph et = GraphUtility.readGraph("graphTest.txt");
//        System.out.println(et);
//        EulerTourTree etTree = new EulerTourTree(et);
//        System.out.println(etTree);
    }
}

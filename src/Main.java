public class Main {

    public static void main(String[] args) {
        ImplicitTreap<Integer> L = new ImplicitTreap<Integer>(7);
        ImplicitTreap<Integer> R = new ImplicitTreap<Integer>(6);
        ImplicitTreap<Integer> testTreap = new ImplicitTreap<Integer>(10, L, R);



        ImplicitTreap<Integer> L1 = new ImplicitTreap<Integer>(2);
        ImplicitTreap<Integer> R1 = new ImplicitTreap<Integer>(3);
        ImplicitTreap<Integer> testTreap1 = new ImplicitTreap<Integer>(9, L1, R1);

//        testTreap.inOrderPrint();
//        testTreap1.inOrderPrint();
        ImplicitTreap.inOrderPrint(testTreap.split(testTreap.merge(testTreap, testTreap1), 3)[1]);
//        testTreap.
        System.out.println("--");
//        ImplicitTreap.inOrderPrint(testTreap.add(8));
        ImplicitTreap.inOrderPrint(testTreap.add(8).remove(2));
    }
}

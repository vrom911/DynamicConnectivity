public class ImplicitTreap<T extends Comparable> {

    public T y;
    public ImplicitTreap left;
    public ImplicitTreap right;
    public int size;

    public ImplicitTreap(T y){
        this.y = y;
        this.left = null;
        this.right = null;
        this.size = 1;
    }

    public ImplicitTreap(T y, ImplicitTreap left, ImplicitTreap right){
        this.y = y;
        this.left = left;
        this.right = right;
        this.size = 1;
        this.updateSize();
    }

    public int size(ImplicitTreap t) {
        return t == null ? 0 : t.size;
    }

    public void updateSize() {

        size = size(left) + size(right) + 1;
    }

    public ImplicitTreap<T> merge(ImplicitTreap<T> l, ImplicitTreap<T> r) {
        ImplicitTreap result;

        if (l == null) {
            return r;
        }
        if (r == null) {
            return l;
        }

        if (l.y.compareTo(r.y) < 0) {
            result = new ImplicitTreap(r.y, merge(l, r.left), r.right);
        } else {
            result = new ImplicitTreap(l.y, l.left, merge(l.right, r));
        }
        result.updateSize();
        return result;
    }

    public ImplicitTreap[] split(ImplicitTreap t, int k) {
        ImplicitTreap[] result = new ImplicitTreap[] {null, null};
        if (t == null) {
            return  result;
        }
        int ind = size(t.left) + 1;
        if (ind <= k) {
            result = split(t.right, k - ind);
            t.right = result[0];
            t.updateSize();
            result[0] = t;
            return result;
        }
        else {
            result = split(t.left, k);
            t.left = result[1];
            t.updateSize();
            result[1] = t;
            return result;
        }
    }

    public ImplicitTreap add(int k) {
        ImplicitTreap toAdd = new ImplicitTreap(k);
        ImplicitTreap[] splitRes = split(this, k);

        return merge(merge(splitRes[0], toAdd), splitRes[1]);
    }

    public ImplicitTreap remove(int k) {
        ImplicitTreap[] splitRes = split(this, k);
        return merge(splitRes[0], split( splitRes[1], 1)[1]);
    }

    public static void inOrderPrint(ImplicitTreap t) {
        if (t == null)
            return;
        if (t.left != null) {
            inOrderPrint(t.left);
        }

        System.out.println(t);
        if (t.right != null) {
            inOrderPrint(t.right);
        }
    }

    public void update(int k, T y) {
        int ind = size(left) + 1;
        if (ind == k) {
            this.y = y;
        } else if (ind < k) {
            this.right.update(k - ind, y);
        } else {
            this.left.update(k, y);
        }
    }

    public void clear() {
        this.y = null;
        this.left = null;
        this.right = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        return "Node {" +
                "y = " + y +
                ", size = " + size +
                '}';
    }

}

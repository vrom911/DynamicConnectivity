package ru.ifmo.ads.romashkina.euler;

import ru.ifmo.ads.romashkina.graph.TreapEdge;
import ru.ifmo.ads.romashkina.graph.Vertex;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;
import ru.ifmo.ads.romashkina.utils.Pair;

import java.util.Random;

import static ru.ifmo.ads.romashkina.graph.GraphUtility.removeOrientedEdge;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.*;

public class EulerTourTree {
    public static Random random = new Random(123456);

    private static Pair<ImplicitTreap<TreapEdge>> updateParts(TreapEdge treapEdge) {
        ImplicitTreap<TreapEdge> edgeTreap = treapEdge.getLink();
        Pair<ImplicitTreap<TreapEdge>> pair = split(edgeTreap);
        ImplicitTreap<TreapEdge> beforeEdge = pair.getFirst();
        ImplicitTreap<TreapEdge> afterEdge = pair.getSecond();
        beforeEdge = remove(beforeEdge, fullSize(beforeEdge) - 1);
        ImplicitTreap<TreapEdge> newVedge = new ImplicitTreap<>(random.nextLong(), treapEdge);
        treapEdge.setLink(newVedge);
        afterEdge = merge(newVedge, afterEdge);
        return new Pair<>(beforeEdge, afterEdge);
    }

    public static ImplicitTreap<TreapEdge> link(Vertex v, Vertex u) {
        if (areConnected(u, v)) return null;

        TreapEdge vTreapEdge = v.getRandomTreapEdge();
        TreapEdge uTreapEdge = u.getRandomTreapEdge();
        ImplicitTreap<TreapEdge> beforeVedge = null;
        ImplicitTreap<TreapEdge> afterVedge = null;
        ImplicitTreap<TreapEdge> beforeUedge = null;
        ImplicitTreap<TreapEdge> afterUedge = null;
        if (vTreapEdge != null) {
            Pair<ImplicitTreap<TreapEdge>> pairV = updateParts(vTreapEdge);
            beforeVedge = pairV.getFirst();
            afterVedge = pairV.getSecond();
        }

        if (uTreapEdge != null) {
            Pair<ImplicitTreap<TreapEdge>> pairU = updateParts(uTreapEdge);
            beforeUedge = pairU.getFirst();
            afterUedge = pairU.getSecond();
        }

        TreapEdge vu = v.addTreapEdge(u);
        ImplicitTreap<TreapEdge> vuTreap = new ImplicitTreap<>(random.nextLong(), vu);
        vu.setLink(vuTreap);
        beforeVedge = merge(beforeVedge, vuTreap);
        TreapEdge uv = u.addTreapEdge(v);
        ImplicitTreap<TreapEdge> uvTreap = new ImplicitTreap<>(random.nextLong(), uv);
        uv.setLink(uvTreap);
        beforeUedge = merge(beforeUedge, uvTreap);

        return merge(merge(beforeVedge, afterUedge), merge(beforeUedge, afterVedge));

    }

    public static Pair<ImplicitTreap<TreapEdge>> cut(Vertex v, Vertex u) {
        if (!v.hasTreapEdge(u)) return null;

        TreapEdge eFromV = v.getTreapEdgeTo(u);
        TreapEdge eFromU = u.getTreapEdgeTo(v);
        ImplicitTreap<TreapEdge> vu = eFromV.getLink();
        ImplicitTreap<TreapEdge> uv = eFromU.getLink();
        if (findIndex(vu) > findIndex(uv)) {
            Vertex temp = v;
            v = u;
            u = temp;
            ImplicitTreap<TreapEdge> tempTreap = vu;
            vu = uv;
            uv = tempTreap;
        }

        ImplicitTreap<TreapEdge> part1 = split(vu).getFirst();
        Pair<ImplicitTreap<TreapEdge>> other = split(uv);
        ImplicitTreap<TreapEdge> part2 = other.getFirst();
        ImplicitTreap<TreapEdge> part3 = other.getSecond();

        part1 = remove(part1, fullSize(part1) - 1);
        part2 = remove(part2, fullSize(part2) - 1);

        removeOrientedEdge(v, u);
        return new Pair<>(merge(part1, part3), part2);
    }

    public static boolean areConnected(Vertex v, Vertex u) {
        TreapEdge vTreapEdge = v.getRandomTreapEdge();
        TreapEdge uTreapEdge = u.getRandomTreapEdge();
        return !(vTreapEdge == null || uTreapEdge == null) && getRoot(vTreapEdge.getLink()) == getRoot(uTreapEdge.getLink());
    }
}

package ru.ifmo.ads.romashkina.euler;

import ru.ifmo.ads.romashkina.graph.Graph;
import ru.ifmo.ads.romashkina.graph.Vertex;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;
import ru.ifmo.ads.romashkina.treap.ImplicitTreapPair;

import java.util.List;

import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.*;

/*
 * 0. Убрать считывать из файла в конструкторе; заменить на статический метод
 * 1. Заменить список рёбер в Vertex на HashMap из самой вершины в ребро, ведущее в эту вершину
 *    + getEdgeFrom убрать
 * 2. Поправь findEulerPath, чтобы он возвращал сразу ImplicitTreap по эйлерову пути
 *    + перенести в тесты функции построения ImplicitTreap из списка вершин
 *    + не забыть обновить ссылки с вершин на узлы внутри поиска пути
 * 3. Ничего не Хранить в EulerTourTree
 * 4. Убрать метод добавления вершины
 * 5. Добавлять рёбра вдоль пути в тестах (и обновлять в них ссылки на вершины)
 *    + тут потребуется рефакторинг
 * 5. Добавить метод удаления ребра (возвращает пару из деревьев)
 * 6. Добавить ручные тесты на несколько link'ов подряд
 * 7. Добавить ручные простые тесты на cut
 */
public class EulerTourTree {
    private List<Graph> graphs;

    EulerTourTree(List<Graph> graphs) {
        this.graphs = graphs;
    }

    public static ImplicitTreap<Vertex> link(Vertex v, Vertex u) {
        if (areConnected(u, v)) return null;

        // разрезаем обход первого эйлерового дерева по вершине v; обновляем ссылку у ребра v -> x на новый клон v
        ImplicitTreapPair<Vertex> vPair = split(v.getIn());
        ImplicitTreap<Vertex> beforeV = vPair.getFirst();
        ImplicitTreap<Vertex> afterV  = vPair.getSecond();
        ImplicitTreap<Vertex> vNew = new ImplicitTreap<>(v);
        Vertex nextAfterV = getValueByIndex(afterV, 1);
//        if (nextAfterV != null) nextAfterV.getEdgeFrom(v).setFromNode(vNew);
//        else System.out.println("Arrrr");
        afterV = merge(vNew, afterV);

        // разрезаем обход второго эйлерового дерева по вершине u; обновляем ссылку у ребра u -> y на новый клон u
        ImplicitTreapPair<Vertex> uPair = split(u.getIn());
        ImplicitTreap<Vertex> beforeU = uPair.getFirst();
        ImplicitTreap<Vertex> afterU = uPair.getSecond();
        ImplicitTreap<Vertex> uNew = new ImplicitTreap<>(u);
        Vertex nextAfterU = getValueByIndex(afterU, 1); // тут NPE если дерево в u состоит из одной вершины всего
//        if (nextAfterU != null) nextAfterU.getEdgeFrom(u).setFromNode(uNew);
//        else System.out.println("Arrrr2");
        afterU = merge(uNew, afterU);

        u.setIn(uNew); // Обновить у вершины ссылку на ноду на случай если u -- первая вершина пути
        beforeU = remove(beforeU, 0); // удалить первую вершину эйлеровго пути

        // обновить ссылку на from у ребра между первой (удалённой) вершиной в пути на её последнее вхождение
//        if (fullSize(beforeU) > 0) {
//            ImplicitTreap<Vertex> lastInAfterU = getTreapByIndex(afterU, fullSize(afterU));
//            Vertex lastVertexInAfterU = lastInAfterU.getValue();
//            getValueByIndex(beforeU, 1).getEdgeTo(lastVertexInAfterU).setFromNode(lastInAfterU); // теперь не падает!!!
//
//        }

//        addOrientedEdge(v, u);
//        v.getEdgeTo(u).setFromNode(getTreapByIndex(beforeV, fullSize(beforeV)));
//        v.getEdgeTo(u).setToNode(uNew); // uNew == new first in afterU (clone)
//        u.getEdgeTo(v).setFromNode(getTreapByIndex(beforeU, fullSize(beforeU)));
//        u.getEdgeTo(v).setToNode(vNew); // vNew == new first in afterV (clone)

        ImplicitTreap<Vertex> vu = merge(beforeV, afterU);
        ImplicitTreap<Vertex> uv = merge(beforeU, afterV);
        return merge(vu, uv);
    }

    public static void cut(Vertex v, Vertex u) {
        if (!v.hasEdge(u)) return;



    }

    public static boolean areConnected(Vertex v, Vertex u) {
        return getRoot(v.getIn()) == getRoot(u.getIn());
    }


}

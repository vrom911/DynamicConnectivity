package ru.ifmo.ads.romashkina.dynamicConnectivity;

import ru.ifmo.ads.romashkina.euler.EulerTourTree;
import ru.ifmo.ads.romashkina.graph.Graph;
import ru.ifmo.ads.romashkina.graph.GraphWithMST;
import ru.ifmo.ads.romashkina.graph.UndirectedEdge;
import ru.ifmo.ads.romashkina.graph.Vertex;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ifmo.ads.romashkina.euler.EulerUtility.findEulerPathForGraph;
import static ru.ifmo.ads.romashkina.graph.GraphUtility.kruskalFindMST;
import static ru.ifmo.ads.romashkina.utils.Stuff.log;


/*
 * -1. Убрать level из Edge (он будет в UndirectedEdge потом)
 * 0. Пропатчить конструктор FastDC, чтобы на каждому уровне строился эйлеров путь
 * 1. написать incLevel функцию, которая на каждом уровне увеличивает level у ребра (ориентированного ребра)
 * 2. Научиться понимать: лежит ребро в остовном лесу или нет (пройтись по каждому уровню и смотреть в Other)
 * 3. Если не лежит, удалить его с каждого уровня просто из __НЕ__ MST
 * 3,5. Возможно ребра вообще нет в графе (тогда вообще ничего не делать)
 * 4. Иначе:
 *    + найти максимальный уровень, на котором лежит ребро (проходом по списку)
 *    + найти оба ребра (потому что они ориентированные) (с этим дерево разберётся)
 *      + удалить из дерева эйлерова обхода (одно ребро, дерево разберётся)
 *      + удалить два ребра из графа (возможно дерево и с этим разберётся)
 *      + удалить из дерева отрезков
 *    + cutFromMST: удалить из каждого уровня
 *    + выбрать компоненту распавшуюся связности меньшего размера
 *    + I. Для каждого ребра уровня L из MST из меньшей компоненты связности:
 *      + сделать incLevel этому ребру
 *      + Добавить в MST на следующем уровне (используя Link для дерева эйлерова обхода)
 *    + II. Для каждого ребра уровня L _НЕ_ из MST из меньшей компоненты связности:
 *      + если концы лежат внутри той же компоненты оба (в T1, в меньшей), тогда перенести на следующий уровень в Other (incLevel + Добавить ребро в граф other на следующий уровень)
 *      + иначе:
 *        + когда нашла ребро, то удаляешь из Other, добавляешь в MST и делаешь линк (линк уже добавляет в MST)
 *        + удалять два ребра ориентированных из Other
 *        + _НЕ_ переносить на следующий уровень
 *    + можно считерить: на предыдущих уровнях сразу добавить ребро (ибо ты его нашла и знаешь, какие вершины оно соединяет)
 *    + но если не нашла, то запустить мутную процедуру на предыдущем уровне
 *  5. Хранить на каждом уровне L список UndirectedEdge уровня L
// *  5. Как находить рёбра уровня L внутри меньшей компоненты связности в MST:
// *    + нужно в каждом графе хранить список всех UndirectedEdges для этого графа и
// *    + построить по нему дерево отрезков с минимумом по Level
// *    + Обходом по дереву получить все рёбра (спускаешься только в те вершины, в которых минимум <= L)
// *  6. Как получить рёбра уровня L внутри Other в меньшей компоненте связности:
// *    + точно так же
// *  7. Хранить два дерева отрезков двумя полями в FastDC _НЕ_ в каждом элементе списка, а просто
// *  8. Реализовать дерево отрезков в виде декартового дерева Treap
// *     + структура SegmentTreap
// *     + build: создаёшь узел от каждого ребра, потом merge
// *     + insert: просто добавляешь ребро в конец (как add в ImplicitTreap)
// *     + delete: нужно в UndirectedEdge хранить ссылку на узел SegmentTreap, подниматься к родителю, а потом remove через сплиты
// *     + SegmentTreap нужно обновлять в самом конце операции, чтобы на промежуточных уровнях ничего не испортилось
 */
public class FastDynamicConnectivity implements DynamicConnectivity{
    public List<GraphWithMST> graphs;

    public FastDynamicConnectivity(List<UndirectedEdge> edges) {
        GraphWithMST g = kruskalFindMST(edges);
        findEulerPathForGraph(g.getGraphMST());
        int n = log(g.getGraphMST().getVertexNum());
        graphs = new ArrayList<>(n);
        graphs.add(g);


        for (int i = 1; i < n; i++) {
            Map<String, Vertex> vertices = new HashMap<>();
            for (String l : g.getGraphMST().getVertexMap().keySet()) {
                vertices.put(l, new Vertex(l));
            }
            // TODO: свои вершины для каждой пары
            graphs.add(new GraphWithMST(new Graph(vertices), new Graph(vertices)));
        }
    }

    @Override
    // TODO: Добавлять в дерево отрезков ребро
    public void link(String v, String  u) {
        GraphWithMST graphWithMST = graphs.get(0);
        Graph g = graphWithMST.getGraphMST();
        Graph gOther = graphWithMST.getGraphOther();
        ImplicitTreap<Vertex> link = EulerTourTree.link(g.getVertex(v), g.getVertex(u));
        if (link == null) {
            gOther.addDirectedEdge(v, u);
        }
    }

    @Override
    public void cut(String v, String  u) {
    }

    @Override
    public boolean areConnected(String v, String  u) {
        return false;
    }
}

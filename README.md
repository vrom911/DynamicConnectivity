# Dynamic Connectivity

The project that implements a data structure of the dynamic connectivity in random undirected graph, which supports operations of removal and addition of edges, verification that two vertices are in the same connected component.

- `void link(u, v)` – add edge to the graph, operation time is ![equation](http://latex.codecogs.com/png.latex?O(\log&space;&space;n))
- `void cut(u, v)` – delete edge from the graph, the amortized time for a delete operation is ![equation](http://latex.codecogs.com/png.latex?O(\log{}^2&space;n))
- `boolean areConnected(u, v)` – query to check whether two vertices are connected by a path, operation time is ![equation](http://latex.codecogs.com/png.latex?O(\log&space;&space;n))

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Other

[Report](https://github.com/vrom911/DynamicConnectivity/blob/master/Romashkina_M4138_DynamicConnectivity.pdf) in russian

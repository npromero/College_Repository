package cs2321;
/**
 * Niklas Romero
 *CS2321/assignment1
 * The class implements graph to make an adjustable graph
 */
import net.datastructures.*;

/*
 * Implement Graph interface. A graph can be declared as either directed or undirected.
 * In the case of an undirected graph, methods outgoingEdges and incomingEdges return the same collection,
 * and outDegree and inDegree return the same value.
 * 
 * @author CS2321 Instructor
 */
public class AdjListGraph<V, E> implements Graph<V, E> {
	private class InnerVertex<V> implements Vertex<V> {
		private V element;
		private Position<Vertex<V>> pos;
		private Map<Vertex<V>, Edge<E>> outgoing, incoming;

		// Constructs a new InnerVertex instance storing an element.
		public InnerVertex(V elem, boolean graphIsDirected) {
			element = elem;
			outgoing = new HashMap<>();
			if (graphIsDirected)
				incoming = new HashMap<>();
			else
				incoming = outgoing; // if undirected, alias outgoing map
		}
		public V getElement() {
			return element;
		}
		public V setElement(V element) {
			V previousValue = this.element;
			this.element = element;
			return previousValue;
		}

		// Stores the vertex's position within the graph's vertex list.
		public void setPosition(Position<Vertex<V>> p) {
			pos = p;
		}

		// Returns the vertex's position within the graph's vertex list.
		public Position<Vertex<V>> getPosition() {
			return pos;
		}

		// Returns reference to the underlying map of outgoing edges.
		public Map<Vertex<V>, Edge<E>> getOutgoing() {
			return outgoing;
		}

		// Returns reference to the underlying map of incoming edges.
		public Map<Vertex<V>, Edge<E>> getIncoming() {
			return incoming;
		}
	}
	private class InnerEdge<E> implements Edge<E> {
		private E element;
		private Position<Edge<E>> pos;
		private Vertex<V>[] endpoints;

		// Constructs InnerEdge instance from u to v, storing the given element.
		public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
			element = elem;
			endpoints = (Vertex<V>[]) new Vertex[] { u, v }; // array of length 2
		}

		// Returns the element associated with the edge.
		public E getElement() {
			return element;
		}
		
		public E setElement(E element) {
			E previousValue = this.element;
			this.element = element;
			return previousValue;
		}

		// Returns reference to the endpoint array.
		public Vertex<V>[] getEndpoints() {
			return endpoints;
		}

		// Stores the position of this edge within the graph's vertex list.
		public void setPosition(Position<Edge<E>> p) {
			pos = p;
		}

		// Returns the position of this edge within the graph's vertex list.
		public Position<Edge<E>> getPosition() {
			return pos;
		}
	} 
	
	private boolean isDirected;
	private PositionalList<Vertex<V>> vertices = new DoublyLinkedList<>();
	private PositionalList<Edge<E>> edges = new DoublyLinkedList<>();

	public AdjListGraph(boolean directed) {
		isDirected = directed;
	}

	public AdjListGraph() {
		//TODO non directed graph constructor 
	}


	/* (non-Javadoc)
	 * @see net.datastructures.Graph#edges()
	 */
	public Iterable<Edge<E>> edges() {
		return edges;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#endVertices(net.datastructures.Edge)
	 */
	public Vertex[] endVertices(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		return edge.getEndpoints();
	}
	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertEdge(net.datastructures.Vertex, net.datastructures.Vertex, java.lang.Object)
	 */
	//@Timecomplecity o(1)
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o)
			throws IllegalArgumentException {
		if (getEdge(u, v) == null) {
			InnerEdge<E> e = new InnerEdge<>(u, v, o);
			e.setPosition(edges.addLast(e));
			InnerVertex<V> origin = validate(u);
			InnerVertex<V> dest = validate(v);
			origin.getOutgoing().put(v, e);
			dest.getIncoming().put(u, e);
			return e;
		} else
			throw new IllegalArgumentException("Edge from u to v exists");
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertVertex(java.lang.Object)
	 */
	//@Timecomplecity o(1)
	public Vertex<V> insertVertex(V o) {
		InnerVertex<V> v = new InnerVertex<>(o, isDirected);
		v.setPosition(vertices.addLast(v));
		return v;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numEdges()
	 */
	public int numEdges() {
		return edges.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numVertices()
	 */
	public int numVertices() {
		return vertices.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#opposite(net.datastructures.Vertex, net.datastructures.Edge)
	 */
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
			throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();
		if (endpoints[0] == v)
			return endpoints[1];
		else if (endpoints[1] == v)
			return endpoints[0];
		else
			throw new IllegalArgumentException("v is not incident to this edge");
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeEdge(net.datastructures.Edge)
	 */
	//@Timecomplecity o(1)
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		// remove this edge from vertices' adjacencies

		InnerVertex<V> u = validate(edge.getEndpoints()[0]);
		InnerVertex<V> v = validate(edge.getEndpoints()[1]);

		u.getOutgoing().remove(v);
		v.getOutgoing().remove(u);

		// remove this edge from the list of edges
		edges.remove(edge.getPosition());
		edge.setPosition(null);
		edge.setElement(null);
		edge.endpoints = null;

	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeVertex(net.datastructures.Vertex)
	 */
	//@Timecomplecity o(1)
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		// remove all incident edges from the graph
		for (Edge<E> e : vert.getOutgoing().values())
			removeEdge(e);
		for (Edge<E> e : vert.getIncoming().values())
			removeEdge(e);
		// remove this vertex from the list of vertices
		vertices.remove(vert.getPosition());
	}

	/* 
     * replace the element in edge object, return the old element
     */
	public E replace(Edge<E> e, E o) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		return edge.setElement(o);
	}

    /* 
     * replace the element in vertex object, return the old element
     */
	public V replace(Vertex<V> v, V o) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.setElement(o);
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#vertices()
	 */
	public Iterable<Vertex<V>> vertices() {
		return vertices;
	}

	@Override
	public int outDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().size();
	}

	@Override
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().size();
	}

	@Override
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v)
			throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().values();
	}

	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v)
			throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().values();
	}

	@Override
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v)
			throws IllegalArgumentException {
		InnerVertex<V> origin = validate(u);
		return origin.getOutgoing().get(v);
	}
	
	private InnerVertex<V> validate(Vertex<V> v) {
		if (!(v instanceof InnerVertex))
			throw new IllegalArgumentException("Invalid vertex");
		InnerVertex<V> vert = (InnerVertex<V>) v; // safe cast
		return vert;
	}
	private InnerEdge<E> validate(Edge<E> e) {
		if (!(e instanceof InnerEdge))
			throw new IllegalArgumentException("Invalid edge");
		InnerEdge<E> edge = (InnerEdge<E>) e; // safe cast
		return edge;
	}
}

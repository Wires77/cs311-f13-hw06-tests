import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.anyOf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;

import edu.iastate.cs311.f13.hw6.IGraph;
import edu.iastate.cs311.f13.hw6.IGraph.Pair;
import edu.iastate.cs311.f13.hw6.IMaxFlowAlgorithms;

/**
 * Test max flow algorithms.
 */
public class TestMaxFlow {
    /** Class under test. */
    private IMaxFlowAlgorithms mMax;

    /**
     * Instantiat the class we will test.
     */
    @Before
    public final void setUp() {
        mMax = TestRunner.newMaxFlow();
    }

    /**
     * Calculate the source and sink flow of a given graph.
     * @param f Flow as a map from Pairs of vertices to the flow
     * @return An integer array of length 2. First position is source flow,
     * second is sink flow.
     */
    public static int[] calcFlow(final Map<Pair<String, String>, Integer> f) {
        HashMap<String, Integer> in = new HashMap<String, Integer>();
        HashMap<String, Integer> out = new HashMap<String, Integer>();
        HashSet<String> vertices = new HashSet<String>();

        int flow = 0;

        for (Entry<Pair<String, String>, Integer> e : f.entrySet()) {
            Pair<String, String> edge = e.getKey();
            String v = edge.first;
            String u = edge.second;
            int vTotal, uTotal;

            if (!in.containsKey(v)) {
                vTotal = 0;
            } else {
                vTotal = in.get(v);
            }

            if (!out.containsKey(u)) {
                uTotal = 0;
            } else {
                uTotal = out.get(u);
            }

            int x = (Integer) e.getValue();
            vTotal += x;
            uTotal += x;

            in.put(v, vTotal);
            out.put(u, uTotal);

            vertices.add(v);
            vertices.add(u);
        }

        int s = 0, t = 0;

        for (String v : vertices) {
            int i, o;

            if (in.containsKey(v)) {
                i = in.get(v);
            } else {
                i = 0;
            }

            if (out.containsKey(v)) {
                o = out.get(v);
            } else {
                o = 0;
            }

            if (i != 0 && o == 0) {
                s = i;
            } else if (i == 0 && o != 0) {
                t = o;
            }
        }

        int[] result = {
            s,
            t,
        };

        return result;
    }

    /**
     * Create path from argument list.
     * @param vertices to add to the path
     * @return List containing arguments
     */
    public static List<String> createPath(final String... vertices) {
        List<String> result = new ArrayList<String>();

        for (String v : vertices) {
            result.add(v);
        }

        return result;
    }

    /**
     * Test graph with two vertices but no edges.
     */
    @Test
    public final void testTwoVertexNoEdgesMaxFlow() {
        HashMap<Pair<String, String>, Integer> c = new HashMap<Pair<String, String>, Integer>();
        IGraph g = TestRunner.newGraph();

        String s = "A";
        String t = "B";

        g.addVertex(s);
        g.addVertex(t);

        int[] actual;
        int expected;

        Map<Pair<String, String>, Integer> max = mMax.maxFlow(g, s, t, c);

        actual = calcFlow(max);
        expected = 0;

        assertThat("Source outflow equals 0", actual[0], equalTo(expected));
        assertThat("Sink inflow equals 0", actual[1], equalTo(expected));
    }

    /**
     * Test graph with two vertices and a single edge between them.
     */
    @Test
    public final void testTwoVertexWithEdgeMaxFlow() {
        HashMap<Pair<String, String>, Integer> c = new HashMap<Pair<String, String>, Integer>();
        IGraph g = TestRunner.newGraph();

        String s = "A";
        String t = "B";

        Pair<String, String> e1 = new Pair<String, String>(s, t);

        g.addVertex(s);
        g.addVertex(t);

        g.addEdge(e1);
        c.put(e1, 9);

        int[] actual;
        int expected;

        Map<Pair<String, String>, Integer> max = mMax.maxFlow(g, s, t, c);

        actual = calcFlow(max);
        expected = 9;

        assertThat("Source outflow equals 9", actual[0], equalTo(expected));
        assertThat("Sink inflow equals 9", actual[1], equalTo(expected));
    }

    /**
     * Test max flow with triangle like graph.
     */
    @Test
    public final void testTriangleMaxFlow() {
        HashMap<Pair<String, String>, Integer> c = new HashMap<Pair<String, String>, Integer>();
        IGraph g = TestRunner.newGraph();

        String s = "A";
        String v2 = "B";
        String v3 = "C";
        String t = "D";

        Pair<String, String> e1 = new Pair<String, String>(s, v2);
        int e1C = 11;
        Pair<String, String> e2 = new Pair<String, String>(s, v3);
        int e2C = 7;
        Pair<String, String> e3 = new Pair<String, String>(v2, t);
        int e3C = 3;
        Pair<String, String> e4 = new Pair<String, String>(v3, t);
        int e4C = 5;

        g.addVertex(s);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(t);

        g.addEdge(e1);
        c.put(e1, e1C);

        g.addEdge(e2);
        c.put(e2, e2C);

        g.addEdge(e3);
        c.put(e3, e3C);

        g.addEdge(e4);
        c.put(e4, e4C);

        int[] actual;
        int expected;

        Map<Pair<String, String>, Integer> max = mMax.maxFlow(g, s, t, c);

        actual = calcFlow(max);
        expected = 8;

        assertThat("Source outflow equals 8", actual[0], equalTo(expected));
        assertThat("Sink inflow equals 8", actual[1], equalTo(expected));
    }

    /**
     * Test graph with more complex graph.
     *
     * Source:
     *      Intro to Algorithms, pg. 727, Figure 26.6
     */
    @Test
    public final void testComplexMaxFlow() {
        HashMap<Pair<String, String>, Integer> c = new HashMap<Pair<String, String>, Integer>();
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";
        String t = "t";

        Pair<String, String> e1 = new Pair<String, String>(s, v1);
        int e1C = 16;
        Pair<String, String> e2 = new Pair<String, String>(s, v2);
        int e2C = 13;
        Pair<String, String> e3 = new Pair<String, String>(v1, v3);
        int e3C = 12;
        Pair<String, String> e4 = new Pair<String, String>(v2, v1);
        int e4C = 4;
        Pair<String, String> e5 = new Pair<String, String>(v2, v4);
        int e5C = 14;
        Pair<String, String> e6 = new Pair<String, String>(v3, v2);
        int e6C = 9;
        Pair<String, String> e7 = new Pair<String, String>(v3, t);
        int e7C = 20;
        Pair<String, String> e8 = new Pair<String, String>(v4, v3);
        int e8C = 7;
        Pair<String, String> e9 = new Pair<String, String>(v4, t);
        int e9C = 4;

        g.addVertex(s);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(t);

        g.addEdge(e1);
        c.put(e1, e1C);

        g.addEdge(e2);
        c.put(e2, e2C);

        g.addEdge(e3);
        c.put(e3, e3C);

        g.addEdge(e4);
        c.put(e4, e4C);

        g.addEdge(e5);
        c.put(e5, e5C);

        g.addEdge(e6);
        c.put(e6, e6C);

        g.addEdge(e7);
        c.put(e7, e7C);

        g.addEdge(e8);
        c.put(e8, e8C);

        g.addEdge(e9);
        c.put(e9, e9C);

        int[] actual;
        int expected;

        Map<Pair<String, String>, Integer> max = mMax.maxFlow(g, s, t, c);

        actual = calcFlow(max);
        expected = 23;

        assertThat("Source outflow equals 23", actual[0], equalTo(expected));
        assertThat("Sink inflow equals 23", actual[1], equalTo(expected));
    }

    /**
     * Test vertex capacities, simple.
     */
    @Test
    public final void testVertexCapacitiesSimple() {
        HashMap<String, Integer> c = new HashMap<String, Integer>();
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String t = "t";

        Pair<String, String> e1 = new Pair<String, String>(s, t);

        g.addVertex(s);
        c.put(s, 10);

        g.addVertex(t);
        c.put(t, 1);

        g.addEdge(e1);

        int[] actual;
        int expected;

        Map<Pair<String, String>, Integer> max = mMax.maxFlowWithVertexCapacities(g, s, t, c);

        actual = calcFlow(max);
        expected = 1;

        assertThat("Source outflow equals 1", actual[0], equalTo(expected));
        assertThat("Sink inflow equals 1", actual[1], equalTo(expected));
    }

    /**
     * Test vertex disjoint, no edges.
     */
    @Test
    public final void testVertexDisjointNoEdges() {
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String t = "t";

        g.addVertex(s);
        g.addVertex(t);

        Matcher<?>[] disjointPaths = {
            equalTo(Collections.EMPTY_LIST),
        };

        Collection<List<String>> actual = mMax.maxVertexDisjointPaths(g, s, t);

        assertThat("Vertex disjoint with no edges", actual, anyOf(disjointPaths));
    }

    /**
     * Test vertex disjoint, two vertices.
     */
    @Test
    public final void testVertexDisjointTwoVertices() {
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String t = "t";

        Pair<String, String> e1 = new Pair<String, String>(s, t);

        g.addVertex(s);
        g.addVertex(t);

        g.addEdge(e1);

        Matcher<?>[] disjointPaths = {
            equalTo(createPath(s, t)),
        };

        Collection<List<String>> actual = mMax.maxVertexDisjointPaths(g, s, t);

        assertThat("Vertex disjoint with two vertices", actual, anyOf(disjointPaths));
    }

    /**
     * Test vertex disjoint, three vertices.
     */
    @Test
    public final void testVertexDisjointThreeVertices() {
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String v1 = "v1";
        String t = "t";

        Pair<String, String> e1 = new Pair<String, String>(s, v1);
        Pair<String, String> e2 = new Pair<String, String>(v1, t);

        g.addVertex(s);
        g.addVertex(v1);
        g.addVertex(t);

        g.addEdge(e1);
        g.addEdge(e2);

        Matcher<?>[] disjointPaths = {
            equalTo(createPath(s, v1, t)),
        };

        Collection<List<String>> actual = mMax.maxVertexDisjointPaths(g, s, t);

        assertThat("Vertex disjoint with three vertices", actual, anyOf(disjointPaths));
    }

    /**
     * Test vertex disjoint, two paths.
     */
    @Test
    public final void testVertexDisjointTwoSimplePaths() {
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String v1 = "v1";
        String v2 = "v2";
        String t = "t";

        Pair<String, String> e1 = new Pair<String, String>(s, v1);
        Pair<String, String> e2 = new Pair<String, String>(v1, t);

        Pair<String, String> e3 = new Pair<String, String>(s, v2);
        Pair<String, String> e4 = new Pair<String, String>(v2, t);

        g.addVertex(s);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(t);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        Matcher<?>[] disjointPaths = {
            equalTo(createPath(s, v1, t)),
            equalTo(createPath(s, v2, t)),
        };

        Collection<List<String>> actual = mMax.maxVertexDisjointPaths(g, s, t);

        assertThat("Vertex disjoint with two simple paths", actual, anyOf(disjointPaths));
    }

    /**
     * Test vertex disjoint, three paths, two with cardinality of 3.
     */
    @Test
    public final void testVertexDisjointThreeSimplePaths() {
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String v1 = "v1";
        String v2 = "v2";
        String t = "t";

        Pair<String, String> e1 = new Pair<String, String>(s, v1);
        Pair<String, String> e2 = new Pair<String, String>(v1, t);

        Pair<String, String> e3 = new Pair<String, String>(s, v2);
        Pair<String, String> e4 = new Pair<String, String>(v2, t);

        Pair<String, String> e5 = new Pair<String, String>(s, t);

        g.addVertex(s);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(t);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        Matcher<?>[] disjointPaths = {
            equalTo(createPath(s, v1, t)),
            equalTo(createPath(s, v2, t)),
        };

        Collection<List<String>> actual = mMax.maxVertexDisjointPaths(g, s, t);

        assertThat("Vertex disjoint, three paths, two with cardinality of 2", actual, anyOf(disjointPaths));
    }

    /**
     * Test vertex disjoint, four paths, one with cardinality of 4.
     */
    @Test
    public final void testVertexDisjointFourSimplePaths() {
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String t = "t";

        Pair<String, String> e1 = new Pair<String, String>(s, v1);
        Pair<String, String> e2 = new Pair<String, String>(v1, v2);
        Pair<String, String> e3 = new Pair<String, String>(v2, t);

        Pair<String, String> e4 = new Pair<String, String>(s, v3);
        Pair<String, String> e5 = new Pair<String, String>(v3, t);

        Pair<String, String> e6 = new Pair<String, String>(s, t);

        g.addVertex(s);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(t);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);

        Matcher<?>[] disjointPaths = {
            equalTo(createPath(s, v1, v2, t)),
        };

        Collection<List<String>> actual = mMax.maxVertexDisjointPaths(g, s, t);

        assertThat("Vertex disjoint, four paths, one with cardinality of 4", actual, anyOf(disjointPaths));
    }

    /**
     * Test vertex disjoint, overlapped paths, one with cardinality 3.
     */
    @Test
    public final void testVertexDisjointOverlapThreePaths() {
        IGraph g = TestRunner.newGraph();

        String s = "s";
        String v1 = "v1";
        String v2 = "v2";
        String t = "t";

        Pair<String, String> e1 = new Pair<String, String>(s, v1);
        Pair<String, String> e2 = new Pair<String, String>(v1, t);

        Pair<String, String> e3 = new Pair<String, String>(s, v2);
        Pair<String, String> e4 = new Pair<String, String>(v2, t);

        Pair<String, String> e5 = new Pair<String, String>(v1, v2);

        g.addVertex(s);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(t);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);

        Matcher<?>[] disjointPaths = {
            equalTo(createPath(s, v1, v2, t)),
        };

        Collection<List<String>> actual = mMax.maxVertexDisjointPaths(g, s, t);

        assertThat("Vertex disjoint, two overlapped, one with cardinality of 3", actual, anyOf(disjointPaths));
    }
}

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import org.hamcrest.Matcher;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;

import java.util.List;
import java.util.Arrays;

import edu.iastate.cs311.f13.hw6.IGraph;
import edu.iastate.cs311.f13.hw6.IGraph.Pair;
import edu.iastate.cs311.f13.hw6.ITopologicalSortAlgorithms;

/**
 * Test topological algorithms.
 */
public class TestTopologicalSort {
    @Test
    public final void testTopoForEmptyGraph() {
        IGraph g = TestRunner.newGraph();
        ITopologicalSortAlgorithms topo = TestRunner.newTopoSort();

        List<String> actual = topo.topologicalSort(g);

        assertTrue("Empty topo sort should be empty", actual.isEmpty());
    }

    @Test
    public final void testTopoForSimpleGraph() {
        IGraph g = TestRunner.newGraph();
        ITopologicalSortAlgorithms topo = TestRunner.newTopoSort();

        String v = "A";

        g.addVertex(v);

        List<String> expected = Arrays.asList(v);
        List<String> actual = topo.topologicalSort(g);

        assertThat("Simple topo sort for single vertex", actual, equalTo(expected));
    }

    @Test
    public final void testTopoForSimpleWithTwoVertices() {
        IGraph g = TestRunner.newGraph();
        ITopologicalSortAlgorithms topo = TestRunner.newTopoSort();

        String v = "A";
        String u = "B";

        g.addVertex(v);
        g.addVertex(u);

        Pair<String, String> e1 = new Pair<String, String>(v, u);

        g.addEdge(e1);

        List<String> expected = Arrays.asList(v, u);
        List<String> actual = topo.topologicalSort(g);

        assertThat("Simple topo sort with 2 vertices", actual, equalTo(expected));
    }

    @Test
    public final void testTopoExtreme() {
        IGraph g = TestRunner.newGraph();
        ITopologicalSortAlgorithms topo = TestRunner.newTopoSort();

        String v1 = "1";
        String v2 = "2";
        String v3 = "3";
        String v4 = "4";
        String v5 = "5";
        String v6 = "6";
        String v7 = "7";

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);

        Pair<String, String> e1 = new Pair<String, String>(v1, v4);
        Pair<String, String> e2 = new Pair<String, String>(v2, v3);
        Pair<String, String> e3 = new Pair<String, String>(v2, v4);
        Pair<String, String> e4 = new Pair<String, String>(v3, v4);
        Pair<String, String> e5 = new Pair<String, String>(v3, v7);
        Pair<String, String> e6 = new Pair<String, String>(v6, v7);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);

        Matcher<?>[] validTopos = {
            equalTo(Arrays.asList(v1, v2, v3, v4, v5, v6, v7)),
            equalTo(Arrays.asList(v2, v1, v3, v4, v5, v6, v7)),
            equalTo(Arrays.asList(v2, v3, v1, v4, v5, v6, v7)),
            equalTo(Arrays.asList(v1, v2, v3, v5, v4, v6, v7)),
            equalTo(Arrays.asList(v2, v1, v3, v5, v4, v6, v7)),
            equalTo(Arrays.asList(v2, v3, v1, v5, v4, v6, v7)),
            equalTo(Arrays.asList(v2, v3, v5, v1, v4, v6, v7)),
            equalTo(Arrays.asList(v1, v2, v5, v3, v4, v6, v7)),
            equalTo(Arrays.asList(v2, v1, v5, v3, v4, v6, v7)),
            equalTo(Arrays.asList(v2, v5, v1, v3, v4, v6, v7)),
            equalTo(Arrays.asList(v2, v5, v3, v1, v4, v6, v7)),
            equalTo(Arrays.asList(v1, v5, v2, v3, v4, v6, v7)),
            equalTo(Arrays.asList(v5, v1, v2, v3, v4, v6, v7)),
            equalTo(Arrays.asList(v5, v2, v1, v3, v4, v6, v7)),
            equalTo(Arrays.asList(v5, v2, v3, v1, v4, v6, v7)),
            equalTo(Arrays.asList(v1, v2, v3, v5, v6, v4, v7)),
            equalTo(Arrays.asList(v2, v1, v3, v5, v6, v4, v7)),
            equalTo(Arrays.asList(v2, v3, v1, v5, v6, v4, v7)),
            equalTo(Arrays.asList(v2, v3, v5, v1, v6, v4, v7)),
            equalTo(Arrays.asList(v2, v3, v5, v6, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v2, v5, v3, v6, v4, v7)),
            equalTo(Arrays.asList(v2, v1, v5, v3, v6, v4, v7)),
            equalTo(Arrays.asList(v2, v5, v1, v3, v6, v4, v7)),
            equalTo(Arrays.asList(v2, v5, v3, v1, v6, v4, v7)),
            equalTo(Arrays.asList(v2, v5, v3, v6, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v5, v2, v3, v6, v4, v7)),
            equalTo(Arrays.asList(v5, v1, v2, v3, v6, v4, v7)),
            equalTo(Arrays.asList(v5, v2, v1, v3, v6, v4, v7)),
            equalTo(Arrays.asList(v5, v2, v3, v1, v6, v4, v7)),
            equalTo(Arrays.asList(v5, v2, v3, v6, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v2, v5, v6, v3, v4, v7)),
            equalTo(Arrays.asList(v2, v1, v5, v6, v3, v4, v7)),
            equalTo(Arrays.asList(v2, v5, v1, v6, v3, v4, v7)),
            equalTo(Arrays.asList(v2, v5, v6, v1, v3, v4, v7)),
            equalTo(Arrays.asList(v2, v5, v6, v3, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v5, v2, v6, v3, v4, v7)),
            equalTo(Arrays.asList(v5, v1, v2, v6, v3, v4, v7)),
            equalTo(Arrays.asList(v5, v2, v1, v6, v3, v4, v7)),
            equalTo(Arrays.asList(v5, v2, v6, v1, v3, v4, v7)),
            equalTo(Arrays.asList(v5, v2, v6, v3, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v5, v6, v2, v3, v4, v7)),
            equalTo(Arrays.asList(v5, v1, v6, v2, v3, v4, v7)),
            equalTo(Arrays.asList(v5, v6, v1, v2, v3, v4, v7)),
            equalTo(Arrays.asList(v5, v6, v2, v1, v3, v4, v7)),
            equalTo(Arrays.asList(v5, v6, v2, v3, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v2, v3, v5, v6, v7, v4)),
            equalTo(Arrays.asList(v2, v1, v3, v5, v6, v7, v4)),
            equalTo(Arrays.asList(v2, v3, v1, v5, v6, v7, v4)),
            equalTo(Arrays.asList(v2, v3, v5, v1, v6, v7, v4)),
            equalTo(Arrays.asList(v2, v3, v5, v6, v1, v7, v4)),
            equalTo(Arrays.asList(v2, v3, v5, v6, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v2, v5, v3, v6, v7, v4)),
            equalTo(Arrays.asList(v2, v1, v5, v3, v6, v7, v4)),
            equalTo(Arrays.asList(v2, v5, v1, v3, v6, v7, v4)),
            equalTo(Arrays.asList(v2, v5, v3, v1, v6, v7, v4)),
            equalTo(Arrays.asList(v2, v5, v3, v6, v1, v7, v4)),
            equalTo(Arrays.asList(v2, v5, v3, v6, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v5, v2, v3, v6, v7, v4)),
            equalTo(Arrays.asList(v5, v1, v2, v3, v6, v7, v4)),
            equalTo(Arrays.asList(v5, v2, v1, v3, v6, v7, v4)),
            equalTo(Arrays.asList(v5, v2, v3, v1, v6, v7, v4)),
            equalTo(Arrays.asList(v5, v2, v3, v6, v1, v7, v4)),
            equalTo(Arrays.asList(v5, v2, v3, v6, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v2, v5, v6, v3, v7, v4)),
            equalTo(Arrays.asList(v2, v1, v5, v6, v3, v7, v4)),
            equalTo(Arrays.asList(v2, v5, v1, v6, v3, v7, v4)),
            equalTo(Arrays.asList(v2, v5, v6, v1, v3, v7, v4)),
            equalTo(Arrays.asList(v2, v5, v6, v3, v1, v7, v4)),
            equalTo(Arrays.asList(v2, v5, v6, v3, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v5, v2, v6, v3, v7, v4)),
            equalTo(Arrays.asList(v5, v1, v2, v6, v3, v7, v4)),
            equalTo(Arrays.asList(v5, v2, v1, v6, v3, v7, v4)),
            equalTo(Arrays.asList(v5, v2, v6, v1, v3, v7, v4)),
            equalTo(Arrays.asList(v5, v2, v6, v3, v1, v7, v4)),
            equalTo(Arrays.asList(v5, v2, v6, v3, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v5, v6, v2, v3, v7, v4)),
            equalTo(Arrays.asList(v5, v1, v6, v2, v3, v7, v4)),
            equalTo(Arrays.asList(v5, v6, v1, v2, v3, v7, v4)),
            equalTo(Arrays.asList(v5, v6, v2, v1, v3, v7, v4)),
            equalTo(Arrays.asList(v5, v6, v2, v3, v1, v7, v4)),
            equalTo(Arrays.asList(v5, v6, v2, v3, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v2, v3, v4, v6, v5, v7)),
            equalTo(Arrays.asList(v2, v1, v3, v4, v6, v5, v7)),
            equalTo(Arrays.asList(v2, v3, v1, v4, v6, v5, v7)),
            equalTo(Arrays.asList(v1, v2, v3, v6, v4, v5, v7)),
            equalTo(Arrays.asList(v2, v1, v3, v6, v4, v5, v7)),
            equalTo(Arrays.asList(v2, v3, v1, v6, v4, v5, v7)),
            equalTo(Arrays.asList(v2, v3, v6, v1, v4, v5, v7)),
            equalTo(Arrays.asList(v1, v2, v6, v3, v4, v5, v7)),
            equalTo(Arrays.asList(v2, v1, v6, v3, v4, v5, v7)),
            equalTo(Arrays.asList(v2, v6, v1, v3, v4, v5, v7)),
            equalTo(Arrays.asList(v2, v6, v3, v1, v4, v5, v7)),
            equalTo(Arrays.asList(v1, v6, v2, v3, v4, v5, v7)),
            equalTo(Arrays.asList(v6, v1, v2, v3, v4, v5, v7)),
            equalTo(Arrays.asList(v6, v2, v1, v3, v4, v5, v7)),
            equalTo(Arrays.asList(v6, v2, v3, v1, v4, v5, v7)),
            equalTo(Arrays.asList(v1, v2, v3, v6, v5, v4, v7)),
            equalTo(Arrays.asList(v2, v1, v3, v6, v5, v4, v7)),
            equalTo(Arrays.asList(v2, v3, v1, v6, v5, v4, v7)),
            equalTo(Arrays.asList(v2, v3, v6, v1, v5, v4, v7)),
            equalTo(Arrays.asList(v2, v3, v6, v5, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v2, v6, v3, v5, v4, v7)),
            equalTo(Arrays.asList(v2, v1, v6, v3, v5, v4, v7)),
            equalTo(Arrays.asList(v2, v6, v1, v3, v5, v4, v7)),
            equalTo(Arrays.asList(v2, v6, v3, v1, v5, v4, v7)),
            equalTo(Arrays.asList(v2, v6, v3, v5, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v6, v2, v3, v5, v4, v7)),
            equalTo(Arrays.asList(v6, v1, v2, v3, v5, v4, v7)),
            equalTo(Arrays.asList(v6, v2, v1, v3, v5, v4, v7)),
            equalTo(Arrays.asList(v6, v2, v3, v1, v5, v4, v7)),
            equalTo(Arrays.asList(v6, v2, v3, v5, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v2, v6, v5, v3, v4, v7)),
            equalTo(Arrays.asList(v2, v1, v6, v5, v3, v4, v7)),
            equalTo(Arrays.asList(v2, v6, v1, v5, v3, v4, v7)),
            equalTo(Arrays.asList(v2, v6, v5, v1, v3, v4, v7)),
            equalTo(Arrays.asList(v2, v6, v5, v3, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v6, v2, v5, v3, v4, v7)),
            equalTo(Arrays.asList(v6, v1, v2, v5, v3, v4, v7)),
            equalTo(Arrays.asList(v6, v2, v1, v5, v3, v4, v7)),
            equalTo(Arrays.asList(v6, v2, v5, v1, v3, v4, v7)),
            equalTo(Arrays.asList(v6, v2, v5, v3, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v6, v5, v2, v3, v4, v7)),
            equalTo(Arrays.asList(v6, v1, v5, v2, v3, v4, v7)),
            equalTo(Arrays.asList(v6, v5, v1, v2, v3, v4, v7)),
            equalTo(Arrays.asList(v6, v5, v2, v1, v3, v4, v7)),
            equalTo(Arrays.asList(v6, v5, v2, v3, v1, v4, v7)),
            equalTo(Arrays.asList(v1, v2, v3, v6, v5, v7, v4)),
            equalTo(Arrays.asList(v2, v1, v3, v6, v5, v7, v4)),
            equalTo(Arrays.asList(v2, v3, v1, v6, v5, v7, v4)),
            equalTo(Arrays.asList(v2, v3, v6, v1, v5, v7, v4)),
            equalTo(Arrays.asList(v2, v3, v6, v5, v1, v7, v4)),
            equalTo(Arrays.asList(v2, v3, v6, v5, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v2, v6, v3, v5, v7, v4)),
            equalTo(Arrays.asList(v2, v1, v6, v3, v5, v7, v4)),
            equalTo(Arrays.asList(v2, v6, v1, v3, v5, v7, v4)),
            equalTo(Arrays.asList(v2, v6, v3, v1, v5, v7, v4)),
            equalTo(Arrays.asList(v2, v6, v3, v5, v1, v7, v4)),
            equalTo(Arrays.asList(v2, v6, v3, v5, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v6, v2, v3, v5, v7, v4)),
            equalTo(Arrays.asList(v6, v1, v2, v3, v5, v7, v4)),
            equalTo(Arrays.asList(v6, v2, v1, v3, v5, v7, v4)),
            equalTo(Arrays.asList(v6, v2, v3, v1, v5, v7, v4)),
            equalTo(Arrays.asList(v6, v2, v3, v5, v1, v7, v4)),
            equalTo(Arrays.asList(v6, v2, v3, v5, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v2, v6, v5, v3, v7, v4)),
            equalTo(Arrays.asList(v2, v1, v6, v5, v3, v7, v4)),
            equalTo(Arrays.asList(v2, v6, v1, v5, v3, v7, v4)),
            equalTo(Arrays.asList(v2, v6, v5, v1, v3, v7, v4)),
            equalTo(Arrays.asList(v2, v6, v5, v3, v1, v7, v4)),
            equalTo(Arrays.asList(v2, v6, v5, v3, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v6, v2, v5, v3, v7, v4)),
            equalTo(Arrays.asList(v6, v1, v2, v5, v3, v7, v4)),
            equalTo(Arrays.asList(v6, v2, v1, v5, v3, v7, v4)),
            equalTo(Arrays.asList(v6, v2, v5, v1, v3, v7, v4)),
            equalTo(Arrays.asList(v6, v2, v5, v3, v1, v7, v4)),
            equalTo(Arrays.asList(v6, v2, v5, v3, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v6, v5, v2, v3, v7, v4)),
            equalTo(Arrays.asList(v6, v1, v5, v2, v3, v7, v4)),
            equalTo(Arrays.asList(v6, v5, v1, v2, v3, v7, v4)),
            equalTo(Arrays.asList(v6, v5, v2, v1, v3, v7, v4)),
            equalTo(Arrays.asList(v6, v5, v2, v3, v1, v7, v4)),
            equalTo(Arrays.asList(v6, v5, v2, v3, v7, v1, v4)),
            equalTo(Arrays.asList(v1, v2, v3, v4, v6, v7, v5)),
            equalTo(Arrays.asList(v2, v1, v3, v4, v6, v7, v5)),
            equalTo(Arrays.asList(v2, v3, v1, v4, v6, v7, v5)),
            equalTo(Arrays.asList(v1, v2, v3, v6, v4, v7, v5)),
            equalTo(Arrays.asList(v2, v1, v3, v6, v4, v7, v5)),
            equalTo(Arrays.asList(v2, v3, v1, v6, v4, v7, v5)),
            equalTo(Arrays.asList(v2, v3, v6, v1, v4, v7, v5)),
            equalTo(Arrays.asList(v1, v2, v6, v3, v4, v7, v5)),
            equalTo(Arrays.asList(v2, v1, v6, v3, v4, v7, v5)),
            equalTo(Arrays.asList(v2, v6, v1, v3, v4, v7, v5)),
            equalTo(Arrays.asList(v2, v6, v3, v1, v4, v7, v5)),
            equalTo(Arrays.asList(v1, v6, v2, v3, v4, v7, v5)),
            equalTo(Arrays.asList(v6, v1, v2, v3, v4, v7, v5)),
            equalTo(Arrays.asList(v6, v2, v1, v3, v4, v7, v5)),
            equalTo(Arrays.asList(v6, v2, v3, v1, v4, v7, v5)),
            equalTo(Arrays.asList(v1, v2, v3, v6, v7, v4, v5)),
            equalTo(Arrays.asList(v2, v1, v3, v6, v7, v4, v5)),
            equalTo(Arrays.asList(v2, v3, v1, v6, v7, v4, v5)),
            equalTo(Arrays.asList(v2, v3, v6, v1, v7, v4, v5)),
            equalTo(Arrays.asList(v2, v3, v6, v7, v1, v4, v5)),
            equalTo(Arrays.asList(v1, v2, v6, v3, v7, v4, v5)),
            equalTo(Arrays.asList(v2, v1, v6, v3, v7, v4, v5)),
            equalTo(Arrays.asList(v2, v6, v1, v3, v7, v4, v5)),
            equalTo(Arrays.asList(v2, v6, v3, v1, v7, v4, v5)),
            equalTo(Arrays.asList(v2, v6, v3, v7, v1, v4, v5)),
            equalTo(Arrays.asList(v1, v6, v2, v3, v7, v4, v5)),
            equalTo(Arrays.asList(v6, v1, v2, v3, v7, v4, v5)),
            equalTo(Arrays.asList(v6, v2, v1, v3, v7, v4, v5)),
            equalTo(Arrays.asList(v6, v2, v3, v1, v7, v4, v5)),
            equalTo(Arrays.asList(v6, v2, v3, v7, v1, v4, v5)),
            equalTo(Arrays.asList(v1, v2, v3, v6, v7, v5, v4)),
            equalTo(Arrays.asList(v2, v1, v3, v6, v7, v5, v4)),
            equalTo(Arrays.asList(v2, v3, v1, v6, v7, v5, v4)),
            equalTo(Arrays.asList(v2, v3, v6, v1, v7, v5, v4)),
            equalTo(Arrays.asList(v2, v3, v6, v7, v1, v5, v4)),
            equalTo(Arrays.asList(v2, v3, v6, v7, v5, v1, v4)),
            equalTo(Arrays.asList(v1, v2, v6, v3, v7, v5, v4)),
            equalTo(Arrays.asList(v2, v1, v6, v3, v7, v5, v4)),
            equalTo(Arrays.asList(v2, v6, v1, v3, v7, v5, v4)),
            equalTo(Arrays.asList(v2, v6, v3, v1, v7, v5, v4)),
            equalTo(Arrays.asList(v2, v6, v3, v7, v1, v5, v4)),
            equalTo(Arrays.asList(v2, v6, v3, v7, v5, v1, v4)),
            equalTo(Arrays.asList(v1, v6, v2, v3, v7, v5, v4)),
            equalTo(Arrays.asList(v6, v1, v2, v3, v7, v5, v4)),
            equalTo(Arrays.asList(v6, v2, v1, v3, v7, v5, v4)),
            equalTo(Arrays.asList(v6, v2, v3, v1, v7, v5, v4)),
            equalTo(Arrays.asList(v6, v2, v3, v7, v1, v5, v4)),
            equalTo(Arrays.asList(v6, v2, v3, v7, v5, v1, v4)),
        };

        List<Matcher<?>> expected = Arrays.asList(validTopos);
        List<String> actual = topo.topologicalSort(g);

        assertThat("Extreme topo sort test", actual, anyOf(expected));
    }
}

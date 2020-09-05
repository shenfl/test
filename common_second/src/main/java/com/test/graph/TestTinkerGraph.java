package com.test.graph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.util.List;
import java.util.Map;

/**
 * @author shenfl
 */
public class TestTinkerGraph {
    public static void main(String[] args) {
        // Create a new (empty) TinkerGrap
        TinkerGraph tg = TinkerGraph.open();

// Create a Traversal source object
        GraphTraversalSource g = tg.traversal();

// Add some nodes and vertices - Note the use of "iterate".
        g.addV("airport").property("code","AUS").as("aus").
                addV("airport").property("code","DFW").as("dfw").
                addV("airport").property("code","LAX").as("lax").
                addV("airport").property("code","JFK").as("jfk").
                addV("airport").property("code","ATL").as("atl").
                addE("route").from("aus").to("dfw").
                addE("route").from("aus").to("atl").
                addE("route").from("atl").to("dfw").
                addE("route").from("atl").to("jfk").
                addE("route").from("dfw").to("jfk").
                addE("route").from("dfw").to("lax").
                addE("route").from("lax").to("jfk").
                addE("route").from("lax").to("aus").
                addE("route").from("lax").to("dfw").iterate();

// Display the vertices created, note we have to use the "T." prefix
// for label and id as they are not stored as regular strings.

        List<Map<Object, Object>> list = g.V().valueMap(true).toList();
        list.forEach(System.out::println);

/* Just for fun!
for (v in vm)
{
  v.keySet().each {print "${v[it]} "}
  println()
}
*/

        System.out.println(String.format("The graph has %s vertices", g.E().count().next()));
        System.out.println(String.format("The graph has %s edges", g.V().count().next()));
    }
}

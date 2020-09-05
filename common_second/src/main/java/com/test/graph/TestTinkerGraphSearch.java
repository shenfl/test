package com.test.graph;

import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * https://github.com/krlawrence/graph/blob/master/sample-code/GraphSearch.java
 * @author shenfl
 */

public class TestTinkerGraphSearch
{
    private TinkerGraph tg;
    private GraphTraversalSource g;

    // Try to create a new graph and load the specified GraphML file
    public boolean loadGraph(String name) {
        tg = TinkerGraph.open() ;

        try
        {
            tg.io(IoCore.graphml()).readGraph(name);
        }
        catch( IOException e )
        {
            System.out.println("GraphStats - Air routes GraphML file not found");
            return false;
        }
        g = tg.traversal();
        return true;
    }

    private void simple() {
        System.out.println(g.V().count().next());
        List<Vertex> vertices = g.V().has("code", "AUS").out().toList();
        vertices.forEach(System.out::println);
        GraphTraversal<Vertex, Map<Object, Long>> by = g.V().hasLabel("airport").groupCount().by("country");
        GraphTraversal<Vertex, Path> by1 = g.V().has("code", "AUS").out().out().out().has("code", "AGR").path().by("city");
        while (by1.hasNext()) {
            Path next = by1.next();
            System.out.println(next);
        }
        GraphTraversal<Vertex, Object> values = g.V().has("airport", "code", "DFW").values("city");
        System.out.println("------");
        System.out.println(values);
        while (values.hasNext()) {
            System.out.println(values.next());
        }
        by1 = g.V().has("airport","code","LCY").outE().inV().path().by("code").by("dist").by("city");
        while (by1.hasNext()) {
            Path next = by1.next();
            System.out.println(next);
        }

    }

    // Return the distance between two airports.
    // Input parameters are the airport IATA codes.
    public Integer getDistance(String from, String to) {
        Integer d = (Integer)
                g.V().has("code",from).outE().as("edge").inV().has("code",to).
                        select("edge").by("dist").next();

        return(d);
    }

    // Return the distance between two airports.
    // Input parameters are the airport IATA codes.
    // If no route exists between the specified airports return -1
    public Integer getDistance1(String from, String to)
    {
        //The coalesce step avoids an exception when no result is found
        //Integer d = (Integer)
        //  g.V().has("code",from).outE().as("edge").inV().has("code",to).
        //        select("edge").by("dist").fold().
        //        coalesce(__.unfold(),__.constant(-1)).next();

        // Using toList() is another way to check for no result
        List<Object> result =
                g.V().has("code",from).outE().as("edge").inV().has("code",to).
                        select("edge").by("dist").toList();

        Integer d = ((result.isEmpty()) ? -1 : (Integer)(result.get(0)));

/*
    if (result.isEmpty())
    {
      System.out.println("No results were found");
    }
    else
    {
      System.out.println("The distance is " + result.get(0));
    }
*/
        return(d);
    }

    public Map<String, Object> findLongestRoute()
    {
        // Note how we need to prefix certain things that we would not have to
        // when using the Gremlin console with "__." and "Order."
        Map<String, Object> result =
                g.V().hasLabel("airport").outE("route").
                        order().by("dist", Order.decr).limit(1).
                        project("from","distance","to").
                        by(__.inV().values("code")).by("dist").by(__.outV().values("code")).next();

        return(result);
    }

    // Run some tests
    public static void main(String[] args)
    {
        TestTinkerGraphSearch gs = new TestTinkerGraphSearch();
        boolean loaded = gs.loadGraph("/Users/shenfl/IdeaProjects/test/common_second/src/main/resources/air-routes.graphml");

        gs.simple();
        if (loaded)
        {
            String[][] places = {{"AUS","LHR"},{"JFK","PHX"},{"SYD","LAX"},
                    {"PEK","HND"},{"HKG","MEL"},{"MIA","SFO"},
                    {"MNL","BKK"},{"DXB","DFW"},{"DOH","JNB"},
                    {"NRT","FRA"},{"AMS","GVA"},{"CDG","SIN"}};

            System.out.println("\n\nFrom    To   Distance");
            System.out.println("=====================");

            Integer dist;

            for (String[] p : places)
            {
                dist = gs.getDistance(p[0],p[1]);
                System.out.format("%4s   %4s   %5d\n",p[0],p[1],dist) ;
            }


            Map<String, Object> longest = gs.findLongestRoute() ;
            String s = "The longest route in the graph is between " + longest.get("from");
            s += " and " + longest.get("to") + " covering a distance of ";
            s += longest.get("distance") + " miles." ;

            System.out.println(s);

        }
    }
}
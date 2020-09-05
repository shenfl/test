package com.test.graph;

/**
 * @author shenfl
 */
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.*;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.*;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.util.Gremlin;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.io.*;
import java.util.function.Function;

public class GraphFromCSV
{

    private TinkerGraph tg;
    private GraphTraversalSource g;

    public void displayGraph()
    {
        Long c;
        c = g.V().count().next();
        System.out.println("Graph contains " + c + " vertices");
        c = g.E().count().next();
        System.out.println("Graph contains " + c + " edges");

        List<Path> edges = g.V().outE().inV().path().by("name").by().toList();

        for (Path p : edges)
        {
            System.out.println(p);
        }
    }


    // Open the sample csv file and build a graph based on its contents

    public static void main(String[] args) throws IOException {

        String yaml = "/Users/shenfl/IdeaProjects/test/common_second/src/main/resources/gdb.yaml";
        Cluster cluster = Cluster.build(new File(yaml)).create();
        GraphTraversalSource g =
                EmptyGraph.instance().traversal().
                        withRemote(DriverRemoteConnection.using(cluster));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(GraphFromCSV.class.getResourceAsStream("/air-routes-latest-nodes.csv")));
        String[] keys = bufferedReader.readLine().toLowerCase().split(",");


        String[] realKeys = new String[keys.length];
        Function<String, Object>[] functions = new Function[keys.length];
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].startsWith("~")) {
                realKeys[i] = keys[i].substring(1);
                continue;
            }
            String[] split = keys[i].split(":");
            realKeys[i] = split[0];
            switch (split[1]) {
                case "string":
                    functions[i] = a -> a;
                    break;
                case "int":
                    functions[i] = Integer::parseInt;
                    break;
                case "double":
                    functions[i] = Double::parseDouble;
                    break;
                default:
                    throw new RuntimeException("format error");
            }
        }


        String line;
        String[] values;
        while((line = bufferedReader.readLine()) != null)
        {
            //System.out.println(line);
            values = line.split(",");
            GraphTraversal<Vertex, Vertex> property = g.addV(values[1]).
                    property(T.id, values[0]);
            for (int i = 2; i < 6; i++) {
                try {
                    property = property.property(realKeys[i], functions[i].apply(values[i]));
                } catch (Exception e) {
                    System.out.println("error: " + line);
                }
            }
            try {
                property.iterate();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("eeee");
            }
        }
        bufferedReader.close();

        bufferedReader = new BufferedReader(new InputStreamReader(GraphFromCSV.class.getResourceAsStream("/air-routes-latest-edges.csv")));
        bufferedReader.readLine();
        while((line = bufferedReader.readLine()) != null) {
            //System.out.println(line);
            values = line.split(",");
            g.addE(values[3]).from(g.V(values[1])).to(g.V(values[2])).property("dest", Integer.valueOf(values[4])).iterate();
        }
        bufferedReader.close();

        cluster.close();
    }

    public static boolean addElements(GraphTraversalSource g, String name1, String label, String name2)
    {

        // Create an edge between 'name1' and 'name2' unless it exists already
//        g.V().has("name",name1).out(label).has("name",name2).fold().
//                coalesce(__.unfold(),
//                        __.addE(label).from(__.V(v1)).to(__.V(v2))).iterate();

        return true;
    }

}
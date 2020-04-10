package com.test.neo4j;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.util.Pair;

import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * Created by shenfl on 2018/7/18
 * 官网java driver
 */
public class TestNeoJavaDriver implements AutoCloseable {
    private final Driver driver;

    public TestNeoJavaDriver( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    public void close() throws Exception {
        driver.close();
    }

    public void printGreeting( final String message ) {
        try ( Session session = driver.session() ) {
            // write
//            String greeting = session.writeTransaction( new TransactionWork<String>() {
//                @Override
//                public String execute( Transaction tx ) {
//                    StatementResult result = tx.run( "CREATE (a:Greeting) " +
//                                    "SET a.message = $message " +
//                                    "RETURN a.message + ', from node ' + id(a)",
//                            parameters( "message", message ) );
//                    return result.single().get( 0 ).asString();
//                }
//            } );
//            System.out.println( greeting );

            // read
            String s = session.readTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction transaction) {
                    StatementResult run = transaction.run("MATCH (n:Customer) RETURN n LIMIT 25");
                    while (run.hasNext()) {
                        Record next = run.next();
                        Iterable<String> keys = next.get(0).keys();
                        for (String key : keys) {
                            System.out.println(key + ":" + next.get(0).get(key));
                        }
                        System.out.println(next.toString());
                    }
                    return "";
                }
            });
        }
    }

    public static void main( String... args ) throws Exception {
        try ( TestNeoJavaDriver greeter = new TestNeoJavaDriver( "bolt://172.17.41.96:7687", "neo4j", "admin" ) ) {
            greeter.printGreeting( "hello, world" );
        }
    }
}

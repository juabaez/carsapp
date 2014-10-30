package com.unrc.app;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

public class ElasticSearch {    
    private static final Node node = org.elasticsearch.node.NodeBuilder
                                            .nodeBuilder()
                                            .clusterName("carsapp")
                                            .local(true)
                                            .node();
    
    public static Client client(){
        return node.client();
    }
    
    public static void deleteAllIndexs(){
        client().admin().indices().delete(new DeleteIndexRequest("admins")).actionGet();
        client().admin().indices().delete(new DeleteIndexRequest("users")).actionGet();
        client().admin().indices().delete(new DeleteIndexRequest("posts")).actionGet();
        client().admin().indices().delete(new DeleteIndexRequest("vehicles")).actionGet();
    }
}

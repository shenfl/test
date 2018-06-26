package com.test.es.plugin;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.ElasticsearchClient;

/**
 * Created by shenfl on 2018/5/7
 */
public class TasteEventRequestBuilder extends ActionRequestBuilder<TasteEventRequest, TasteEventResponse, TasteEventRequestBuilder> {
    private SearchRequest searchRequest;

    protected TasteEventRequestBuilder(ElasticsearchClient client) {
        super(client, TasteEventAction.INSTANCE, new TasteEventRequest());
    }

    public void setSearchRequest(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
        request.setSearchRequest(searchRequest);
    }
}

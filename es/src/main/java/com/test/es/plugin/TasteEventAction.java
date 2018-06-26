package com.test.es.plugin;

import org.elasticsearch.action.Action;
import org.elasticsearch.client.ElasticsearchClient;

/**
 * Created by shenfl on 2018/5/7
 */
public class TasteEventAction extends Action<TasteEventRequest, TasteEventResponse, TasteEventRequestBuilder> {
    public static final String NAME="tastevent";
    public static final TasteEventAction INSTANCE = new TasteEventAction();
    protected TasteEventAction() {
        super(NAME);
        // TODO Auto-generated constructor stub
    }
    @Override
    public TasteEventRequestBuilder newRequestBuilder(ElasticsearchClient client) {
        // TODO Auto-generated method stub
        return new TasteEventRequestBuilder(client);
    }
    @Override
    public TasteEventResponse newResponse() {
        // TODO Auto-generated method stub
        return new TasteEventResponse();
    }
}
package com.test.es.plugin;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.StatusToXContentObject;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

import static org.elasticsearch.rest.RestStatus.OK;

/**
 * Created by shenfl on 2018/5/7
 */
public class TasteEventResponse extends ActionResponse implements StatusToXContentObject {
    private SearchResponse searchResponse;
    public TasteEventResponse(SearchResponse searchResponse) {
        // TODO Auto-generated constructor stub
        this.searchResponse = searchResponse;
    }
    public TasteEventResponse(){

    }
    public SearchResponse getSearchResponse() {
        return searchResponse;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, ToXContent.Params params) throws IOException {
        // TODO Auto-generated method stub
        if (searchResponse != null) {
            searchResponse.innerToXContent(builder, ToXContent.EMPTY_PARAMS);
        }
        return builder;
    }
    @Override
    public RestStatus status() {
        // TODO Auto-generated method stub
        return OK;
    }
}

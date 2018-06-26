package com.test.es.plugin;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;
import org.elasticsearch.rest.action.search.RestSearchAction;

import java.io.IOException;

/**
 * Created by shenfl on 2018/5/7
 */
public class TasteEventRestAction extends BaseRestHandler {

    protected TasteEventRestAction(Settings settings, RestController restController) {
        super(settings);
        restController.registerHandler(RestRequest.Method.GET, "/_taste/event", this);
        restController.registerHandler(RestRequest.Method.GET, "/{index}/_taste/event", this);
        restController.registerHandler(RestRequest.Method.GET, "/{index}/{type}/_taste/event", this);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        //构造自定义的request请求
        final TasteEventRequestBuilder actionBuilder=new TasteEventRequestBuilder(client);
        SearchRequest searchRequest=new SearchRequest();
        RestSearchAction.parseSearchRequest(searchRequest, request, null);
        actionBuilder.setSearchRequest(searchRequest);
        //实现项目功能，必须要用异步编程
        return channel -> client.execute(TasteEventAction.INSTANCE, actionBuilder.request(),
                new ActionListener<TasteEventResponse>() {
                    @Override
                    public void onResponse(TasteEventResponse response) {
                        try {
                            //将response转换成json类型，不过这个未转化成功
                            XContentBuilder builder = channel.newBuilder();
                            builder.startObject();
                            response.toXContent(builder, request);
                            builder.endObject();
                            channel.sendResponse(
                                    new BytesRestResponse(
                                            RestStatus.OK,
                                            builder));
                        } catch (Exception e) {
                            logger.debug("Failed to emit response.", e);
                            onFailure(e);
                        }
                    }
                    @Override
                    public void onFailure(Exception e) {
//                        emitErrorResponse(channel, logger, e); // todo
                    }
                });
    }
}

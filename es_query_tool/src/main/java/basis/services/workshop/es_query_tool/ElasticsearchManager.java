package basis.services.workshop.es_query_tool;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;

/**
 * This class manages Elasticsearch configuration and querying
 * 
 * @author pharding
 */
public class ElasticsearchManager {
    private static ElasticsearchManager instance;
    private static final String elastic_host = "localhost";
    private static final Integer elastic_port = 9200;
    private static final String index = "oclc";
    private RestHighLevelClient client;
    private static final int WINDOW  = 10;
    
    private ElasticsearchManager() {
        initialize();
    }
    
    /**
     * Gets a single instance/connection to the Elasticsearch cluster
     * 
     * @return 
     */
    public static ElasticsearchManager getInstance() {
        if (instance == null) {
            instance = new ElasticsearchManager();
        }

        return instance;
    }
        
    /**
     * This method configures the ES client
     */
    private void initialize() {
        try {
            client = new RestHighLevelClient(RestClient.builder(new HttpHost(elastic_host, elastic_port, "http")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method queries Elasticsearch with a RNI query and rescorer
     * 
     * @param query
     * @param rescorer
     * @return 
     */
    public SearchResponse singleQuery(AbstractQueryBuilder query, QueryRescorerBuilder rescorer) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(query)
            .addRescorer(rescorer)
            .explain(Boolean.TRUE)
            .size(WINDOW);
        
        searchRequest.source(sourceBuilder);
        
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return searchResponse;
    }
}

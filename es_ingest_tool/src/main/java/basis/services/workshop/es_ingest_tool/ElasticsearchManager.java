package basis.services.workshop.es_ingest_tool;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;


/**
 * This class manages all Elasticsearch indexing operations
 * 
 * @author pharding
 */
public class ElasticsearchManager {

    private static ElasticsearchManager instance;
    private static final String elastic_host = "localhost";
    private static final Integer elastic_port = 9200;
    private static final String index = "oclc";
    private RestHighLevelClient client;
    private int docID = 0;
    
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
     * This method indexes a single ES document
     * 
     * @param esDocument
     * @throws IOException 
     */
    public void singleIndex(XContentBuilder esDocument) throws IOException {
        
        IndexRequest request = new IndexRequest(index).id(String.valueOf(docID)).source(esDocument);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        docID++;
    }
    
    public void bulkIndex() {
        
    }
}

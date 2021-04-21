package basis.services.workshop.es_query_tool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;

/**
 * This class provides orchestrates the process of data, transforming the input, 
 * and queries an Elasticsearch index using the RNI plugin.
 * 
 * @author pharding
 */
public class Pipeline {

    private ElasticsearchManager es = ElasticsearchManager.getInstance();

    public Pipeline() {

    }

    /**
     * This method is responsible for orchestrating the execution of reading in
     * data, transforming it into a ES document, and querying it
     *
     */
    public void execute() {

        try {
            //Get data
            String filePath = System.getProperty("user.dir") + "/data/oclc_query_data.csv";
            Stream<String> stream = Files.lines(Paths.get(filePath)).skip(1);   //skip header

            //Process the dataset
            stream.forEach(line -> search(line));
            stream.close();
            System.out.println("QUERYING COMPLETE");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method takes in a line from a CSV file and transforms it into an RNI-ES
     * query and queries Elasticsearch
     *
     * @param line
     */
    private void search(String line) {
        
        //Transform the data into an ES query
        MatchQueryBuilder query = DataTransform.createQuery(line);
        QueryRescorerBuilder rescorer = DataTransform.createRescorer(line);
        
        SearchResponse response = es.singleQuery(query, rescorer);
        displayResults(line, response);
    }
    
    /**
     * This method takes the results and search terms and displays it 
     * 
     * @param line
     * @param response 
     */
    private void displayResults(String line, SearchResponse response) {
        for (SearchHit hit : response.getHits().getHits()) {
            
            //Get the RNI score of the hit
            float rni_score = hit.getScore();
            
            //Get the values from the hit
            Map<String, Object> source = hit.getSourceAsMap();
            Map rniMap = (HashMap) source.get("name");
            String indexedName = (String) rniMap.get("data");
            
            System.out.println("SEARCHED TERMS: " + line + " MATCH RECORD: " + indexedName + " RNI SCORE: " + rni_score);
        }
    }
}

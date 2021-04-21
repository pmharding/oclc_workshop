package basis.services.workshop.es_ingest_tool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * This class reads in data from a simple CSV, transforms it, and index it into
 * ES
 *
 * @author pharding
 */
public class Pipeline {

    private ElasticsearchManager es = ElasticsearchManager.getInstance();

    public Pipeline() {

    }

    /**
     * This method is responsible for orchestrating the execution of reading in
     * data, transforming it into a ES document, and indexing it
     *
     */
    public void execute() {

        try {
            //Get data
            String filePath = System.getProperty("user.dir") + "/data/oclc_test_data.csv";
            Stream<String> stream = Files.lines(Paths.get(filePath)).skip(1);   //skip header

            //Process the dataset
            stream.forEach(line -> indexRecord(line));
            stream.close();
            System.out.println("INDEXING COMPLETE");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method takes in a line from a CSV file and transforms it into
     * an ES document which is then indexed into Elasticsearch
     * 
     * @param line 
     */
    private void indexRecord(String line) {
        //Transform the data into an ES document
        XContentBuilder esDocument = DataTransform.transform(line);

        if (esDocument != null) {
            //Index the ES document
            try {
                es.singleIndex(esDocument);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package basis.services.workshop.es_ingest_tool;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * This class transforms an example PERSON record/identity into a XContentBuilder
 * object 
 * 
 * @author pharding
 */
public class DataTransform {

    /**
     * This method parses a comma seperated line of text and creates a
     * XContentBuilder object that matches the ES mapping for a particular index
     * 
     * @param data
     * @return 
     */
    public static XContentBuilder transform(String data) {
        XContentBuilder esDoc = null;
        
        //Parse the CSV line of text
        String[] splits = data.split(",");
        String name = splits[0];
        String dob = splits[1];
        String employer = splits[2];
        String pob = splits[3];
        
        //Build the ES document
        try {
        esDoc = XContentFactory.jsonBuilder();
        esDoc.startObject()
            .startObject("name")
                .field("data", name)
                .field("lang", "en")
                .field("entityType", "PERSON")
            .endObject()
            .startObject("dob")
                .field("data", dob)
                .field("lang", "en")
                .field("entityType", "DATE")
            .endObject()
            .startObject("employer")
                .field("data", employer)
                .field("lang", "en")
                .field("entityType", "ORGANIZATION")
            .endObject()
            .startObject("pob")
                .field("data", pob)
                .field("lang", "en")
                .field("entityType", "LOCATION")
            .endObject()
        .endObject();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return esDoc;
    }
}

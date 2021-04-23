package basis.services.workshop.es_ingest_tool;

import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This class transforms an example PERSON record/identity into a
 * XContentBuilder object
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

        //dataValidate()
        
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return esDoc;
    }


    public static XContentBuilder transformNested(String jsonData) throws Exception {
        XContentBuilder esDoc = null;
        String trimedJson = jsonData.substring(0, jsonData.length() - 1);

        JSONParser parser = new JSONParser();

        JSONObject json = (JSONObject) parser.parse(trimedJson);
        JSONArray names = (JSONArray) json.get("names");
        String birthdate = (String) json.get("birthdate");

        esDoc = XContentFactory.jsonBuilder()
            .startObject();
            esDoc.startArray("names");
            for (int i=0; i<names.size();i++) {
                JSONObject nameObj = (JSONObject)names.get(i);
                String n = (String)nameObj.get("name");
                 esDoc.startObject()
                    .startObject("name")
                        .field("data", n)
                        .field("lang", "en")
                        .field("entityType", "PERSON")
                    .endObject()
                .endObject();
            }
            esDoc.endArray();
            esDoc.startObject("birthdate")
                .field("data", birthdate)
                .field("lang", "en")
                .field("entityType", "DATE")
            .endObject();
        esDoc.endObject();

        System.out.println(Strings.toString(esDoc));
        
        return esDoc;
    }
}

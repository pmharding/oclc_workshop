package basis.services.workshop.es_ingest_tool;

/**
 * This is the starting point for the application
 * 
 * @author pharding
 */
public class Application {

    public Application() {
        
    }
    
    /**
     * Main method that calls and executes the pipeline for processing and 
     * indexing data
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Pipeline pipeline = new Pipeline();
        pipeline.execute();
    }
}

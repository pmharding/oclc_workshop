package basis.services.workshop.es_query_tool;

/**
 * This application is entry point into the application
 * 
 * @author pharding
 */
public class Application {
    
    public Application() {
        
    }
    
    /**
     * Main method that calls and executes the pipeline for processing and 
     * querying data
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Pipeline pipeline = new Pipeline();
        pipeline.execute();
        System.exit(0);
    }
}

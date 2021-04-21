package basis.services.workshop.es_query_tool;

import com.basistech.rni.es.DocScoreFunctionBuilder;
import com.basistech.rni.match.Name;
import com.basistech.rni.match.NameBuilder;
import com.basistech.rni.match.date.DateSpec;
import com.basistech.rni.match.date.DateSpecBuilder;
import com.basistech.util.NEConstants;
import com.basistech.util.Pathnames;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.rescore.QueryRescoreMode;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;

/**
 *
 * @author pharding
 */
public class DataTransform {

    private static String RNI_ROOT = "/Users/pharding/software/elastic/elasticsearch-7.4.2/plugins/rni/bt_root";
    private static final int WINDOW  = 10;

    /**
     * This method parses a line/record from a CSV line and creates the first pass
     * query
     * 
     * @param data
     * @return 
     */
    public static MatchQueryBuilder createQuery(String data) {
        //Parse the CSV line of text
        String[] splits = data.split(",");
        String name = splits[0];
        String dob = splits[1];

        MatchQueryBuilder query = QueryBuilders.matchQuery("name", name);
        return query;
    }

    /**
     * This method parses a line/record from a CSV line and creates the second pass
     * rescorer
     * 
     * @param data
     * @return 
     */
    public static QueryRescorerBuilder createRescorer(String data) {
        //Parse the CSV line of text
        String[] splits = data.split(",");
        String name = splits[0];
        String dob = splits[1];

        Pathnames.setBTRootDirectory(RNI_ROOT);
        Name n = NameBuilder.data(name).entityType(NEConstants.NE_TYPE_PERSON).build();
        DateSpec d = DateSpecBuilder.dateFromString(dob);
        
        DocScoreFunctionBuilder builder = new DocScoreFunctionBuilder()
                .queryName("name", n, 60)
                .queryDate("dob", d, 40);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builder);
        
        QueryRescorerBuilder rescorer = new QueryRescorerBuilder(functionScoreQueryBuilder);
        rescorer.setScoreMode(QueryRescoreMode.Max);
        rescorer.setQueryWeight(0.0f);
        rescorer.windowSize(WINDOW);

        return rescorer;
    }
}

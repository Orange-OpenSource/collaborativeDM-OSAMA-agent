import cartago.*;
import java.util.*;
import ora4mas.nopl.JasonTermWrapper;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.apache.jena.rdf.model.*;
import org.topbraid.spin.util.JenaUtil;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.topbraid.shacl.util.ModelPrinter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class Diagnosis extends Artifact 
{

    private Model kb;
	 
 	void init(String kbURL) 
	{     
	
        Path path = Paths.get(".").toAbsolutePath().normalize();
        String data = "file:" + path.toFile().getAbsolutePath() + "/src/"+kbURL;	 
        this.kb=ModelFactory.createDefaultModel();
        this.kb.read(data);    	     
        
	}

        @OPERATION
         void doDiagnosis(String deviceType, String failureCode, OpFeedbackParam<String> recoveryAction)
         {
               String recoveryTemp="";
               
               String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                             "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
                             "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                             "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"+
                             "PREFIX iotf: <http://www.semanticweb.org/orangeinnovation/IoTF#>\n"+
             "SELECT ?deviceType ?failureMode ?failureCause ?recoveryAction ?failureCode ?batteryLevel ?memoryUsage ?cpuUsage"+
	       "WHERE { ?failureMode rdf:type iotf:FailureMode."+
	               "?failureMode iotf:hasRecoveryAction ?recoveryAction."+
	               "?failureMode iotf:happensAt ?deviceType."+
	               "OPTIONAL {?failureMode iotf:hasFailureCode ?failureCode. }.OPTIONAL {?failureMode iotf:BatteryLevel ?batteryLevel. }.OPTIONAL {?failureMode iotf:MemoryUsage ?memoryUsage }.OPTIONAL {?failureMode iotf:CPUUsage ?cpuUsage}."+
              "Filter(?failureCode=\""+failureCode+"\")}";
                               
	    	    Query query = QueryFactory.create(queryString);
	    	    QueryExecution qe = QueryExecutionFactory.create(query, this.kb);
                    ResultSet results =  qe.execSelect();
                     while(results.hasNext()){ 
	    	    	QuerySolution binding = results.nextSolution(); 
                        recoveryTemp=binding.get("recoveryAction").toString();                              
 	    	    }    
                         recoveryAction.set(recoveryTemp.split("#")[1]);
         }

    
	



}
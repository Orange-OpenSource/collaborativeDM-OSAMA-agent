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



public class Time extends Artifact {

 
	 
	
	void init() {	 
         
	}

   
@OPERATION
void getTime(OpFeedbackParam<Long> time)
{
     time.set(System.currentTimeMillis());

}

@OPERATION
void getExecutionTime(Long startTime, Long endTime, OpFeedbackParam<Long> execTime )
{
     execTime.set(endTime-startTime);

}
 
}


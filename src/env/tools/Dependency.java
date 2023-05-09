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



public class Dependency extends Artifact {

 private HttpClient httpClient;
 private String token;
	 
	
	void init() {	 
         httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .proxy(ProxySelector.of(new InetSocketAddress("cs.pr-proxy.service.sd.diod.tech", 3128)))
                    //.proxy(ProxySelector.of(new InetSocketAddress("proxypac.si.francetelecom.fr",8080)))
                    .build();
       token=getTokenFromThingIN();
 
	}

    String getTokenFromThingIN()
    {

          HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://coreapi.thinginthefuture.com/auth"))
                .setHeader("accept", "text/plain")
                .setHeader("Use-Cache", "true")
                .setHeader("Authorization",  "Basic YW1hbC5ndWl0dG91bUBvcmFuZ2UuY29tOkFtb3VsYTE5OTYm")
                .build();
 
          try {
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            }
  
            // print response body
            return (response.body().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        }





    }
    @OPERATION
    void getDependency (String agent, OpFeedbackParam<Object[]> devices, OpFeedbackParam<Object[]> agents)
    {
          Object[] listAgents;
          Object[] listDevices;

        switch (agent)
        {

         case "orange" :
            listAgents=new Object[2];
            listDevices=new Object[2];

			listAgents[0] = new JasonTermWrapper("amazon");
            listDevices[0] = new JasonTermWrapper("d1");
		
            listAgents[1] = new JasonTermWrapper("microsoft");
            listDevices[1] = new JasonTermWrapper("d1");
		    agents.set(listAgents);
            devices.set(listDevices);

         break;
         
         case "amazon" :
            listAgents=new Object[1];
            listDevices=new Object[1];
			listAgents[0] = new JasonTermWrapper("samsung");
            listDevices[0] = new JasonTermWrapper("d2");
		 
		    agents.set(listAgents);
            devices.set(listDevices);
         break;

         case "samsung":
           /* list=new Object[1];
			list[0] = new JasonTermWrapper("vide");
            ls.set(list);*/
         break;
          case "microsoft":
           /* list=new Object[1];
			list[0] = new JasonTermWrapper("vide");
            ls.set(list);*/
         break;
        }
     
      
    }
    @OPERATION
    Boolean getdependNumber(String agent, OpFeedbackParam<Integer> nb)
    {
          switch (agent)
        {

         case "orange" :
             nb.set(2);
         break;
         
         case "amazon" :  
            nb.set(1);
         break;

         case "samsung":
         
            nb.set(0);
         break;

         case "microsoft":
          
            nb.set(0);
         break;
        }
        return true;
            
    }
    @OPERATION
    void getMNFomThingIN(String failedDevice, OpFeedbackParam<String> mn)
    {
         String req="{\n" +
                "    \"query\": [\n" +
                "        {\n" +
                "            \"$domain\": \"http://thingin.orange.com/thesisdemo/\",\n" +
                "            \"$iri\": \"http://thingin.orange.com/thesisdemo/"+failedDevice+"\",\n" +
                "             \n" +
                "            \"->http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#managedBy\" : \"var1\"\n" +
                "        },\n" +
                "        {\n" +
                "              \"$alias\" : \"var1\",\n" +
                "              \"http://xmlns.com/foaf/0.1/name\" : {\n" +
                "      \"$in\" :[\"MN\"]\n" +
                "    }\n" +
                " \n" +
                "         }\n" +
                "\n" +
                "     ],\n" +
                "    \"return\" : \"var1\",\n" +
                "\n" +
                "     \"view\": {}\n" +
                "}";

        //request thingIN

  HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(req))
                .uri(URI.create("https://coreapi.thinginthefuture.com/avatars/find/"))
                .setHeader("accept", "text/turtle")
                .setHeader("Cache-TTL", "0")
                .setHeader("Use-Cache", "false")
                .setHeader("jsonld_frame", "{   \"@context\": {     \"iot-access\": \"http://orange-labs.fr/fog/ont/access.owl#\",     \"dogont\": \"http://elite.polito.it/ontologies/dogont.owl#\",     \"org-iot\": \"http://orange-labs.fr/fog/ont/iot.owl#\",     \"xsd\": \"http://www.w3.org/2001/XMLSchema#\",     \"eupont\": \"http://elite.polito.it/ontologies/eupont.owl#\"   },   \"@type\": \"dogont:PlugwiseGateway\",   \"dogont:isIn\": {} }")
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Content-Type", "application/json")
                .build();

          try {
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            }
  
            // print response body
            mn.set(response.body().toString().split("<http://thingin.orange.com/thesisdemo/")[1].split(">")[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      

        //geTMN





    }

   @OPERATION
     void getDMPFomThingIN(String failedDevice, OpFeedbackParam<String> dmp )
    {
         String req="{\n" +
                "    \"query\": [\n" +
                "        {\n" +
                "            \"$domain\": \"http://thingin.orange.com/thesisdemo/\",\n" +
                "            \"$iri\": \"http://thingin.orange.com/thesisdemo/"+failedDevice+"\",\n" +
                "             \n" +
                "            \"->http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#managedBy\" : \"var1\"\n" +
                "        },\n" +
                "        {\n" +
                "              \"$alias\" : \"var1\",\n" +
                "              \"http://xmlns.com/foaf/0.1/name\" : {\n" +
                "      \"$in\" :[\"DMP\"]\n" +
                "    }\n" +
                " \n" +
                "         }\n" +
                "\n" +
                "     ],\n" +
                "    \"return\" : \"var1\",\n" +
                "\n" +
                "     \"view\": {}\n" +
                "}";

        //request thingIN

         HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(req))
                .uri(URI.create("https://coreapi.thinginthefuture.com/avatars/find/"))
                .setHeader("accept", "text/turtle")
                .setHeader("Cache-TTL", "0")
                .setHeader("Use-Cache", "false")
                .setHeader("jsonld_frame", "{   \"@context\": {     \"iot-access\": \"http://orange-labs.fr/fog/ont/access.owl#\",     \"dogont\": \"http://elite.polito.it/ontologies/dogont.owl#\",     \"org-iot\": \"http://orange-labs.fr/fog/ont/iot.owl#\",     \"xsd\": \"http://www.w3.org/2001/XMLSchema#\",     \"eupont\": \"http://elite.polito.it/ontologies/eupont.owl#\"   },   \"@type\": \"dogont:PlugwiseGateway\",   \"dogont:isIn\": {} }")
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Content-Type", "application/json")
                .build();

          try {
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            }
  
            // print response body
            dmp.set(response.body().toString().split("<http://thingin.orange.com/thesisdemo/")[1].split(">")[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      
         //geTMN

    }

    @OPERATION
    void getDependecyFromThingIN (String failedDevice, OpFeedbackParam<Object[]> devices)
    {
         //connect to Thing'in
        Model datatest = ModelFactory.createDefaultModel();
   
        Object[] listDevices;
        System.out.println(failedDevice);
        String domain="http://thingin.orange.com/thesisdemo/";
        //failedDevice="device1";
        String data = "{ \n \"query\": [ \n { \"$domain\" : \""+domain+"\", \"$iri\" : \""+domain+failedDevice+"\", \n \"->http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasServiceDependencyTo\": \"av1\" }, \n {\"$alias\": \"av1\", \n \"$classes\" :  \"http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#IoTDevice\" } ], \n \"return\" : \"av1\", \n \"view\": {} \n }";
  
         HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .uri(URI.create("https://coreapi.thinginthefuture.com/avatars/find/"))
                .setHeader("accept", "text/turtle")
                .setHeader("Cache-TTL", "0")
                .setHeader("Use-Cache", "false")
                .setHeader("jsonld_frame", "{   \"@context\": {     \"iot-access\": \"http://orange-labs.fr/fog/ont/access.owl#\",     \"dogont\": \"http://elite.polito.it/ontologies/dogont.owl#\",     \"org-iot\": \"http://orange-labs.fr/fog/ont/iot.owl#\",     \"xsd\": \"http://www.w3.org/2001/XMLSchema#\",     \"eupont\": \"http://elite.polito.it/ontologies/eupont.owl#\"   },   \"@type\": \"dogont:PlugwiseGateway\",   \"dogont:isIn\": {} }")
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            // print status code
           // System.out.println(response.statusCode());
           try {
          //  System.out.println(response.body());
            datatest.read(IOUtils.toInputStream(response.body(), "UTF-8"), null, "TURTLE");
            ResIterator iter = datatest.listSubjects();
            List<Resource> list = IteratorUtils.toList(iter);       

            int count = list.size();
            listDevices=new Object[count];       
                    
           for(int i=0; i<count; i++)
                    {
                        listDevices[i]=new JasonTermWrapper(list.get(i).toString().split(domain)[1]);
                        //listAgents[i]=new JasonTermWrapper(list.get(i).getProperty(datatest.getProperty("http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#managedBy")).getObject().toString());
                        //listAgents[i]=new JasonTermWrapper(getDMPFomThingIN(list.get(i).toString().split(domain)[1]));
                    }

		    //agents.set(listAgents);
            devices.set(listDevices);

         } 
            catch (IOException e) 
            {
            e.printStackTrace();
            }
        } 
        
        catch (InterruptedException e) {
            e.printStackTrace();
       
        }


    }

    @OPERATION
    Boolean getNumberOfDependecyFromThingIN (String failedDevice, OpFeedbackParam<Integer> nb)
    {
         //connect to Thing'in
        Model datatest = ModelFactory.createDefaultModel();
    
        String domain="http://thingin.orange.com/thesisdemo/";
        //failedDevice="device1";
        String data = "{ \n \"query\": [ \n { \"$domain\" : \""+domain+"\", \"$iri\" : \""+domain+failedDevice+"\", \n \"->http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#hasServiceDependencyTo\": \"av1\" }, \n {\"$alias\": \"av1\", \n \"$classes\" :  \"http://www.semanticweb.org/OrangeLab/ontologies/2021/9/IoTD#IoTDevice\" } ], \n \"return\" : \"av1\", \n \"view\": {} \n }";
  
         HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .uri(URI.create("https://coreapi.thinginthefuture.com/avatars/find/"))
                .setHeader("accept", "text/turtle")
                .setHeader("Cache-TTL", "0")
                .setHeader("Use-Cache", "false")
                .setHeader("jsonld_frame", "{   \"@context\": {     \"iot-access\": \"http://orange-labs.fr/fog/ont/access.owl#\",     \"dogont\": \"http://elite.polito.it/ontologies/dogont.owl#\",     \"org-iot\": \"http://orange-labs.fr/fog/ont/iot.owl#\",     \"xsd\": \"http://www.w3.org/2001/XMLSchema#\",     \"eupont\": \"http://elite.polito.it/ontologies/eupont.owl#\"   },   \"@type\": \"dogont:PlugwiseGateway\",   \"dogont:isIn\": {} }")
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            // print status code
           // System.out.println(response.statusCode());
           try {
          //  System.out.println(response.body());
            datatest.read(IOUtils.toInputStream(response.body(), "UTF-8"), null, "TURTLE");
            ResIterator iter = datatest.listSubjects();
            List<Resource> list = IteratorUtils.toList(iter);       

            int count = list.size();
            nb.set(count);  
            return true;
 
         } 
            catch (IOException e) 
            {
            e.printStackTrace();
            }
             return true;
        } 
        
        catch (InterruptedException e) {
            e.printStackTrace();
       
        }
           return true;

    }


    @OPERATION
    void getAgent (String device, OpFeedbackParam<String> agent)
    {
        switch (device)
        {
         case "device1" :
             agent.set("orange");
         break;
         
         case "device2" :  
            agent.set("amazon");
         break;

         case "device3":
          agent.set("microsoft");
         break;

         case "device4":
          agent.set("samsung");
         break;
        }

    }
      @OPERATION
    void getMonitoringIndicator(String device, OpFeedbackParam<String> code)
    {
        switch (device)
        {
         case "leakDetector" :
             code.set("0x8564000");
         break;
         
         case "alarm" :  
            code.set("0x3432");
         break;

         case "smokeSensor" :  
            code.set("0x0010");
         break;

         case "airconditioner" :  
            code.set("0x0006");
         break;

         case "temperatureSensor" :  
            code.set("0x0005");
         break;

         case "motionSensor" :  
            code.set("0x0001");
         break;

         case "device1" :
             code.set("0x8564001");
         break;

          case "device2" :
             code.set("0x8564002");
         break;
          case "device3" :
             code.set("0x8564003");
         break;
          case "device4" :
             code.set("0x8564004");
         break;
          case "device5" :
             code.set("0x8564005");
         break;
          case "device6" :
             code.set("0x8564006");
         break;
          case "device7" :
             code.set("0x8564007");
         break;
          case "device8" :
             code.set("0x8564008");
         break;
          case "device9" :
             code.set("0x8564009");
         break;
          case "device10" :
             code.set("0x85640010");
         break;
          case "device11" :
             code.set("0x85640011");
         break;
          case "device12" :
             code.set("0x85640012");
         break;
          case "device13" :
             code.set("0x85640013");
         break;
          case "device14" :
             code.set("0x85640014");
         break;
          case "device15" :
             code.set("0x85640015");
         break;
          case "device16" :
             code.set("0x85640016");
         break;
          case "device17" :
             code.set("0x85640017");
         break;

        }
        

    }

 
 
}


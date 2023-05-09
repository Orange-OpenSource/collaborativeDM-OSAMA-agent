!start. // initial goal
  

+!start : true <- 
				?name(N);
				.print("i'm the DM manager ", N).



+!diagnosis(F,C)[source(D)]<- doDiagnosis(F,C,A);
				             .print("Proposed recovery Action ",A);
				             .send(D, tell, recover(F,A)).
				   
 {include("$jacamoJar/templates/common-cartago.asl")}
 {include("$jacamoJar/templates/common-moise.asl")}
 {include("$jacamoJar/templates/org-obedient.asl")}
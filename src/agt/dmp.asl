!start. // initial goal
  

+!start : true <- 
				?name(N);
				.print("I'm the DM manager ", N).

+failure(D,F,C)  <-   ?name(N);
					  getTime(H);
                     .print("A failure detected by ",N,"on the device ",D,"its type ",F);
					 .print("Get the MN of the device ",D);
					  getMNFomThingIN(D,J);
                     .print("Request diagnosis from agent ", J);
					 .send(J, achieve, diagnosis(F,C));
					 .wait({+recover(F,R)});
					 .print("Executing ", R, "on ", D);
					 //.print("Recover..............."); //call for recovery artifacts.
					 .print("Still having a failure");
					 .print("Start cascading failure recovery"); 
                    // getDependency(N,X, Z);
					  getDependecyFromThingIN(D,Y);
					  .print(" dependencies ", Y);
	                 for (.list(Y) & .member(M, Y) )  {
						   getDMPFomThingIN(M, A);
						  .print("send Check request to : ", A);
                          .send(A, achieve, check(M));
                      };
					 .wait({+!receiveAllResponses});
					 .print("End of Check request");
					 .print("Recover..............."); //call for recovery artifacts.*/
					  getTime(Z);
					  getExecutionTime(H, Z, E);
					 .print("Execution Time", " ", E, " ms").


+endCheck(_, _, X):  .findall(b(D,N), endCheck(D, N, _), L) & .length(L, X) <- .print(X, " EndCheck received");
								 							                   !receiveAllResponses.

//+endCheck(_, _):  .findall(b(D,N), endCheck(D, N), L) & .length(L, 1) <- .print(" EndCheck received");
								 							                    //!receiveAllResponses.			

+!receiveAllResponses<- .print("all responses arrived !");
                        .abolish(endCheck(_,_,_)); 
						.abolish(failure(_,_,_)).
						 

+!check(D)[source(A)] <- .print("A failure arrives !");
 						 .print("Diagnosis..............."); //call for recovery artifacts.
						 .print("Get the MN of the device ",D);
					      getMNFomThingIN(D,J);
						 .print("Get monitoring indicators");
                         .print("Request diagnosis from agent ", J);
						  getMonitoringIndicator(D,C);
					     .send(J, achieve, diagnosis("xx",C));
					     .wait({+recover(F,R)});
					     .print("Executing ", R, "on ", D);
					     //.print("Recover..............."); //call for recovery artifacts.
					    .print("Still having a failure");
					    .print("Start cascading failure recovery"); 
						 ?name(N);
                          //	getDependency(N,X,Z);
						 getDependecyFromThingIN(D,Y);
						  .print(" dependencies ", Y);
						 if(not .length(Y, 0)) 
						 	{ 
								.print("Request help from other agents");
								 for (.list(Y) & .member(M, Y) )  {
								getDMPFomThingIN(M, G);
                          	    .send(G, achieve, check(M));    
                             	};	
							    .print("Wait for endCheck from dependent agents....");
							    .wait({+!receiveAllResponses});
								.print("send end check to the requester ", A);
								// getdependNumber(A, S);//to fix
								.length(Y,S);
							//	.print("S= ",S);
                                .send(A, tell, endCheck(D, N, S));
								//.send(A, tell, endCheck(D,N));
				             }
					     	 else { 
							  .print("send end check to the requester ", A);
							   //getdependNumber(A, S);//to fix
							   //.length(Y,S);
							   //.print("S= ",S);
                               .send(A, tell, endCheck(D, N, S));
							   //.send(A, tell, endCheck(D,N));

						 	 }.


							/* tests :-)*/

+testStructure <- ?name(N); 
				  getDependency(N, X, Z);
				  for ( .list(X)  & .member(Y,X) ) {
						  .print("agent : ", M, " device: ", Y);
                  }.

+testNumberDependency <- .my_name(A); getdependNumber(A, X)	; .print("number of depenndecies ", X).			 
						   
+testdp<-   ?depends(X);
			print("number of dependencies : ", X).

+testIntention <- .wait({+!receiveAllResponses}); .print("unlock").

+testThingIN <- ?name(N);
                getDependecyFromThingIN(N,Y,Z);
				for (  .member(M, Z) | .member(L, Y))  {
						  .print("agent : ", M);
						  .print("device : ", L);
                       }.
+getDependency(D) <- ?name(N);
                     getDependecyFromThingIN(D,Y);
				     for (  .member(L, Y))  {
						  .print("device : ", L);
						  getDMPFomThingIN(L,A);
						   .print("agent : ", A);	  
                       }.

//getDependency(alarm).
+testDiagnosis(T, C) <- doDiagnosis(T,C,F);
						.print(F).

//testDiagnosis(AlarmXM,"0x3432").
+testMN(D)<- getMNFomThingIN(D,J);
			 .print(J).

+testnumber(D): getNumberOfDependecyFromThingIN(D, X) <- .print(X).

 
//to fix length = dependencies size
//+endCheck(_,_): .findall(b(D,N), endCheck(D, N), L) & .my_name(N) & getDependency(N,X, Z) & .length(L, .length(Z)) <- .print("EndCheck received"); 
																													  //!receiveAllResponses.	

//+endCheck(_, _) <- .findall(b(D, N), endCheck(D, N), L); .length(L, X); .print("number of dependenies ", X).

 {include("$jacamoJar/templates/common-cartago.asl")}
 {include("$jacamoJar/templates/common-moise.asl")}
 {include("$jacamoJar/templates/org-obedient.asl")}
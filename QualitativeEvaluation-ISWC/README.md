# Qualitative Evaluation For ISWC conference
Qualitative Evaluation Materials proposed for the ISWC conference.
The Qualitative Evaluation consists on Managing a set of cascading failure scenarios in a Smart Home using five OSAMA agents: Orange, Amazon, Phillips, Kelvin and Samsung.
<p align="center">
<img src="https://github.com/Orange-OpenSource/collaborativeDM-OSAMA-agent/blob/master/smartHome.png" width="300" height="250">
</p> 

## Content
```
collaborativeDM-OSAMA-agent/QualitativeEvaluation-ISWC/
├───The Smart Home Dependency Knowledge Graph (DKG)
└───The Failure Knowledge Base (FKB) of the Kelvin ,Phillips and Orange OSAMA Agent.
└───Cascading Failure Management Traces <representing message exchange between OSAMAs to solve the cascading failure scenarios>.
└───Deployement Steps allowing to reproduce the management of Cascading Failure Scenario 01.
```
## Deployment Steps
1. Get Developper Access to the Orange platform [Thing'in](https://tech2.thinginthefuture.com/) by contacting amal.guittoum@orange.com
3. Use your Thing'in access token by updating the line 42 in the file[Dependency.java](src/env/tools/Dependency.java)
```
.setHeader("Authorization",  "Basic [YOUR Token]")
```
4. Build the Docker IMAGE [DockerFile](DockerFile)
```
sudo docker build --no-cache --file=dockerFile --tag=osama-agent --rm=true .
```
5. Run the Docker for each OSAMA agent in a seperate terminal

```
docker run -ti --rm osama-agent jacamo amazon.jcm
docker run -ti --rm osama-agent jacamo phillips.jcm
docker run -ti --rm osama-agent jacamo kelvin.jcm
docker run -ti --rm osama-agent jacamo orange.jcm

```
6. Observe message exchange between agents managing the following casacding failure scenarios
<p align="center">
<img src="https://github.com/Orange-OpenSource/collaborativeDM-OSAMA-agent/blob/master/smartHome.png" width="300" height="250">
</p> 

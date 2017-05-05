# BioModelsWeb
This is a classwork application user interface for this semester's course Database Application Development.

The Requirement is to implement an web-based database application to store XML file of a [bio-model](http://www.ebi.ac.uk/biomodels-main/BIOMD0000000011).

####Tasks including:
+ file stroge in database
+ information extraction in XML file
+ fetch and update data in database via JDBC
+ a web page as user interface
+ to retrieve elements' relationships within an XML file
    - the implementation is to draw a bipartite graph to represent reactions between materials

####Other notes:
+ The project is deployed on a Tomcat 8.5.* web server, with Postgresql 9.4(remote)/9.5(local) database.
+ Front-side interface is originated to the [Mimic](http://peterchon.github.io/mimic/) CSS framework (though it "isn't a framework").

####Demo page:
Available on my personal [website](http://cirqqq.xyz:8080/BioModels).
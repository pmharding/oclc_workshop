# oclc_workshop
This repository contains two simple Java applications.   

# es_ingest_tool
This application is for loading and transforming data into Elasticsearch using the Rosette Name Indexer plugin.
This project has the following project dependencies:
  - elasticsearch 7.4.2
  - elasticsearch-rest-client 7.4.2
  - elasticsearch-rest-high-level-client 7.4.2

To execute the application: Run Application.java

# es_query_tool
This application loads and transforms data into an RNI-ES query and searchs an Elasticsearch index, then displays the 
the results to standard output.

To build and run this application you need to add the follow local JARS included the RNI plugin.
First, create a local lib folder directory with the following JARs(not the version of the JARs might be different):
  - rni-es-7.4.2.0.jar
  - rni-rnt-bundle-7.30.1.c62.0.jar
  - btcommon-api-37.0.1.ja
  - btcommon-lib-38.0.2.jar
  - slf4j-api-1.6.4.jar

Second, install these JARs to your .m2 repository by executing the following commands:

mvn install:install-file -Dfile=<path_to_lib_folder>/rni-es-7.4.2.0.jar -DgroupId=oclc.workshop -DartifactId=rni-es -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=<path_to_lib_folder>/rni-rnt-bundle-7.30.1.c62.0.jar -DgroupId=oclc.workshop -DartifactId=rni-rnt-bundle -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=/<path_to_lib_folder>/btcommon-api-37.0.1.jar -DgroupId=oclc.workshop -DartifactId=btcommon-api -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=<path_to_lib_folder>/btcommon-lib-38.0.2.jar -DgroupId=oclc.workshop -DartifactId=btcommon-lib -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=<path_to_lib_folder>/slf4j-api-1.6.4.jar -DgroupId=oclc.workshop -DartifactId=slf4j-api -Dversion=1.0 -Dpackaging=jar

Lastly, modify the RNI_ROOT variable the DataTransform.java to the local instance of the plugin.

Run Application.java

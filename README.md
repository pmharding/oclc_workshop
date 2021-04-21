# oclc_workshop
This repository contains two simple Java applications to demonstrate simple RNI-ES index and querying.   
Both applications will require the following dependencies (note the version of ES and the RNI plugin must match):
  - elasticsearch <version_num>
  - elasticsearch-rest-client <version_num>
  - elasticsearch-rest-high-level-client <version_num>

# es_ingest_tool
This application is for loading and transforming data into Elasticsearch using the Rosette Name Indexer plugin.

To execute the application: Run Application.java

# es_query_tool
This application loads and transforms data into an RNI-ES query and searchs an Elasticsearch index, then displays the 
the results to standard output.

To build and run this application you need to add the follow local JARS included the RNI plugin.
First, create a local lib folder directory with the following JARs(not the version of the JARs might be different):
  - rni-es-<version_num>.jar
  - rni-rnt-bundle-<version_num>.jar
  - btcommon-api-<version_num>.ja
  - btcommon-lib-<version_num>.jar
  - slf4j-api-<version_num>.jar

Second, install these JARs to your .m2 repository by executing the following commands:

mvn install:install-file -Dfile=<path_to_lib_folder>/rni-es-<version_num>.jar -DgroupId=oclc.workshop -DartifactId=rni-es -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=<path_to_lib_folder>/rni-rnt-bundle-<version_num>.jar -DgroupId=oclc.workshop -DartifactId=rni-rnt-bundle -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=<path_to_lib_folder>/btcommon-api-<version_num>.jar -DgroupId=oclc.workshop -DartifactId=btcommon-api -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=<path_to_lib_folder>/btcommon-lib-<version_num>.jar -DgroupId=oclc.workshop -DartifactId=btcommon-lib -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=<path_to_lib_folder>/slf4j-api-<version_num>.jar -DgroupId=oclc.workshop -DartifactId=slf4j-api -Dversion=1.0 -Dpackaging=jar

Lastly, modify the RNI_ROOT variable the DataTransform.java to the local instance of the plugin.

Run Application.java

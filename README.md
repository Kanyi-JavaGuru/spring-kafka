# spring-kafka
Demo showing how to configure and use spring-kafka which is used to create messaging systems or streaming apps
##Instructions
how to install kafka
download from https://www.apache.org/dyn/closer.cgi?path=/kafka/1.1.0/kafka_2.12-1.1.0.tgz

unzip to c: folder
open cmd

###1- go to kafka directory using the following commands and start zookeeper
cd\
cd kafka_2.12-1.1.0
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

###2-start apache kafka in new cmd
cd\
cd kafka_2.12-1.1.0
.\bin\windows\kafka-server-start.bat .\config\server.properties

###3-create new topic. open new cmd and go to kafka installation and create topic helloworldtopic
cd\
cd kafka_2.12-1.1.0
.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic helloworldtopic

###4.1-view all created topics
.\bin\windows\kafka-topics.bat --describe --zookeeper localhost:2181

###5-create producer. open new cmd and go to kafka installation and send message
cd\
cd kafka_2.12-1.1.0
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic helloworldtopic
hello

###6-create consumer. open new cmd and go to kafka installation
cd\
cd kafka_2.12-1.1.0
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic helloworldtopic --from-beginning


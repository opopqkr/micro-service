## Data synchronization between Microservices

### With a Message queuing server (feat. Apache kafka)

### - Apache Kafka

- Apache Software Foundation의 Scalar 언어로 된 오픈 소스 메시지 브로커 프로젝트
  - Open Source Message Broker Project
- 링크드인(Linked-in)에서 개발, 2011년 오픈 소스화
  - 2014년 11월 링크드인에서 Kafka를 개발하던 엔지니어들이 Kafka 개발에 집중하기 위해 Confluent 회사 창립
- 각기 다른 DB, File System, Application등 간의 End-to-End 연동 방식으로 아키텍처의 복잡성을 해소하고자 개발
  - 연동하고자 하는 target system과는 무관하게 Kafka와만의 연동으로 복잡성을 해소 (단일 포맷)
- 실시간 데이터 피드를 관리하기 위해 통일된 높은 처리량, 낮은 지연 시간을 지닌 플랫폼 제공
- Apple, Netflix, Shopify, Yelp, Kakao, New York Times, Uber 등이 사용

> - Producer/Consumer 분리하여 작업
> - 전달받은 하나의 메시지를 여러 Consumer들에게 전달 가능
> - 높은 처리량을 위한 메시지 최적화
> - Clustering 구조로, 여러 개의 서버로 구성하여 기동 (Scale-out 가능)
> - Eco-system

---

#### Kafka Broker

- 실행 된 Kafka application server
- 3대 이상의 Broker Clustering 구성 권장
  - Broker간의 데이터가 복제될 때 시간 차이로 인해 일부 데이터가 유실될 가능성이 있음
  - 즉, 한 개의 Broker에서 장애가 발생하더라도 지속적으로 데이터 처리가 가능
- N개 Broker 중 1대는 Controller(leader) 기능 수행
  - Controller 역할
    - 각 Broker에게 담당 파티션 할당 수행
    - Broker 정상 동작 모니터링 관리
- Zookeeper 연동
  - Broker 간의 중재(컨트롤) 역할로 메타데이터 (Broker ID, Controller ID등) 저장
  - Controller 정보 저장

#### Eco-system 1. Kafka Client

- Kafka와 데이터를 주고받기 위해 사용하는 java library
- Producer, Consumer, Admin, Stream 등 Kafka 관련 API 제공
- 다양한 3rd party library : java, c/c++, node.js, python, .net 등
  - https://cwiki.apache.org/confluence/display/KAFKA/Clients

> #### Kafka 기본 실행 명령어
> **1. Zookeeper & Kafka 서버 구동**
> - $KAFKA_HOME\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
> - $KAFKA_HOME\bin\windows\kafka-server-start.bat .\config\server.properties
> 
> **2. Topic**
> - Topic 목록 확인
>   - $KAFKA_HOME\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list
>     - --bootstrap-server option : 서버 위치 지정
>      
> 
> - Topic 상세 확인
>   - $KAFKA_HOME\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic test-events
>   
> 
> - Topic 생성
>   - $KAFKA_HOME\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic test-events --partitions 1
>     - --create --topic ${topic name} option : 토픽 명을 지정 후 토픽 생성
>     - --partitions option : multi clustering 구성 시, 파티셔닝 갯수
>     
> 
> - Producer 실행 (메시지 발행)
>   - $KAFKA_HOME\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test-events
> 
> 
> - Consumer 실행 (메시지 구독)
>   - $KAFKA_HOME\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-events --from-beginning
>     - --from-beginning option : message를 처음부터 읽어 들이는 옵션


#### Eco-system 2. Kafka Connect

- Kafka Connect를 통해 Data Import/Export 가능
- 코드 없이 Configuration file만으로 데이터 이동 가능
- Standalone mode, Distribution mode 지원
  - RESTful API 지원
  - Stream 또는 Batch 형태로 데이터 전송 가능
  - 커스텀 Connector를 통한 다양한 Plugin 제공 (File, AWS S3, Hive, MySQL, etc..)

> #### Kafka Connect 설치 및 드라이버 설정 방법 (JDBC Connect, Mariadb 사용 기준)
> **1. Kafka Connect 설치**
> - curl -O http://packages.confluent.io/archive/6.1/confluent-community-6.1.0.tar.gz
> 
> **2. JDBC Connector 설치**
> 1. https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc 에서 다운로드 및 압축해제
> 2. Kafka Connect 설정에 JDBC Connector 플러그인 경로 지정 
>    1. $KAFKA_CONNECT_HOME\etc\kafka\connect-distributed.properties file 실행 </br>
>    2. connect-distributed.properties 최하단의 plugin.path 수정 
>       - plugin.path=$JDBC_CONNECTOR_HOME (confluentinc-kafka-connect-jdbc-{version} 경로) \lib
> 3. Jdbc Source Connector에서 Mariadb를 사용하기 위한 mariadb-jdbc-client.jar 드라이버 추가
>    - $KAFKA_CONNECT_HOME\share\java\kafka\mariadb-java-client-{version}.jar file
>  

> #### Kafka Connect 기본 실행 명령어
> **1. Kafka Connect 실행 (Zookeeper 및 Kafka 서버가 실행되어 있어야 함)**
> - $KAFKA_CONNECT_HOME\bin\windows\connect-distributed.bat .\etc\kafka\connect-distributed.properties
>   - Kafka Connect 실행 시 생성되는 Topic 목록
>     - connect-config
>     - connect-offset
>     - connect-status


---
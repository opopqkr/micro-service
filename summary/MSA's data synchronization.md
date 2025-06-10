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
- 
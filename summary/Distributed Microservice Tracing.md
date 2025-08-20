## Distributed Microservice Tracing

### - Zipkin

- Twitter에서 사용하는 분산 환경의 Timing 데이터 수집, 추적 시스템 (오픈소스)
- 분산환경에서의 시스템 병목 현상 파악
- Collector, Query Service, Database WebUI 구성
- Span
  - 하나의 요청에 사용되는 작업의 단위
  - 64bit unique ID
- Trace
  - 트리 구조로 이루어진 Span 셋
  - 하나의 요청에 대한 같은 Trace ID 발급
- https://zipkin.io/

> #### Install & Boot Zipkin ####
> - curl -sSL https://zipkin.io/quickstart.sh | bash -s
> - java -jar zipkin.jar

### - Spring Cloud Sleuth

- 스프링 부트 애플리케이션을 Zipkin과 연동
- 요청 값에 따른 Trace ID, Span ID 부여
- Trace, Span Ids를 로그에 추가하여 Zipkin server 전달
  - servlet filter
  - rest template
  - scheduled ac tions
  - message channels
  - feigh client

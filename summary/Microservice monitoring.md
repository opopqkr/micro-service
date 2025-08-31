## Microservice monitoring

### - Turbine Server

- 마이크로서비스에 설치된 Hystrix 클라이언트의 스트림을 통합
  - 마이크로서비스에서 생성되는 Hystrix 클라이언트 스트림 메시지를 Turbine Server로 수집
- Hystrix Dashboard는 Hystrix 클라이언트에서 생성하는 스트림을 시각화
- <b>Zipkin/Sleuth는 마이크로서비스간의 요청 로깅 및 추적의 목적(요청 단위 추적)</b>이며, </br>
  <b>Hystrix/Turbine은 마이크로서비스의 상태 및 성능 모니터링 목적(서비스 상태 모니터링)</b>

> #### Hystrix ####
> - Netflix에서 만든 오픈소스 라이브러리로, </br>
    > 마이크로서비스 환경에서 분산 서비스 간의 장애 전파를 막고 </br>
    > 시스템의 안정성을 높이기 위한 서킷 브레이커(Circuit Breaker) 라이브러리
> - 현재 Hystrix는 Deprecated 되었으며 Resilience4j 라이브러리로 대체


- #### Hystrix / Trubine 한계점
  - Turbine은 Hystrix 스트림만 집계가 가능하며, CPU 및 메모리, DB Connection Pool과 같은 메트릭은 수집불가
  - Circuit Breaker 상태만 볼수 있는 제한된 모니터링 도구
  - 실시간 스트림 정보만 확인핧 수 있으며, 데이터를 저장하지 않음
  - 시계열로 도식화를 하기 위해서는 별도의 외부 DB에 따로 정보를 적재 필요
  - <b><u>이러한 한계점들로 인해 Hystrix/Turbine 대신 Spring에서는 다른 모니터링 도구를 권장</u></b>
    - <b>Micrometer + Monitoring System (Prometheus/Grafana)</b>

---

### - Micrometer

- https://micrometer.io
- JVM 기반의 애플리케이션 Metrics 제공
- Spring Framework 5, Spring Boot 2부터 Spring의 Metrics 처리
- Prometheus등의 다양한 모니터링 시스템 지원
- <b><u>Timer</u></b>
  - 짧은 지연 시간, 이벤트의 사용 빈도를 등록 및 측정하기 위한 Class
  - 시계열로 이벤트의 시간, 호출빈도 등을 제공
  - @Timed annotation 제공

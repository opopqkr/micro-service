## What is Microservice Architecture?

### Monolithic Architecture

- 모든 비지니스 로직이 하나의 어플리케이션 형태로 패키징 되어 서비스
- 어플리케이션에서 사용하는 데이터가 한 곳에 모여 참조·집중되어 서비스되는 형태
- 필요한 모든 서비스들은 하나의 어플리케이션에서 제공되기 때문에 복잡한 통신 없이 직접 사용할 수 있음
- 어플리케이션의 일부 기능만을 수정한다 하더라도 전체 어플리케이션을 다시 빌드, 테스트, 패키징하는 과정을 거쳐야 함

---

### Microservice Architecture

- 여러 개의 작은 서비스로 구성되어 각 서비스가 독립적으로 개발되고 배포되는 구조
- 전체 어플리케이션이 분산되어 있어 개발, 배포가 독립적으로 가능하며 확장성과 유지관리가 용이함
- 기능 고립성이라는 특징 때문에 일부 서비스가 실패하더라도 전체 시스템에 큰 영향을 미치지 않음
- 서비스 간 통신이 필요하며, 서로 간의 연결 구축 및 관리의 복잡성이 증가

> **Microservice Features**
> - Challenges
> - Small Well Chosen Deployable Units
> - Bounded Context
> - RESTFul
> - Configuration Management
> - Cloud Enabled
> - Dynamic Scale UP & Scale Down
> - CI/CD
> - Visibility

---

### Spring Cloud 

- 기존 모놀리식 개발 방식이 아닌, 서비스를 독립적으로 개발하기 위한 <b><u>마이크로서비스 아키텍처를 지원하는 Spring Framework</u></b> (https://spring.io/projects/spring-cloud)
- 환경 설정 관리, 서비스 검색, 회복성 처리, 라우팅, 프록시 등 다양한 서비스 기능을 지원하여 분산 시스템에 적합한 어플리케이션을 빠르게 개발하는데 목적을 두고 만들어 짐.

- Spring cloud 어플리케이션 구축 기본 요소
  - Spring Cloud Config Server : 중앙집중식 구성 관리 (환경 설정)
  - Naming Server (Eureka) : 서비스 등록 및 위치정보 확인, 검색 등 (서비스 위치 투명성)
  - Spring Cloud Gateway / Ribbon (Client Side) : 부하 분산을 위한 로드 밸런싱, 게이트웨이
  - RestTemplate / FeignClient : 서비스간 통신
  - Zipkin / Netflix API gateway / ELK : 시각화 및 모니터링 (로그 및 분산 추적)
  - Hystrix : 장애 발생 회복 (결함 내성)

> **Note**
> - MSA 12-factor (https://12factor.net/)
> - Cloud Native Computing Foundation CNCF (https://www.cncf.io/)
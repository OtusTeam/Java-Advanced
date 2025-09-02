# Код для лекции по актуатору

service/actuator - демонстрация возможности spring actuator
service/admin - админская часть для spring actuator
service/load демонстрация работы сервиса, работает  связке с service/randomanswer
service/randomanswer - возвращает случные ответы в том числе ошибочные
service/metrics - демонстрация работы с метриками
service/trace - демонстрация работы с трейсами

prometheus http://localhost:9090
grafana http://localhost:3000
admin http://localhost:8084/
api loki https://grafana.com/docs/loki/latest/reference/loki-http-api/
api tempo https://grafana.com/docs/tempo/latest/api_docs/

дашборды (https://grafana.com/grafana/dashboards/)
 - jvm https://grafana.com/grafana/dashboards/4701-jvm-micrometer/
 - observability https://grafana.com/grafana/dashboards/17175-spring-boot-observability/
# documents

Курсовая работа по разработке серверных частей интернет ресурсов

## Билд и запуск pr3_t2.Dockerfile

`docker-compose build`

`docker-compose up`

## ZABBIX
Мониторит нагрузку на систему

логин: Admin
пароль: zabbix

## GRAFANA

логин: admin
пароль: admin

https://dev.to/nelsoncode/how-to-monitor-posgresql-with-prometheus-and-grafana-docker-24c8

### Import JSON or code

https://grafana.com/grafana/dashboards/9628

Импортируем из grafana.com 9628 (дашборд сообщества)

## GRAYLOG

логин: admin
пароль: admin

## Выгрузка логов

http://localhost:9000/api/search/universal/relative/export?query=*&range=1000&fields=message

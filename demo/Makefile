.PHONY: help build run clean db-up db-down logs

APP_NAME=demo

help:
	@echo "make build     - собрать проект"
	@echo "make run       - запустить Spring Boot"
	@echo "make clean     - очистить target"
	@echo "make db-up     - поднять БД (docker-compose)"
	@echo "make db-down   - остановить БД"
	@echo "make logs      - логи БД"

build:
	./mvnw clean package

run:
	./mvnw spring-boot:run

clean:
	./mvnw clean

db-up:
	docker compose up -d

db-down:
	docker compose down

logs:
	docker compose logs -f

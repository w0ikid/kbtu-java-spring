include .env
export

.PHONY: infra up down

infra:
	docker-compose up -d

infra-down:
	docker-compose down

up:
	chmod +x book-service/mvnw member-service/mvnw notification-service/mvnw
	tmux new-session -d -s book 'cd book-service && ./mvnw spring-boot:run'
	tmux new-session -d -s member 'cd member-service && ./mvnw spring-boot:run'
	tmux new-session -d -s notification 'cd notification-service && ./mvnw spring-boot:run'
	@echo "Use 'tmux attach -t book' to see logs"

down:
	tmux kill-session -t book || true
	tmux kill-session -t member || true
	tmux kill-session -t notification || true

logs-book:
	tmux attach -t book

logs-member:
	tmux attach -t member

logs-notification:
	tmux attach -t notification
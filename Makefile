include .env

.PHONY:
build-db:
	podman build -f Dockerfile -t com.example.k83/test-sample-db:$(APP_DB_VER) ./container/db

.PHONY:
build-app: build-db
	./gradlew clean build bootBuildImage --imageName=com.example.k83/test-sample-app:$(APP_VER)

.PHONY:
test: build-db
	./gradlew test

.PHONY:
run: build-app
	podman compose -f docker-compose.yaml up -d

.PHONY:
e2e: build-app
	podman compose -f docker-compose-test.yaml up --abort-on-container-exit --exit-code-from e2e

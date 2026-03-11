.PHONY:
build-image:
	./gradlew clean build bootBuildImage --imageName=com.example.k83/test-sample-app:0.1.0

.PHONY:
test:
	./gradlew test

.PHONY:
run:
	podman compose -f docker-compose.yaml up -d

.PHONY:
e2e:
	podman compose -f docker-compose-test.yaml up -d
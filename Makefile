.PHONY:
build-image:
	./gradlew clean build bootBuildImage --imageName=com.example.k83/test-app:0.1.0
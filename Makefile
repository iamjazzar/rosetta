SHELL := /usr/bin/env bash
.PHONY: help requirements clean emulator quality test validate e2e artifacts

help :
	@echo ''
	@echo 'Makefile for the edX Android application'
	@echo 'Usage:'
	@echo '    make help            show these information'
	@echo '    make clean           remove artifacts from previous usage'
	@echo '    make deploy          deploy the new version to Bintray'
	@echo ''

clean :
	@echo 'Cleaning the workspace and any previously created AVDs'
	./gradlew clean
	rm -Rf $$HOME/.android/avd/screenshotDevice.avd
	rm -f $$HOME/.android/avd/screenshotDevice.ini


deploy:
	@./gradlew install
	@./gradlew bintrayUpload

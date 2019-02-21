#!/usr/bin/env groovy
// triggered by GitHub in Jenkins-Pipeline

// global variables
def REGISTRY_URL='https://dockerregistry.eigenbaumarkt.com'
def REGISTRY_USER='dockerregistry-login'
def DOCKER_IMG_NAME='mesqualito/gen_commerce'
def CONTAINER_TAG='0.0.5'
// def CONTAINER_HTTP_PORT='8080'

node {
    stage('checkout') {
        checkout scm
    }

    docker.image('jhipster/jhipster:v5.8.1').inside('-u jhipster -e GRADLE_USER_HOME=.gradle') {
        stage('check java') {
            sh "java -version"
        }

        stage('clean') {
            sh "chmod +x gradlew"
            sh "./gradlew clean --no-daemon"
        }

        stage('npm install') {
            sh "./gradlew npm_install -PnodeInstall --no-daemon"
        }

        stage('backend tests') {
            try {
                sh "./gradlew test -PnodeInstall --no-daemon"
            } catch(err) {
                throw err
            } finally {
                junit '**/build/**/TEST-*.xml'
            }
        }

        stage('frontend tests') {
            try {
                sh "./gradlew npm_run_test -PnodeInstall --no-daemon"
            } catch(err) {
                throw err
            } finally {
                junit '**/build/test-results/TESTS-*.xml'
            }
        }

        stage('packaging') {
            sh "./gradlew bootWar -x test -Pprod -PnodeInstall --no-daemon"
            archiveArtifacts artifacts: '**/build/libs/*.war', fingerprint: true
        }

    }

    def dockerImage
    stage('build docker') {
        sh "cp -R src/main/docker build/"
        sh "cp build/libs/*.war build/docker/"
        dockerImage = docker.build("$DOCKER_IMG_NAME:$CONTAINER_TAG", "build/docker")
    }

    stage('publish docker') {
        docker.withRegistry($REGISTRY_URL, $REGISTRY_USER) {
            dockerImage.push $CONTAINER_TAG
        }
    }

    stage('Remove Unused docker image') {
        steps{
            sh "docker rmi $CONTAINER_NAME:$CONTAINER_TAG"
        }
    }
}

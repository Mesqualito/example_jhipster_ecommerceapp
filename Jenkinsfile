#!/usr/bin/env groovy

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
        dockerImage = docker.build('docker-login/store', 'build/docker')
    }

    stage('publish docker') {
        docker.withRegistry('https://dockerregistry.eigenbaumarkt.com', '35c08f27-4962-4992-b840-4f81d69cb966') {
            dockerImage.push 'latest'
        }
    }
}

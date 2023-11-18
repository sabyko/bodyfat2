#!/usr/bin/env groovy

node {

	stage('check old ROOT.war') {
        script {
            if (fileExists('/opt/tomcat/webapps/ROOT.war')) {
                echo "old /opt/tomcat/webapps/ROOT.war found!"				
				sh "rm /opt/tomcat/webapps/ROOT.war" 
            } else {
				echo "no ROOT.war found!"
            }
        }
    }

    stage('check old h2') {
        script {
            if (fileExists('/opt/tomcat/webapps/opt')) {
                echo "old /opt/tomcat/webapps/opt found!"				
				sh "rm /opt/tomcat/webapps/opt" 
            } else {
				echo "no opt folder found!"
            }
        }
    }
    
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "chmod +x mvnw"
        sh "./mvnw -ntp clean -P-webapp"
    }
    stage('nohttp') {
        sh "./mvnw -ntp checkstyle:check"
    }

    stage('install tools') {
        sh "./mvnw -ntp com.github.eirslett:frontend-maven-plugin:install-node-and-npm@install-node-and-npm"
    }

    stage('npm install') {
        sh "./mvnw -ntp com.github.eirslett:frontend-maven-plugin:npm"
    }
    stage('backend tests') {
        try {
            sh "./mvnw -ntp verify -P-webapp"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
        }
    }

    stage('frontend tests') {
        try {
            sh "./mvnw -ntp com.github.eirslett:frontend-maven-plugin:npm -Dfrontend.npm.arguments='run test'"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/test-results/TESTS-results-jest.xml'
        }
    }

    stage('packaging') {
        sh "./mvnw -ntp verify -P-webapp -Pdev -DskipTests"
        archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    }
    stage('quality analysis') {
        withSonarQubeEnv('sonar') {
            sh "./mvnw -ntp initialize sonar:sonar"
        }
    }
}

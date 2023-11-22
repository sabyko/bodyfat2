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

    stage('packaging') {
        sh "./mvnw -ntp verify -P-webapp -Pprod -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }
    
    stage('copy war') {
        sh "cp ./target/bodyfat-0.0.1-SNAPSHOT.war /opt/tomcat/webapps/ROOT.war"
        sleep 20
    }

}

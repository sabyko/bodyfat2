#!/usr/bin/env groovy

node {

    stage('gatling tests') {
        try {
    //      sh "./mvnw gatling:test -DbaseURL=http://192.168.178.119:8080"
        } catch(err) {
           throw err
        } finally {
    //       gatlingArchive()
        }
    }

    stage('cypress tests') {
            sh "./concurrently -k -s first \"npm run ci:e2e:server:start\" \"npm run e2e:headless\"
    }

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

    stage('backend tests') {
        try {
     //       sh "./mvnw -ntp verify -P-webapp"
        } catch(err) {
            throw err
        } finally {
     //       junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
        }
    }

    stage('frontend tests') {
        try {
     //       sh "./mvnw -ntp com.github.eirslett:frontend-maven-plugin:npm -Dfrontend.npm.arguments='run test'"
        } catch(err) {
            throw err
        } finally {
    //       junit '**/target/test-results/TESTS-results-jest.xml'
        }
    }


    stage('packaging') {
        sh "./mvnw -ntp verify -P-webapp -Pprod -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }
    

    stage('copy war') {
        sh "cp ./target/bodyfat-0.0.1-SNAPSHOT.war /opt/tomcat/webapps/ROOT.war"
    }

    stage('quality analysis') {
        withSonarQubeEnv('sonar') {
           sh "./mvnw sonar:sonar -Dsonar.host.url=http://localhost:9001"
        }
    }
}

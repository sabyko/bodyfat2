#!/usr/bin/env groovy

node {

    stage('Sonar Test') {
            def sonarOutput = sh ("./mvnw sonar:sonar -Dsonar.host.url=http://192.168.178.119:9001", returnStdout: true).trim()
                if (sonarOutput.contains('ANALYSIS SUCCESSFUL')) {
                        
                    echo "SonarQube analysis was successful!"

                        // Extract and save the SonarQube dashboard link
                        def dashboardLink = sonarOutput =~ /ANALYSIS SUCCESSFUL, you can find the results at: (.+)/
                        if (dashboardLink) {
                            def urlToSave = dashboardLink[0][1]
                            echo "SonarQube Dashboard Link: ${urlToSave}"

                            // Save the URL to a file
                            writeFile file: DASHBOARD_FILE, text: urlToSave
                        }
                    } else {
                        error "SonarQube analysis failed! Check the build logs for more details."
        }
    }
        

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
    //        sh "./npm run ci:e2e:run"
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

    
    post {
    always {
        cleanWs()
    }
    success {
        slackSend(color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        bitbucketStatusNotify(buildState: 'SUCCESSFUL')
    }

    failure {
        slackSend(color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        bitbucketStatusNotify(buildState: 'FAILED')
    }
}
}

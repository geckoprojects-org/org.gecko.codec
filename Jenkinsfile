pipeline  {
    agent any

    tools {
        jdk 'OpenJDK17'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }

    stages {
        stage('Clean Workspace') {
            steps {
                // Cleanup before starting the stage
                cleanWs()
            }
        }
        stage('Checkout') {
            steps {
                // Checkout the repository
                checkout scm                                                    
            }
        }
        stage('Unit & Integration Tests') {
            steps {
                script {
                    try {
                        sh './gradlew clean testOSGi --info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2 --no-daemon' 
                    } finally {
                        junit testResults: '**/generated/test-reports/testOSGi/TEST-*.xml', skipPublishingChecks: true //make the junit test results available in any case (success & failure)
                    }
                }
            }
        }
        stage('Main branch release') {
            when { 
                branch 'main' 
            }
            steps {
                echo "I am building on ${env.BRANCH_NAME}"
                sh "./gradlew release -x testOSGi -Drelease.dir=$JENKINS_HOME/repo.gecko/release/org.gecko.codec --info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2"
            }
        }
        stage('Snapshot branch release') {
            when { 
                branch 'develop'
            }
            steps  {
                echo "I am building on ${env.JOB_NAME}"
                sh "./gradlew release -x testOSGi -info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2"
                sh "mkdir -p $JENKINS_HOME/repo.gecko/snapshot/org.gecko.codec"
                sh "rm -rf $JENKINS_HOME/repo.gecko/snapshot/org.gecko.codec/*"
                sh "cp -r cnf/release/* $JENKINS_HOME/repo.gecko/snapshot/org.gecko.codec"
            }
        }
        stage('Other branch') {
            when {
                allOf {
                    not {
                        branch 'develop'
                    }
                    not {
                        branch 'main'
                    }
                }
            }
            steps  {
                echo "I am building on ${env.JOB_NAME}"
                sh "./gradlew build -x testOSGi --info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2"
            }
        }
    }
}

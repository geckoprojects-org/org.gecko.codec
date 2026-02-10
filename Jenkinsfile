pipeline  {
    agent any

    tools {
        jdk 'OpenJDK21'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }

    stages {
        stage('Main branch release') {
            when { 
                branch 'main' 
            }
            steps {
                script {
                    echo "I am building on ${env.BRANCH_NAME}"
                    try {
                        sh "./gradlew clean build release -Drelease.dir=$JENKINS_HOME/repo.gecko/release/org.gecko.mac --info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2"
                    } finally {
                        junit testResults: '**/generated/test-reports/**/TEST-*.xml', allowEmptyResults: true, skipPublishingChecks: true
                    }
                }
            }
        }
        stage('Snapshot branch release') {
            when { 
                branch 'snapshot'
            }
            steps  {
                script {
                    echo "I am building on ${env.JOB_NAME}"
                    try {
                        sh "./gradlew clean release --info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2"
                        sh "mkdir -p $JENKINS_HOME/repo.gecko/snapshot/org.gecko.mac"
                        sh "rm -rf $JENKINS_HOME/repo.gecko/snapshot/org.gecko.mac/*"
                        sh "cp -r cnf/release/* $JENKINS_HOME/repo.gecko/snapshot/org.gecko.mac"
                    } finally {
                        junit testResults: '**/generated/test-reports/**/TEST-*.xml', allowEmptyResults: true, skipPublishingChecks: true
                    }
                }
            }
        }
    }

}

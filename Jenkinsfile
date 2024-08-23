pipeline {
    agent any

    environment {
        PATH = "/usr/bin:$PATH"
    }

    stages {
        stage('Build Frontend') {
            steps {
                sh "echo Building frontend"
                sh "cd frontend && npm install && npm run build"
            }
        }
        stage('Deploy Frontend') {
            steps {
                script {
                    try {
                        withAWS(region: 'us-east-1', credentials: 'AWS_CREDENTIALS') {
                            sh "aws s3 sync frontend/build s3://inventoryman"
                        }
                    } catch (Exception e) {
                        echo "${e}"
                        throw e
                    }
                }
            }
        }
        stage('Build Backend') {
            steps {
                sh "cd backend && mvn clean install && ls target/"
            }
        }
        stage('Test Backend') {
            steps {
                sh "cd backend && mvn test"
            }
        }
        stage('Deploy Backend') {
            steps {
                script {
                    withAWS(region: 'us-east-1', credentials: 'AWS_CREDENTIALS') {
                        sh "aws s3 cp backend/target/inventoryman-0.0.1-SNAPSHOT.jar s3://inventoryman-backend"
                        sh "aws elasticbeanstalk create-application-version --application-name InventoryMan --version-label 0.0.1 --source-bundle S3Bucket=\"inventoryman-backend\",S3Key=\"inventoryman-0.0.1-SNAPSHOT.jar\""
                        sh "aws elasticbeanstalk update-environment --environment-name InventoryManEnv --version-label 0.0.1"
                    }
                }
            }
        }
    }
}

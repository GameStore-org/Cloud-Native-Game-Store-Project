pipeline {
    agent any

    stages {

        stage('build') {
            steps {
              bat '''
                 cd ./level-up-service
                 ./mvnw -DskipTests clean compile
              '''
            }
        }

        stage('test') {
            steps {
              bat '''
                 cd level-up-service
                     ./mvnw test
              '''
            }
        }

        stage('deliver') {
            steps {
              bat '''
                 cd level-up-service
                     ./mvnw -DskipTests install
              '''
            }
        }

    }
}

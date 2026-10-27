pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        COMPOSE_PROJECT_NAME = 'disponibiliteetablissementservice'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build des images') {
            steps {
                withCredentials([file(credentialsId: 'dispo-env', variable: 'ENV_FILE')]) {
                    sh 'docker compose --env-file "$ENV_FILE" build --pull'
                }
            }
        }

        stage('Déploiement') {
            steps {
                withCredentials([file(credentialsId: 'dispo-env', variable: 'ENV_FILE')]) {
                    sh 'docker compose --env-file "$ENV_FILE" up -d'
                }
            }
        }
    }

    post {
        success { echo 'Déploiement terminé : http://localhost:3000' }
        failure { echo 'Échec du pipeline, voir les logs ci-dessus.' }
    }
}
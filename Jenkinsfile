pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
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

        // Tests Maven dans un conteneur JDK 25 (Jenkins tourne en JDK 21).
        // --volumes-from partage le workspace de Jenkins avec ce conteneur.
        stage('Tests backend') {
            steps {
                sh '''
                    docker run --rm \
                      --volumes-from jenkins \
                      -v maven-cache:/root/.m2 \
                      -w "$WORKSPACE/backend" \
                      eclipse-temurin:25-jdk-alpine \
                      sh -c "chmod +x mvnw && ./mvnw -B test"
                '''
            }
        }

        // Lint + tests Vitest dans un conteneur Node
        stage('Tests frontend') {
            steps {
                sh '''
                    docker run --rm \
                      --volumes-from jenkins \
                      -w "$WORKSPACE/frontend" \
                      node:24-alpine \
                      sh -c "npm ci && npm run lint && npm test"
                '''
            }
        }

        stage('Build des images') {
            steps {
                withCredentials([file(credentialsId: 'dispo-env', variable: 'ENV_FILE')]) {
                    sh 'docker compose --env-file "$ENV_FILE" build --pull'
                }
            }
        }

        // ---- Déploiement : désactivé pour l'instant ----
        // À réactiver plus tard avec un déploiement sur le VPS (OVH / Hostinger).
        //
        // stage('Déploiement') {
        //     steps {
        //         withCredentials([file(credentialsId: 'dispo-env', variable: 'ENV_FILE')]) {
        //             sh 'docker compose --env-file "$ENV_FILE" up -d'
        //         }
        //     }
        // }
    }

    post {
        success { echo 'Build et tests OK.' }
        failure { echo 'Échec du pipeline, voir les logs ci-dessus.' }
    }
}
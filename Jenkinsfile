pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo "Étape Build"
            }
        }
        stage('Test') {
            steps {
                echo "Étape Test"
            }
        }
        stage('Deploy') {
            steps {
                echo "Étape Deploy"
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline terminé avec succès !'
        }
        failure {
            echo '❌ Pipeline échoué.'
        }
        always {
            echo 'Fin du pipeline, quoiqu’il arrive.'
        }
    }
}

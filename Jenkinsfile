pipeline {
    agent any

    environment {
        API_TEST_REPORT_DIR = 'target/api-tests-reports'
        UI_TEST_REPORT_DIR = 'target/ui-tests-reports'
    }

    triggers {
        cron('H H * * *') // Запуск один раз на день
    }

    stages {
        stage('Checkout') {
            steps {
                // Клонуємо репозиторій з GitHub
                git 'https://github.com/your-repo/project-name.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Налаштування проекту
                    sh './mvnw clean install'
                }
            }
        }

        stage('Run API Tests') {
            steps {
                script {
                    // Запускаємо API тести
                    sh './mvnw test -Dtest=api.tests.*'
                }
            }
            post {
                always {
                    // Архівуємо результати API тестів
                    archiveArtifacts artifacts: "${API_TEST_REPORT_DIR}/**", allowEmptyArchive: true
                    // Публікація звітів API тестів в Allure
                    allure includeProperties: false, reportBuildPolicy: 'ALWAYS', results: [[path: "${API_TEST_REPORT_DIR}"]]
                }
            }
        }

        stage('Run UI Tests') {
            steps {
                script {
                    // Запускаємо UI тести
                    sh './mvnw test -Dtest=ui.tests.*'
                }
            }
            post {
                always {
                    // Архівуємо результати UI тестів
                    archiveArtifacts artifacts: "${UI_TEST_REPORT_DIR}/**", allowEmptyArchive: true
                    // Публікація звітів UI тестів в Allure
                    allure includeProperties: false, reportBuildPolicy: 'ALWAYS', results: [[path: "${UI_TEST_REPORT_DIR}"]]
                }
            }
        }
    }

    post {
        always {
            // Надсилаємо звіт поштою або повідомленням
            mail to: 'helldegh@gmail.com',
                 subject: "Daily Build: ${currentBuild.fullDisplayName}",
                 body: "Build finished. Check results at ${env.BUILD_URL}"
        }
    }
}


def tagName;
def dockerTagName;
def dockerRepoUrl = "harbor.norsys-afrique.ma"
def dockerImageName = "mediatheque-api"
def dockerImageTag = "${dockerRepoUrl}/agrisud/${dockerImageName}:${dockerTagName}"

pipeline {
   agent any
   stages {
      stage('init'){
        tagName = sh(script: 'git describe --tags --abbrev=0', returnStdout: true).trim()
        sh("git checkout ${tagName}")
      }
      stage('Tests') {
         steps {
            sh("mvn clean test")
         }
      }
      stage('build') {
         steps {
            sh("mvn clean install -DskipTests")
         }
      }
      stage('Docker Build and Tag') {
         steps {
            sh("docker build -f docker/Dockerfile -t ${dockerImageName} .")
         }
      }
      stage('Push') {
         environment {
            DOCKER = credentials("naf-docker-registry")
         }
         steps {
            dockerImageName = tagName.replace("MEDIATHEQUE-API-", "");
            sh("docker tag ${dockerImageName} ${dockerImageTag}")
            sh("docker login -u $DOCKER_USR -p $DOCKER_PSW ${dockerRepoUrl}")

            sh("docker push ${dockerImageTag}")
         }
      }
   }
}


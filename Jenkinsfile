def tagName;
def dockerTagName;
def dockerRepoUrl = "harbor.norsys-afrique.ma"
def dockerImageName = "mediatheque-api"
def dockerImageTag;

def keycloakURL = "https://login-agrisud.int.norsys-afrique.ma/auth"
def keycloakRealm = "agrisud"
def keycloakClientId = "agrisud-mediatheque-api"
def apiUrl = "https://mediathequeapi-agrisud.int.norsys-afrique.ma"

def rancherHost = "192.168.1.235"

pipeline {
   agent any
   stages {
      stage('init'){
        steps {
            script {
                tagName= sh(returnStdout: true, script: 'git describe --tags --abbrev=0').trim()
            }
        }
      }
      stage('Tests') {
         steps {
            sh("git checkout ${tagName}")
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
             script {
                 dockerTagName = tagName.replace("MEDIATHEQUE-API-", "")
                 dockerImageTag = "${dockerRepoUrl}/agrisud/${dockerImageName}:${dockerTagName}"
             }
            sh("docker tag ${dockerImageName} ${dockerImageTag}")
            sh("docker login -u $DOCKER_USR -p $DOCKER_PSW ${dockerRepoUrl}")

            sh("docker push ${dockerImageTag}")
         }
      }
      stage('Deploy') {
         environment {
            OPS = credentials("naf-ops-deploy")
         }
         steps {
            script {
               def remote = [: ]
               remote.name = 'ops'
               remote.host = "$rancherHost"
               remote.user = "$OPS_USR"
               remote.password = "$OPS_PSW"
               remote.allowAnyHosts = true
               sshCommand remote: remote, command: 'export PATH=$PATH:/var/lib/rancher/rke2/bin KUBECONFIG=int-config && kubectl patch deployment agrisud-mediatheque-api -n agrisud-integration -p "{\\"spec\\": {\\"template\\": {\\"metadata\\": { \\"labels\\": {  \\"redeploy\\": \\"$(date +%s)\\"}}}}}"'
            }
         }
      }

   }

}

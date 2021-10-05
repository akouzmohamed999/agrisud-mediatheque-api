def dockerRepoUrl = "harbor.norsys-afrique.ma"
def dockerImageName = "mediatheque-api"
def dockerImageTag = "${dockerRepoUrl}/agrisud/${dockerImageName}:latest"

pipeline {
   agent any
   stages {    
      stage('Deploy') {
         environment {
            OPS = credentials("naf-ops-deploy")
         }
         steps {
            script {
               def remote = [:]
               remote.name = 'ops'
               remote.host = '192.168.1.235'
               remote.user = "$OPS_USR"
               remote.password = "$OPS_PSW"
               remote.allowAnyHosts = true
               sshCommand remote: remote, command: "export PATH=$PATH:/var/lib/rancher/rke2/bin"
               sshCommand remote: remote, command: "export KUBECONFIG=/home/mohamed/int-config"
               sshCommand remote: remote, command: 'kubectl patch deployment agrisud-mediatheque-api -n agrisud-integration -p "{\"spec\": {\"template\": {\"metadata\": { \"labels\": {  \"redeploy\": \"$(date +%s)\"}}}}}"'
            }
         }
      }
   }
}


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
            def remote = [:]
            remote.name = 'ops'
            remote.host = '192.168.1.235'
            remote.user = "$OPS_USR"
            remote.password = "$OPS_PSW"
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "ls -l"
         }
      }
   }
}


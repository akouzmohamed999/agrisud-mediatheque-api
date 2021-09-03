def dockerRepoUrl = "harbor.norsys-afrique.ma"
def dockerImageName = "mediatheque-api"
def dockerImageTag = "${dockerRepoUrl}/agrisud/${dockerImageName}:latest"

pipeline {
	  agent any
		 stages {
             
              stage('Build') {
				   steps {
					sh("java -version")
				    sh("mvn clean install")
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
				    sh("docker tag ${dockerImageName} ${dockerImageTag}")
				    sh("docker login -u $DOCKER_USR -p $DOCKER_PSW ${dockerRepoUrl}")

				    sh("docker push ${dockerImageTag}")
				   }
			  }
			  
	 }

}


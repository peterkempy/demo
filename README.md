# Getting Started

### Pre-requisites
Install Docker Desktop, and in Kubernetes Settings enable Kubernetes

### Commands

| Command                                                                  | Purpose                                                                                                                       | 
|--------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| gradlew clean build                                                      | Build & package fat jar                                                                                                       |
| docker build -t pk/demo .                                                | Build image, tagged as pk/demo                                                                                                |
| kubectl apply -f pk-demo-configmap.yaml                                  | Create configmap - used to simulate Vault integration                                                                         |
| kubectl apply -f pk-demo-deployment.yaml                                 | Create deployment                                                                                                             |
| kubectl expose deployment pk-demo-app --type=NodePort --name=pk-demo-svc | Expose application as a service                                                                                               |
| kubectl get svc pk-demo-svc                                              | Get service details - including NodePort port (will be > 30000) - e.g. if the output is 8080:32395/TCP, the NodePort is 32395 |

Now you should be able to hit the service on [http://localhost:\<node port\>/info](http://localhost:32395/info)
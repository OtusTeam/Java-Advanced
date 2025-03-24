call mvn clean package

call docker build --tag=iamsiberian/helm-kubernetes-backend:latest --rm=true .\backend
call docker push iamsiberian/helm-kubernetes-backend:latest

call docker build --tag=iamsiberian/helm-kubernetes-frontend:latest --rm=true .\frontend
call docker push iamsiberian/helm-kubernetes-frontend:latest

call mvn clean package

call docker build --tag=helm-kunernetes-backend --rm=true .\backend
call docker tag helm-kunernetes-backend iamsiberian/helm-kunernetes-backend:latest
call docker push iamsiberian/helm-kunernetes-backend:latest

call docker build --tag=helm-kunernetes-frontend --rm=true .\frontend
call docker tag helm-kunernetes-frontend iamsiberian/helm-kunernetes-frontend:latest
call docker push iamsiberian/helm-kunernetes-frontend:latest

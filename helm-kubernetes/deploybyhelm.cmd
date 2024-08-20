call helm upgrade --install backend-deployment --values .\backend\values.yaml .\deployment\helm
call helm upgrade --install frontend-deployment --values .\frontend\values.yaml .\deployment\helm
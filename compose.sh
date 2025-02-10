clear
docker-compose down
docker rm -f order-management-app-container delivery-logistic-app-container
docker rmi order-management-app-image delivery-logistic-app-image
#docker-compose up --build
clear
#docker-compose up
docker-compose down
docker rm -f order-management-app-container delivery-logistic-app-container product-catalog-app-container client-app-container
docker rmi order-management-app-image delivery-logistic-app-image product-catalog-app-image client-app-image
docker-compose up --build
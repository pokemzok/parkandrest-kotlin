docker exec docker_rabbit_1 rabbitmqctl add_user admin admin
docker exec docker_rabbit_1 rabbitmqctl set_user_tags admin administrator
docker exec docker_rabbit_1 rabbitmqctl set_permissions -p / admin .* .* .*
docker exec docker_rabbit_1 rabbitmq-plugins enable rabbitmq_management
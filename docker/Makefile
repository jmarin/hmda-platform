docker:
	./build.sh

run:
	COMPOSE_HTTP_TIMEOUT=300 docker-compose up

stop:
	docker-compose stop

clean:
	docker-compose rm --force


.PHONY: run stop clean docker
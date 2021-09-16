Для проверки сервиса необходимо вызвать команду curl

curl --location --request POST '127.0.0.1:8090/save' \
--header 'Content-Type: application/json' \
--data-raw '{
    "series":"1239",
    "number":"123456",
    "dateDoc":"2012-01-01T00:00:00.000Z",
    "dateEnd":"2021-09-01T00:00:00.000Z"
}'

для создания записи
для проверки работы базы данных вызвать команду 

curl --location --request GET '127.0.0.1:8090/find'
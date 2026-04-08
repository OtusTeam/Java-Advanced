curl -X POST http://localhost:6543/greet \
-H "Content-Type: application/json" \
-d '{"name": "World"}'

bash
curl -s -X POST http://localhost:6543/greet \
-H "Content-Type: application/json" \
-d '{"name": "World"}' | jq .
С трейсингом — прокинуть свой traceId вручную:

bash
curl -X POST http://localhost:6543/greet \
-H "Content-Type: application/json" \
-H "X-Trace-Id: my-custom-trace-123" \
-d '{"name": "Alice"}'

Несколько запросов для Loki/Tempo:

bash
for name in Alice Bob Charlie; do
curl -s -X POST http://localhost:6543/greet \
-H "Content-Type: application/json" \
-d "{\"name\": \"$name\"}"
echo
done
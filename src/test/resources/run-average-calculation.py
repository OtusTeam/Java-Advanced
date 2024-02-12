import requests
import time

METRICS_URI = "http://localhost:8080/actuator/metrics/{}"
METRIC = "hash.time"

if __name__ == '__main__':
    while True:
        response = requests.get(METRICS_URI.format(METRIC))
        json = response.json()
        # print(f'Response json={json}')
        count = json["measurements"][0]["value"]  # 0 -> COUNT
        total = json["measurements"][1]["value"]  # 1 -> TOTAL_TIME

        print(f'{METRIC} avg (ms): {1000.0 * total / count}         = {total} / {count} = TOTAL_TIME / COUNT ')
        time.sleep(1)

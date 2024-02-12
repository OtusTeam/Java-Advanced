import requests
import time

METRICS_URI = "http://localhost:8080/actuator/metrics/{}"

if __name__ == '__main__':
    while True:
        response = requests.get(METRICS_URI.format("hash.time"))
        json = response.json()
        # print(f'Response json={json}')
        count = json["measurements"][0]["value"]  # 0 -> COUNT
        total = json["measurements"][1]["value"]  # 1 -> TOTAL_TIME

        print(f'avg: {total / count}         = {total} / {count} = TOTAL_TIME / COUNT ')  # format(total, '.12g')
        time.sleep(1)

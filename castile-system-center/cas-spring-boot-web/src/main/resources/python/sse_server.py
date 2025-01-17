import json

import requests
from flask import Flask, Response, stream_with_context, request
import time

from flask_cors import CORS
from gevent.pywsgi import WSGIServer

app = Flask(__name__)

CORS(app, resources={r"/*": {"origins": "*"}})  # 允许所有源访问，但请注意生产环境的安全性


@app.route('/ret_json_data', methods=['GET'])
def ret_json_data():
    print('start request!')
    return {'hello': 'hello flask'}


@app.route('/stream_json_data',  methods=["GET", "POST"])
def stream_json_data():
    # json_data = {
    #     'state': 0,
    #     'data': [],
    #     'text': '',
    #     'message': ''
    # }
    ret_str = "大模型通常指的是那些规模庞大、参数数量极多的人工智能模型，它们在训练时使用了海量的数据，能够处理复杂的任务并展现出强大的语言理解和生成能力。"

    def generate():
        for i in range(len(ret_str)):
            # json_data["text"] = ret_str[i]

            time.sleep(0.1)  # 暂停一秒，模拟数据产生的时间间隔
            # print(f"Data: {ret_str[i]}\n")
            # yield json.dumps(json_data, ensure_ascii=False)
            yield "data: "+ ret_str[i]+"\r\n"
            # yield ret_str[i]

    print('stream-data')
    return Response(stream_with_context(generate()), mimetype='text/event-stream; charset=utf-8')
    # return stream_data(generate())


@app.route('/stream_data_to_frontend')
def stream_data_to_frontend():
    # 假设你有一个外部 URL，该 URL 提供流式数据
    external_url = 'http://127.0.0.1:7000/stream_json_data'

    # 使用 requests 库的流式响应来接收数据
    headers = {"Content-Type": "application/json"}
    response = requests.get(external_url, stream=True, headers=headers)
    # with requests.get(external_url, stream=True, headers=headers) as r:
    #     # print('r========', r.json())
    #     # 检查外部请求是否成功
    if response.status_code == 200:
        # 创建一个生成器来流式传输响应内容
        def generate():
            for chunk in response.iter_content(chunk_size=1024):  # 你可以设置合适的 chunk_size
                if chunk:
                    print('chunk: ', chunk)
                    # yield chunk
                    # yield chunk.decode("utf-8", "ignore")
                    chunk = json.loads(chunk)
                    chunk['tt'] = 'ttttt'
                    yield json.dumps(chunk, ensure_ascii=False)

                    # 创建一个 Flask 响应对象，并使用 stream_with_context 来发送流式响应

        return Response(stream_with_context(generate()), content_type='application/json; charset=utf-8')
    else:
        # 如果外部请求失败，返回错误信息
        return 'Failed to retrieve data from external source', 500


@app.route('/stream_data_to_frontend_test')
def stream_data_to_frontend_test():
    url = "http://ip:port/llm"
    params = {
        "messages": [
            {
                "role": "user",
                "content": "储能公司在该项目攻关中取得了哪些关键性突破？"
            }
        ]
    }
    headers = {"Content-Type": "application/json"}
    response = requests.post(url=url, json=params, stream=True, headers=headers)

    def generate():
        for chunk in response.iter_content(1024):
            print(chunk.decode("utf-8", "ignore"))
            if chunk:
                yield chunk.decode("utf-8", "ignore")

    return Response(stream_with_context(generate()), content_type='application/json; charset=utf-8')


@app.route('/stream_faq_data_to_frontend_test')
def stream_faq_data_to_frontend_test():
    url = 'http://ip:port/url'
    # 请求的数据
    data = {
        "query": "测试"
    }
    headers = {"Content-Type": "application/json"}
    response = requests.post(url=url, json=data, stream=True, headers=headers)

    def generate():
        for chunk in response.iter_content(1024):
            print('chunk', chunk.decode("utf-8", "ignore"))
            if chunk:
                # chunk = json.loads(chunk)
                # chunk = json.dumps(chunk)
                yield chunk
                # yield chunk.decode("utf-8", "ignore")

    return stream_data(generate())


def stream_data(generate):
    """
    :param generate: generator object
    :return:
    """
    return Response(stream_with_context(generate), content_type='application/json; charset=utf-8')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=7009)
    # server = WSGIServer(('0.0.0.0', 7000), app)
    # server.serve_forever()


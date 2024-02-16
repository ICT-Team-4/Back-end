from openai import OpenAI
import os,json,requests
import urllib.request
from flask_restful import Resource,reqparse
from flask import jsonify, request
from flask import make_response


client = OpenAI(
    api_key=os.environ.get("OPENAI_API_KEY"),
)

class chatImage(Resource):
    def post(self):
        prompt = request.json['message'] #앞에서 넘긴 메시지 받기
        print('받은 메세지:',prompt)
        image_path, image_name = generate_image(prompt,client)
        image_url = '/' + image_path.replace('\\','/')
        print('보내는 메세지(url):', image_url)
        print('보내는 메세지(name):', image_name)
        return jsonify({"image_url":image_url, "image_name":image_name})

# 폴더 생성 함수(폴더 없으면 생성)
def create_folder(folder_path):
    if not os.path.exists(folder_path):
        os.makedirs(folder_path)

# 이미지 생성 함수
def create_image(client, model, messages):
    response = client.chat.completions.create(model=model, messages=messages)
    return response

def generate_image(prompt, client):
    model = 'gpt-3.5-turbo'

    print('받은 메세지2:',prompt)
    messages = [
        {"role": "system", "content": "you are a translation expert who translates korean into english"},
        # 시스템 응답에 난이도?설정
        {"role": "user", "content": prompt}
    ]
    print('반환 메세지',messages)

    response = create_image(client, model, messages)
    prompt = response.choices[0].message.content
    print('번역:', prompt)
    folder_path = '../model/chatImage_upload' # 이미지 저장
    create_folder(folder_path)

    image_name = 'id.jpg'
    image_path = os.path.join(folder_path,image_name)

    if os.path.exists(image_path): #id중복시 숫자 추가(중복 제거)
        index = 1
        while os.path.exists(image_path):
            image_name = f'id_{index}.jpg'
            image_path = os.path.join(folder_path, image_name)
            index += 1
    response = client.images.generate(
        model="dall-e-3",
        prompt=prompt,
        size="1024x1024",
        response_format='url',  # 기본값
        n=1,
    )

    image_url = response.data[0].url  # 1시간만 유효
    print('image_url:')
    urllib.request.urlretrieve(image_url, 'maltese.png')

    return image_path, image_name
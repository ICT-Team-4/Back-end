import requests
from openai import OpenAI
import os,json
import urllib.request
import openai
from flask_restful import Resource,reqparse
from flask import jsonify, request
from flask import make_response

model='gpt-3.5-turbo'
client = OpenAI(
    api_key=os.environ.get("OPENAI_API_KEY"),
)

class chatImage(Resource):

    def chatComplete(self,model,messages):
        response = client.chat.completions.create(model=model, messages=messages)
        return response

    def chatImageCreate(self,chatComplete):
        prompt = input('생성할 이미지를 묘사해 주세요?')
        messages = [
            {"role": "system", "content": "you are a translation expert who translates korean into english"},
            # 시스템 응답에 난이도?설정
            {"role": "user", "content": prompt}]
        response = chatComplete(client, model, messages)
        english = response.choices[0].message.content
        print('번역:', english)
        # 2.번역된 영어로 이미지 생성(한글 prompt은 원하는 이미지가 생성이 안된다)
        response = client.images.generate(
            model="dall-e-3",
            prompt=prompt,
            size="1024x1024",
            response_format='url',  # 기본값
            n=1,
        )
        image_url = response.data[0].url  # 1시간만 유효
        urllib.request.urlretrieve(image_url, 'maltese.png')


        return jsonify(dict())
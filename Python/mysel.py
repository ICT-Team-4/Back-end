from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os

def instar(id_user,pwd_user):
    try:

        # 1.WebDriver객체 생성
        driver_path = f'{os.path.join(os.path.dirname(__file__), "chromedriver.exe")}'
        service = Service(executable_path=driver_path)
        options = webdriver.ChromeOptions()
        # 자동종료 막기
        options.add_experimental_option("detach", True)  # 드라이버랑 detach하자
        options.add_argument('headless') #화면 숨기기 완성시 주석 해제
        options.add_argument('window-size=1920x1080') #모두 똑같은 크기 고정

        driver = webdriver.Chrome(service=service, options=options)

        #주소
        driver.get('https://www.instagram.com/')
        
        #로그인 이동
        id = WebDriverWait(driver, 5).until(
            EC.presence_of_element_located((By.XPATH, '//*[@id="loginForm"]/div/div[1]/div/label/input')))
        id.send_keys(id_user)
        pwd = WebDriverWait(driver, 5).until(
            EC.presence_of_element_located((By.XPATH, '//*[@id="loginForm"]/div/div[2]/div/label/input')))
        pwd.send_keys(pwd_user)
        pwd.send_keys(Keys.ENTER)
        #로그인 성공시...---
        #계정 확인용 아무 버튼 체크 의미는 없다
        WebDriverWait(driver, 3).until(EC.presence_of_element_located((By.CSS_SELECTOR,"div > div > div > div > div > div > div > div > div > div > div > div > div > div:nth-child(8) > div > span > div > a")))
        print("계정 접속 성공")

        return id_user

    except TimeoutException as e:
        print('계정 접속 실패')
        return "false"
    finally:
        driver.quit()


if __name__=="__main__":
    print(instar("minjong020214@gmail.com","teamteam123"))
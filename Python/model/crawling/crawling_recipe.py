from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import  By
import os,time#표준 라이브러리

def recipe(title):
    try:
        # 1.WebDriver객체 생성
        print('title',title)
        driver_path=f'{os.path.join(os.path.dirname(__file__),"chromedriver.exe")}'
        service = Service(executable_path=driver_path)
        options = webdriver.ChromeOptions()

        # 자동종료 막기
        options.add_experimental_option("detach", True)  # 드라이버랑 detach하자
        # Headless Browser를 위한 옵션 설정
        options.add_argument('headless')
        options.add_argument('--disable-gpu')
        # 크기에 따른 요소 hidden방지용
        options.add_argument('window-size=1920x1080')

        options.add_argument('User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36')
        driver = webdriver.Chrome(service=service, options=options)

        driver.get(f'https://www.10000recipe.com/recipe/list.html?q={title}')
        local = driver.find_elements(By.XPATH,'//*[@id="contents_area_full"]/ul/ul/li')
                                                    ##contents_area_full > ul > ul > li:nth-child(1) > div.common_sp_caption > div.common_sp_caption_tit.line2
        # print('local',local.text) #크롤링된 리스트 확인
        foodArr = []
        i = 0
        for txt in local:
            title = txt.find_element(By.CSS_SELECTOR,"div.common_sp_caption > div.common_sp_caption_tit.line2")
            link =txt.find_element(By.CSS_SELECTOR,'div.common_sp_thumb > a') # #contents_area_full > ul > ul > li:nth-child(1) > div.common_sp_thumb > a
            name = txt.find_element(By.CSS_SELECTOR,'div.common_sp_caption > div.common_sp_caption_rv_name') #contents_area_full > ul > ul > li:nth-child(3) > div.common_sp_caption > div.common_sp_caption_rv_name
            img = txt.find_element(By.CSS_SELECTOR,'div.common_sp_thumb > a > img') #contents_area_full > ul > ul > li:nth-child(2) > div.common_sp_thumb > a > img
            # driver.get(link.get_attribute('href'))
            # sub = driver.find_element(By.XPATH,'//*[@id="recipeIntro"]') ##recipeIntro
            print('title:',title.text)
            print('link',link.get_attribute('href'))
            print('name',name.text)
            print('img',img.get_attribute('src'))
            foodArr.append({'title':title.text,'link':link.get_attribute('href'),'name':name.text,'img':img.get_attribute('src')})
            # print('sub',sub)
            i+= 1
            if i == 5: #횟수제한 주기
                return foodArr
                # break



    except Exception as e:
        print('error:',e)

if __name__ == '__main__':
    print(crawling('중식'))
    # print(google_crawling('벤치프레스방법'))

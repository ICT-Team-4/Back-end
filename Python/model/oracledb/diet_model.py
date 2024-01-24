from configparser import ConfigParser
from cx_Oracle import connect
import os


def diet_connectDatabase():  # 데이타베이스 연결
    config = ConfigParser()
    # print(os.path.abspath('.'))
    # 데이터 절대경로 찾아주기
    path = os.path.dirname(os.path.abspath(__file__))
    # print(path)
    config.read(path + '/oracle.ini', encoding='utf8')
    # 데이타베이스 연결
    return connect(user=config['ORACLE']['user'],
                   password=config['ORACLE']['password'],
                   dsn=config['ORACLE']['URL'], encoding="UTF-8")


def diet_close(conn):  # 커넥션객체 닫기
    if conn:
        conn.close()


# 식단 추가
def diet_insert(conn, user_id, list_):
    print(user_id, ":", list_)
    test = []  # 캘린더 테이블
    test.append(user_id)
    test.append(list_['DESCRIPTION']) #제목
    test.append(list_['MEMO']) #메모,내용
    test.append(list_['DIET_IMAGE']) #음식 사진
    test.append(list_['FOOD']) #음식 이름
    test.append(list_['FOOD_WEIGHT']) #음식 용량

    with conn.cursor() as cursor:
        try:
            cursor.execute('INSERT ALL INTO calendar VALUES(SEQ_CALENDAR_CALENDAR_NO.nextval, :1, :2, :3,DEFAULT,default) INTO diet VALUES((SEQ_CALENDAR_CALENDAR_NO.nextval), :4, :5, :6) SELECT * FROM DUAL', test)
            conn.commit()
            return cursor.rowcount
            '''
                CALENDAR_NO    NOT NULL NUMBER  필요없고       
                ACCOUNT_NO     NOT NULL NUMBER  넌 뭔데 no로 왔냐? user_id를 ACCOUNT_NO로 받아오자  
                DESCRIPTION    NOT NULL NVARCHAR2(100) 제목
                MEMO           NOT NULL NVARCHAR2(100) 내용
                START_POSTDATE          DATE           일정 시작일 (짜피 먹는건데 그냥 디폴트 하루임)
                END_POSTDATE            DATE            일정 끝나는날(날짜가 하루면 디폴트 넣어주셈)
                ----------- -------- -------------- 
                CALENDAR_NO NOT NULL NUMBER             SELECT max(calendar_no) FROM calendar; 로 받아오자
                DIET_IMAGE           NVARCHAR2(50)      사진
                FOOD        NOT NULL NVARCHAR2(100)     음식 예시:닭갈비
                FOOD_WEIGHT NOT NULL NUMBER             음식 용량 예시: 1001

                INSERT ALL
                    INTO calendar VALUES(
                        SEQ_CALENDAR_CALENDAR_NO.nextval,
                        3,
                        '다중 insert2',
                        '볶음밥',
                        DEFAULT,
                        default  
                    )
                    INTO diet VALUES(
                        (SELECT max(calendar_no) FROM calendar),
                        null,
                        '김치볶음밥1',
                        100
                        )
                    SELECT * FROM DUAL;
            '''

        except Exception as e:
            print("error:", e)
            return 0

#켈린더 일정(ex:2024.02.01) 하나 읽기
def diet_selectOne(conn, date):
    with conn.cursor() as cursor:
        try:
            date_ = []
            date_.append(date['START_POSTDATE'])
            date_.append(date['ACCOUNT_NO'])
            cursor.execute(f'SELECT c.calendar_no,description,memo,food,to_char(start_postdate,"YYYY-MM-DD HH24:MI:SS") time,diet_image,food_weight,account_no FROM calendar c JOIN diet d ON c.calendar_no = d.calendar_no WHERE TRUNC(start_postdate) = :1 AND account_no = :2 ORDER by time',
                date_)
            return cursor.fetchone()
        except Exception as e:
            print('레코드 하나 조회시 오류:', e)
            return None

#켈린더 일정(ex:2024.02.01~2024.02.10) 전체 읽기
def diet_selectAll(conn, date):
    with conn.cursor() as cursor:
        try:
            date_ = []
            date_.append(date['START_POSTDATE'])
            date_.append(date['END_POSTDATE'])
            date_.append(date['ACCOUNT_NO'])
            cursor.execute(f'SELECT c.calendar_no,description,memo,food,to_char(start_postdate,"YYYY-MM-DD HH24:MI:SS") s_time, to_char(end_postdate,"YYYY-MM-DD HH24:MI:SS") e_time,diet_image,food_weight,account_no FROM calendar c JOIN diet d ON c.calendar_no = d.calendar_no WHERE TRUNC(start_postdate) = :1 AND TRUNC(end_postdate) = :2 AND account_no = :3 ORDER by s_time',
                date_)
            return cursor.fetchall()
        except Exception as e:
            print('모든 데이터 조회시 오류:', e)
            return None

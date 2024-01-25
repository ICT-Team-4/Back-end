from configparser import ConfigParser
from cx_Oracle import connect
import os

def connectDatabase():  # 데이타베이스 연결
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


def close(conn):  # 커넥션객체 닫기
    if conn:
        conn.close()

def selectOne(conn, data):
    with conn.cursor() as cursor:
        try:
            cursor.execute(f"SELECT a.account_no,name,gender,age,height,weight,image FROM account a JOIN inbody i ON a.account_no = i.account_no WHERE a.account_no = :1 AND to_char(post_date,'YYYY-MM-DD HH24:MI:SS') = (SELECT max(to_char(post_date,'YYYY-MM-DD HH24:MI:SS')) FROM inbody WHERE account_no =:1)",data)
            return cursor.fetchone()
        except Exception as e:
            print('레코드 하나 조회시 오류:', e)
            return None
def diet_calendar(conn, user_id):
    with conn.cursor() as cursor:
        try:
            cursor.execute(
                f"SELECT DISTINCT to_char(TRUNC(start_postdate),'YYYY-MM-DD') time FROM calendar c JOIN diet d ON c.calendar_no = d.calendar_no WHERE account_no = :1",
                user_id)
            return cursor.fetchall()
        except Exception as e:
            print('모든 데이터 조회시 오류:', e)
            return None
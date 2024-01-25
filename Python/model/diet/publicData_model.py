import numpy as np
import pandas as pd

def line(name):
    # csv_data = np.loadtxt('amount_of_food.csv', delimiter=',')
    df = pd.read_csv("amount_of_food.csv")
    return  df.values[df.values[:,0]==name]

if __name__ == '__main__':
    print(line('순대'))
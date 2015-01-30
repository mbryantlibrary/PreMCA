import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

data = pd.read_csv(r'C:\Users\Miles\Source\Repos\Uni\PreMCA\logTest.csv')
#print(data)

for i in range(2,7):
    print(i)
    df = ((data['N' + str(i) + '_output']-data['N' + str(i+5) + '_output'])**2)
    
    df.plot()
    
#print(data.columns)
#print(data[[u'N6_output',u'N11_output']])
#print(df)
df.plot()
plt.show()

print(df.describe())
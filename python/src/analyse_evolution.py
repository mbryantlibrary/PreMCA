import util
import pandas as pd

datafilename = util.showFileDialog(r'N:\Source\Repos\Uni\PreMCA\user\experiments')

df = pd.DataFrame.from_csv(datafilename)

print(df)
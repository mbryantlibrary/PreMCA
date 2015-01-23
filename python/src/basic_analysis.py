import numpy
import matplotlib.pyplot as plt

data = numpy.genfromtxt(r"C:\Users\Miles\Source\Repos\Uni\PreMCA\user\experiments\23-01-2015.033944\0-stats.csv", delimiter=',', skip_header=1)


n = data[:,0]

maxFit = data[:,1]

aveFit = data[:,2]

varFit = data[:,3]

plt.plot(n,maxFit)
#plt.plot(n,aveFit)
#plt.plot(n,varFit)

#plt.legend(['maximum fit','average fit','variance'],loc='bottom')

plt.show()

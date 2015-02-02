import numpy
import matplotlib.pyplot as plt
import os

inputfolder = r"C:\Users\Miles\Source\Repos\Uni\PreMCA\user\experiments\27-01-2015.224651" + "\\"

stats = []
legend = []

foldercontents = os.listdir(inputfolder)
for f in foldercontents:
    if(os.path.isfile(os.path.join(inputfolder,f))):
        if(f[:5] == 'stats' and f[-3:]=="csv"):
            print(os.path.join(inputfolder,f))
            stats.append([]);
            fData = numpy.genfromtxt(os.path.join(inputfolder,f), delimiter =',', skip_header=1)
            #print(fData)
            stats[len(stats)-1].append(fData)
            #print(stats)
            
         
print(stats[1][0][:,0])
n = stats[1][0][:,0]

for run in stats:
    plt.plot(n,run[0][:,1])
    #plt.legend(legend, loc='bottom')
plt.xlabel('Generation')
plt.ylabel('Maximum fitness')
plt.show()

#data = numpy.genfromtxt(r"C:\Users\Miles\Source\Repos\Uni\PreMCA\user\experiments\23-01-2015.033944\0-stats.csv", delimiter=',', skip_header=1)
#n = data[:,0]

#maxFit = data[:,1]

#aveFit = data[:,2]

#varFit = data[:,3]

#plt.plot(n,maxFit)
#plt.plot(n,aveFit)
#plt.plot(n,varFit)

#plt.legend(['maximum fit','average fit','variance'],loc='bottom')

#plt.show()

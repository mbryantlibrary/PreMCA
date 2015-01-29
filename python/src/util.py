import Tkinter, tkFileDialog

def showFileDialog(initialDirectory=''):
    root = Tkinter.Tk()
    root.withdraw()
    
    if(initialDirectory==''):
        return tkFileDialog.askopenfilename()
    else:
        return tkFileDialog.askopenfilename(initialdir=initialDirectory)

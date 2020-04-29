from tkinter import *
from tkinter import messagebox
import tkinter as tk

ventana = tk.Tk()
ventana.title("Tomasulo step by step")#nombre ventana
ventana.geometry("850x600")#ancho por alto

##ventana.overrideredirect(True)
##ventana.overrideredirect(False)
ventana.attributes('-fullscreen',True)

ventana.configure(background = 'cadet blue')

etiqueta1 = tk.Label(ventana,text="TOMASULO ALGORITHM", bg = "BLUE", fg="white")
etiqueta1.pack()

instInit  = []
instFinish = []
instExe={}
pE = 0
pF = 0
tCB = 0
init = True
Cont = 0
cont = 0

ciclos = 0

def setValues(e1,e2,e3,ventana):
    global pE
    global pF
    global tCB
    try:
        pE= int(e1)
        pF= int(e2)
        tCB= int(e3)
        ventana.destroy()
        
    except:
        print("fill all the text fiends with the correct format, try again")


    

def write_slogan():
    print("Tkinter is easy to use!")

def deleteContent(pfile):
    pfile.seek(0)
    pfile.truncate()

def write_ventana():
    subVentana = tk.Tk()
    subVentana.title("Tomasulo parameters")#nombre ventana
    #subVentana.geometry("200x200")#ancho por alto
    subVentana.configure(background = 'dark sea green')
    frame = tk.Frame(subVentana)
    frame.grid(row=3,column=1)

    tk.Label(subVentana, text="Tiempo de la unidad entera", bg = "dark sea green").grid(row=0)
    tk.Label(subVentana, text="Tiempo de la unidad flotante", bg = "dark sea green").grid(row=1)
    tk.Label(subVentana, text="Tiempo del CB", bg = "dark sea green").grid(row=2)

    e1 = tk.Entry(subVentana)
    e2 = tk.Entry(subVentana)
    e3 = tk.Entry(subVentana)

    e1.grid(row=0, column=1)
    e2.grid(row=1, column=1)
    e3.grid(row=2, column=1)

    button = tk.Button(frame, 
                   text="OK", 
                   fg="GREEN",
                   command= lambda: setValues(e1.get(),e2.get(),e3.get(),subVentana))

    button.grid(row=2,column=1)
    
    subVentana.mainloop()

    
file = open("instructions.txt","a")
deleteContent(file)

regStatus={"F0":"0","F1":"0",
            "F2":"0","F3":"0",
            "F4":"0","F5":"0",
            "F6":"0","F7":"0","F8":"0","F9":"0","F10":"0",
            "R0":"0","R1":"0",
            "R2":"0","R3":"0",
            "R4":"0","R5":"0",
            "R6":"0","R7":"0",}
inStatus = {}

regs = {"F0":0,"F1":0,
       "F2":0,"F3":0,
       "F4":0,"F5":0,
       "F6":0,"F7":0,
       "R0":0,"R1":0,
       "R2":0,"R3":0,
       "R4":0,"R5":0,
       "R6":0,"R7":0,}

resStation = {}

def loadCode():
    global instInit
    global resStation
    global inStatus
    filepath = 'code.txt'  
    with open(filepath) as fp:  
       line = fp.readline()
       cnt = 1
       while line:
           i=str(line[:-1]).split()
           inStatus[cnt]=[i,[False,False,False]]
           instInit+=[cnt]
           resStation[cnt]=[False,"","","","","","","",0,0]
           instExe[cnt]=[i[0],cnt,None]
           line = fp.readline()
           cnt += 1
loadCode()

def getStrListP(l,n,i):
    p=""
    tmp=""
    c=0
    while(c!=i):
        p+=" "
        c+=1
    for x in l:
        if(str(x)==""):
            tmp="-"
        else:
            tmp=x
        p+=str(tmp)
        c = len(str(tmp))
        while(c!=n):
            p+=" "
            c+=1
    return p

def printInStatus():
    file = open("output.txt","a")
    o = list(inStatus.keys())
    o.sort()
    print("Instruction Status")
    file.write("INSTRUCTION STATUS\n") 
    print("[          INSTRUCTION          ]       [ISSUE     EXECUTE   W_RESU]")
    file.write("-----------INSTRUCTION-------------------ISSUE-----EXECUTE---W_RESU-\n")
    for x in o:
        inst=getStrListP(inStatus[x][0],10,0)
        status=getStrListP(inStatus[x][1],10,0)
        print(inst,status)
        file.write(inst)
        file.write(" ")
        file.write(status)
        file.write("\n")
    print("---------------------------------------------------------------------")
    file.write("--------------------------------------------------------------------------------------------------\n")
    file.close()

def printRegStatus():
    file = open("output.txt","a")
    regsName=['-F0-','-F1-','-F2-','-F3-','-F4-','-F5-','-F6-','-F7-','-F8-','-F9-','-F10-','-R1-','-R2-','-R3-','-R4-','-R5-','-R6-','-R7-']
    deps=[]
    for x in regsName:
        deps+=[str(regStatus[x[1:-1]])]
    print("Register Status")
    file.write("REGISTER STATUS\n")
    rN = getStrListP(regsName,5,0)
    p = getStrListP(deps,5,1)
    print(rN)
    file.write(rN)
    file.write("\n")
    print(p)
    file.write(p)
    file.write("\n")
    print("---------------------------------------------------------------------")
    file.write("--------------------------------------------------------------------------------------------------\n")
    file.close()
    
def printRegs():
    file = open("output.txt","a")
    o = list(regs.keys())
    o.sort()
    p=""
    for x in o:
        p+=str(regs[x])+"   "
    print("Register Values")
    file.write("Register Values\n")
    print(p)
    file.write(p)
    file.write("\n")
    file.close()

def printResStation():
    file = open("output.txt","a")
    o = list(resStation.keys())
    o.sort()
    print("Reservation Stations")
    file.write("RESERVATION STATION\n")
    enc=['--BUSY--','--OP--','--Vj--','--Vk--','--Qj--','--Qk--','--ADDR--','--STdep--','--TIMEex--','--TIMEcb']
    strEnc=getStrListP(enc,10,0)
    print(strEnc)
    file.write(strEnc)
    file.write("\n")
    for x in o:
        rS=getStrListP(resStation[x],10,2)
        print(rS)
        file.write(rS)
        file.write("\n")
    print("---------------------------------------------------------------------")
    file.write("--------------------------------------------------------------------------------------------------\n")
    file.close()
    
def issue(nI,inst):
    if (inst[0]=="L.D"):
        updateResSta(nI,6,inst[3]+"+"+inst[2])
        updateResSta(nI,8,pE)
    elif(inst[0]=="S.D"):
        updateResSta(nI,6,inst[3]+"+"+inst[2])
        updateResSta(nI,8,pE)
        if(regStatus[inst[1]]!="0"):
            updateResSta(nI,7,regStatus[inst[1]])
    if(inst[2] in list(regStatus.keys())):
        if(regStatus[inst[2]]!="0"):
            updateResSta(nI,4,regStatus[inst[2]])
        else:
            updateResSta(nI,2,inst[2])
    else:
        updateResSta(nI,2,inst[2])
    if(inst[3] in list(regStatus.keys())):    
        if(regStatus[inst[3]]!="0"):
            updateResSta(nI,5,regStatus[inst[3]])
        else:
            updateResSta(nI,3,inst[3])
    else:
        updateResSta(nI,3,inst[2])

    updateResSta(nI,0,True)
    inStatus[nI][1][0]=True
    if(inst[0]!="S.D"):
        regStatus[inst[1]]=str(nI)
    if(inst[0]!="L.D" and inst[0]!="S.D"):
        updateResSta(nI,8,pF)
    updateResSta(nI,9,tCB)    
    updateResSta(nI,1,inst[0])

def updateResSta(nI,index,value):
    resStation[nI][index]=value

def doIssue():
    o = list(inStatus.keys())
    o.sort()
    for x in o:
        issue(x,inStatus[x][0])
            
def execute(nI,time):
    global instFinish
    global cont
    updateResSta(nI,8,time-1)
    if(time-1 == 0):
        inStatus[nI][1][1]=True
        instFinish+=[nI]
        instExe[nI][2]=cont
    

def write(nI,t):
    updateResSta(nI,9,t-1)
    if(t-1==0):
        o = list(regStatus.keys())
        o.sort()
        for x in o:
            if (regStatus[x]==str(nI)):
                regStatus[x]="0"
        inStatus[nI][1][2]=True

def upResSat():
    o = list(resStation.keys())
    o.sort()
    rS=list(regStatus.values())
    for x in o:
        if(resStation[x][8]==0 and resStation[x][9]==0):
            resStation[x]=[False,"","","","","","","",0,0]
        else:
            if(resStation[x][4]!="" and not (resStation[x][4] in rS)):
                resStation[x][4]=""
                updateResSta(x,2,inStatus[x][0][2])
            if(resStation[x][5]!="" and not (resStation[x][5] in rS)):
                resStation[x][5]=""
                updateResSta(x,3,inStatus[x][0][3])
            if(resStation[x][7]!="" and not (resStation[x][7] in rS)):
                resStation[x][7]=""
                
                
                
     
def doWrite():
    o = list(resStation.keys())
    o.sort()
    for x in o:
        if(resStation[x][8]==0 and resStation[x][0]):
            write(x,resStation[x][9])
            break                        
    
def doExe():
    o = list(resStation.keys())
    o.sort()
    flagE=False
    flagF=False
    toExe=[]
    for x in o:
        if(resStation[x][1]=="L.D" or resStation[x][1]=="S.D" ):
            if(resStation[x][4] == "" and
               resStation[x][5] == "" and resStation[x][7] == ""
               and not flagE and resStation[x][8]!=0):
                toExe+=[[x,resStation[x][8]]]
                flagE=True
        else:
            if(resStation[x][4] == "" and
               resStation[x][5] == "" and not flagF
               and resStation[x][8]!=0):
                toExe+=[[x,resStation[x][8]]]
                flagF=True
        if(flagF and flagE):
            break
    for x in toExe:
        execute(x[0],x[1])

def finish():
    o = list(resStation.keys())
    o.sort()
    flag=True
    for x in o:
        if(resStation[x][0]):
            flag=False
            break
    return flag


frame = tk.Frame(ventana)
frame.pack()



button = tk.Button(frame, 
                   text="SET VALUES", 
                   fg="deep sky blue",
                   command=write_ventana)
button.pack(side=tk.LEFT)


T = tk.Text(ventana,height=35,width=100)
T.pack()


def writeToWindow():
    global T
    file = open("output.txt","r")
    T.delete('1.0', END)
    T.insert('end',file.read())
    file.close()
    
def reset():
    global T
    global instInit
    global instFinish
    global pE
    global pF
    global tCB
    global init
    global Cont
    global cont
    global ciclos
    global inStatus
    global resStation
    inStatus = {}
    resStation = {}
    T.delete('1.0', END)
    instInit  = []
    instFinish = []
    pE = 0
    pF = 0
    tCB = 0
    init = True
    Cont = 0
    cont = 0
    ciclos = 0
    loadCode()
    
exit_btn=Button(frame,text='EXIT',fg="red",command=ventana.destroy)
exit_btn.pack(side=tk.RIGHT)

button = tk.Button(frame, 
                   text="RESET", 
                   fg="red",
                   command=reset)
button.pack(side=tk.RIGHT)



def getExeInst():
    global instExe
    o = list(instExe.keys())
    o.sort()
    p=""
    for x in o:
        p+=getStrListP(instExe[x],15,0)
        p+="\n"
    return p
    
    
    
def nextTransition():
    global T
    global init
    global cont
    if(pE == 0 or pF == 0 or tCB == 0):
        messagebox.showinfo("ERROR!", "You must set the Tomasulo parameters")
    elif(init):
        init = False
        file = open("output.txt","a")
        deleteContent(file)
        print("---------------------------------------------CICLO: "+str(cont)+"------------------------------------------")
        file.write("-----------------------------------------------CICLO: "+str(cont)+"-------------------------------------------\n")
        file.close()
        doIssue()  
        printRegStatus()
        printInStatus()
        printResStation()
        cont+=1
        writeToWindow()
        
    
    else:
        if(finish()):
            file = open("output.txt","a")
            deleteContent(file)
            print("---------------------------END-----------------------")
            file.write("---------------------------END-----------------------\n")
            print("Comportamiento de Instrucciones")
            file.write("Comportamiento de Instrucciones\n")
            enc = getStrListP(["Instruction","InOrder","OutOfOrder"],15,0)
            file.write(enc+"\n")
            print(enc)
            file.write(getExeInst())
            print(getExeInst())
            print("---------------------------END-----------------------")
            file.write("---------------------------END-----------------------\n")
            file.close()
            writeToWindow()
        else:
            file = open("output.txt","a")
            deleteContent(file)
            print("---------------------------------------------CICLO: "+str(cont)+"------------------------------------------")
            file.write("-----------------------------------------------CICLO: "+str(cont)+"-------------------------------------------\n")
            file.close()
            doWrite()
            doExe()
            upResSat()
            printRegStatus()
            printInStatus()
            printResStation()
            cont+=1
            writeToWindow()
    
        
slogan = tk.Button(frame,
                   text="NEXT TRANSITION",
                   fg = "green",
                   command=nextTransition)
slogan.pack(side=tk.LEFT)



ventana.mainloop()

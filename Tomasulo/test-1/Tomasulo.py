def OPtransformer(op):
    if op=='ADD':
        op='+'
        return op
    elif op=='SUB':
        op='-'
        return op
    elif op=='MUL':
        op='*'
        return op
    elif op=='DIV':
        op='/'
        return op
def keepgoing():
    for i in range(adder+multiplier):
        if RS[i][1]!=None:
            return 1
    if Buffer1!=[] and Buffer2!=[]:
        return 1
    return 0
def mappingREG(REG):
    if RAT[REG]!=None:
        return RAT[REG]
    else:
        return Register[REG]
def mappingRAT(value):
    for k,v in RAT.items():
        if v==value:
            return k
def bufferDescription(rs,op,reg2,reg3):
    buffer=str(rs)+'='+str(reg2)+op+str(reg3)
    return buffer
def buffercal(buffer):

    if buffer[1]=='+':
        return buffer[2]+buffer[3]
    elif buffer[1]=='*':
        return buffer[2]*buffer[3]
    elif buffer[1]=='-':
        return buffer[2]-buffer[3]
    elif buffer[1]=='/':
        return buffer[2]/buffer[3]
def writeresult(REG,value):
    rs=mappingRAT(REG)
    if rs==None:
        for i in range(multiplier+adder):
            if RS[i][0]==REG:
                RS[i][1]=None
                RS[i][2]=None
                RS[i][3]=None
                RS[i][4]=None
            if RS[i][2]==REG:
                RS[i][2]=value
            if RS[i][3]==REG:
                RS[i][3]=value
    else:
        for i in range(multiplier+adder):
            if RS[i][0]==RAT[rs]:
                RS[i][1]=None
                RS[i][2]=None
                RS[i][3]=None
                RS[i][4]=None
            if RS[i][2]==RAT[rs]:
                RS[i][2]=value
            if RS[i][3]==RAT[rs]:
                RS[i][3]=value
        RAT[rs]=None
        Register[rs]=value
def updateRAT(REG,rs):
    RAT[REG]=rs
def updateRF(REG,rs):
    Register[REG]=rs
def updateRS(rs,OP,REG1,REG2,time):
    for i in range (multiplier+adder):
        if RS[i][0]==rs:
            RS[i][1]=OP
            RS[i][2]=REG1
            RS[i][3]=REG2
            RS[i][4]=time
def printTable():
    print('-----Register-----')
    for k,v in  Register.items():
        print(k,v)
    print('-----RAT-----')
    for k,v in  RAT.items():
        print(k,v)
    print('-------RS-------')
    for i in range(multiplier+adder):
        if i<=2:
            print(RS[i][0],RS[i][1],RS[i][2],RS[i][3])
            if Buffer1!=[] and i==2:
                print('(ADD)Buffer',bufferDescription(Buffer1[0],Buffer1[1],Buffer1[2],Buffer1[3]))
            elif i==2:
                print('(ADD)Buffer=Empty')
        else:
            print(RS[i][0],RS[i][1],RS[i][2],RS[i][3])
            if Buffer2!=[] and i==4:
                print('(MUL)Buffer',bufferDescription(Buffer2[0],Buffer2[1],Buffer2[2],Buffer2[3]))
            elif i==4:
                print('(MUL)Buffer=Empty')
if __name__ == "__main__":
    f = open('./input2.txt', 'r')
    lines = f.readlines()
    print('Set ADD: 2 Cycle  MUL: 10 Cycle  DIV: 20 Cycle')
    Register={'F1':1,'F2':1,'F3':2,'F4':None,'F5':None}
    RAT={'F1':None,'F2':None,'F3':None,'F4':None,'F5':None}

    Buffer1=[]
    Buffer2=[]

    addcycle=2 #set cycle
    mulcycle=10
    divcycle=20
    adder=3
    multiplier=2
    op=[]
    reg1=[]
    reg2=[]
    reg3=[]
    initialstate=1
    issue=[[None for i in range(4)] for j in range(len(lines))]
    buffercycle1=0
    buffercycle2=0
    currentcycle=1

    for line in lines:
        str1=line.split(' ')[0]
        str2=line.strip(str1+' ')
        str3=str2.split(',')
        str3[2]=str3[2].strip('\n') #split data ,str1=operator str3= REG,REG,REG
        op.append(str1)
        reg1.append(str3[0])
        reg2.append(str3[1])
        reg3.append(str3[2])
    for i in range(len(lines)):  #create Instruction table
        issue[i][0]=op[i]
        issue[i][1]=reg1[i]
        issue[i][2]=reg2[i]
        issue[i][3]=reg3[i]

    RS=[[None for i in range(5)] for j in range(multiplier+adder)]
    for i in range(5):
        RS[i][0]='RS'+str(i+1)  #create RS table
#    while ((keepgoint())and(initialstate))!=1:
    printTable()

    while (keepgoing()or(initialstate))==1:
        initialstate=0
        if(len(issue)!=0):
            if(issue[0][0]=='ADD' or issue[0][0]== 'ADDI' or issue[0][0]=='SUB'):
                for i in range(adder):
                    if RS[i][1]==None:
                        updateRS(RS[i][0],OPtransformer(issue[0][0]),mappingREG(issue[0][2]),mappingREG(issue[0][3]),currentcycle)
                        updateRAT(issue[0][1],RS[i][0])
                        print('------------------------------Cycle %d------------------------------'%currentcycle)
                        printTable()
                        del issue[0]
                        break
            else:
                for i in range(multiplier):
                    if RS[i+3][1]==None:
                        updateRS(RS[i+3][0],OPtransformer(issue[0][0]),mappingREG(issue[0][2]),mappingREG(issue[0][3]),currentcycle)
                        updateRAT(issue[0][1],RS[i+3][0])
                        print('------------------------------Cycle %d------------------------------'%currentcycle)
                        printTable()
                        del issue[0]
                        break
        if Buffer1==[]:
            for i in range(adder):
                if (type(RS[i][2])==int or type(RS[i][2])==float) and\
                        (type(RS[i][3])==int or type(RS[i][3])==float) and\
                        (currentcycle>RS[i][4]):
                    Buffer1.append(RS[i][0])
                    Buffer1.append(RS[i][1])
                    Buffer1.append(RS[i][2])
                    Buffer1.append(RS[i][3])
                    buffercycle1=currentcycle
                    print('------------------------------Cycle %d------------------------------'%currentcycle)
                    printTable()
                    break
        if Buffer2==[]:
            for i in range(multiplier):
                if (type(RS[i+3][2])==int or type(RS[i+3][2])==float) and\
                        (type(RS[i+3][3])==int or type(RS[i+3][3])==float) and\
                        (currentcycle>RS[i+3][4]):
                    Buffer2.append(RS[i+3][0])
                    Buffer2.append(RS[i+3][1])
                    Buffer2.append(RS[i+3][2])
                    Buffer2.append(RS[i+3][3])
                    buffercycle2=currentcycle
                    print('------------------------------Cycle %d------------------------------'%currentcycle)
                    printTable()
                    break
        if Buffer1!=[]:
            if currentcycle==(buffercycle1+addcycle):
                writeresult(Buffer1[0],buffercal(Buffer1))
                Buffer1.clear()
                print('------------------------------Cycle %d------------------------------'%currentcycle)
                printTable()

        if Buffer2!=[]:
            if currentcycle==(buffercycle2+mulcycle) and Buffer2[1]=='*':
                writeresult(Buffer2[0],buffercal(Buffer2))
                Buffer2.clear()
                print('------------------------------Cycle %d------------------------------'%currentcycle)
                printTable()

            if (currentcycle==(buffercycle2+divcycle)) and Buffer2[1]=='/':
                writeresult(Buffer2[0],buffercal(Buffer2))
                Buffer2.clear()
                print('------------------------------Cycle %d------------------------------'%currentcycle)
                printTable()
        currentcycle=currentcycle+1


  #  for j in range(multiplier+adder):
  #      if type(RS[j][2])==(int) and type(RS[j][3])==(int):


    f.close()
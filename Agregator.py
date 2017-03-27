def transform(prev, records):
    """ This is the method to implement the agregate statistic you wish to do.
    You can also transform the table here.
    In our case, we will transform the input record into: 
      prov, kab, kec, kel, n_tps, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, pemilih, php   
    
    
    """
    # well this is for kelurahan. For kecamatan, delete the fourth field (kelurahan).
    if not prev:
        prev = [records[0], records[1], records[2], records[3], 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] #not added yet
         
    prev[4] = prev[4]+1
    prev[5] = prev[5]+int(records[5])
    prev[6] = prev[6]+int(records[6])
    prev[7] = prev[7]+int(records[7])
    prev[8] = prev[8]+int(records[8])
    prev[9] = prev[9]+int(records[9])
    prev[10] = prev[10]+int(records[10])
    prev[11] = prev[11]+int(records[11])
    prev[12] = prev[12]+int(records[12])
    prev[13] = prev[13]+int(records[13])
    prev[14] = prev[14]+int(records[14])
    prev[15] = prev[15]+int(records[15])
    prev[16] = prev[16]+int(records[16])
    return prev


def kecamatanAsKey(records):
    """ This function will return a value as the basis for grouping the record """
    idr = records[2].upper() 
    return idr
    
def kabupatenAsKey(records):
    """ This function will return a value as the basis for grouping the record """
    idr = records[1].upper() 
    return idr

def keckelAsKey(records):
    idr = records[2] + "." + records[3] 
    idr = idr.upper()
    return idr

from sys import argv
    
#getKey = kecamatanAsKey
getKey = keckelAsKey
#getKey = kabupatenAsKey

fileName = argv[1]
header = False

file = open(fileName)

if header: #skip the header
    file.readline() 
    
idIndex = 17
DELIMITER = ","
result = {}    

#print "hehe"

for line in file: #read the rest
    line = line[0:-1]
    records = line.split(DELIMITER)
    idd = getKey(records)
    #print id
    prev = []
    try:
        prev = result[idd]
    except:
        pass
        
    result[idd] = transform(prev, records)

for x in result:
    print x+",%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s" % tuple(result[x])

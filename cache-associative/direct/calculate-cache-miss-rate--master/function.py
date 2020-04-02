import math

def convert_cachesize_to_int(cacheSize):
	coverted_cachesize = ""
	if cacheSize.isdigit()==False:
		for char in cacheSize:
			if char.isdigit():
				coverted_cachesize = coverted_cachesize + char
		return int(coverted_cachesize)*1024 #str
	else:
		return int(cacheSize) 

def convert_string_to_int(stringToBeConverted):
	return int(stringToBeConverted, 16)

def determine_cache_block(memoryBlock, numberofCacheBlock):
	return memoryBlock%numberofCacheBlock


def determine_memory_block(address, blockSize):
	return math.floor(address/blockSize)

def determine_tag(memoryBlock, numberofCacheBlock):
	return math.floor(memoryBlock/numberofCacheBlock)

def load_address(filename):
	addressList = []
	fileObject = open(filename, 'r')
	while True:
		Line = fileObject.readline()#Load address from file
		if Line == "":
			break
		addressList.append(convert_string_to_int(Line))
	fileObject.close()
	return addressList

def produce_cache_block_table(numberOfCacheBlock):
	cacheBlockTable = {}
	for i in range(numberOfCacheBlock):
		raw = {str(i) : {"Valid" : "No", 
				   "TAG" : 0}}
		cacheBlockTable.update(raw)
	return cacheBlockTable


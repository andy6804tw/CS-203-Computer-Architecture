import sys
from function import *

def check_cache_block(position_of_mapped_cache_block, TAG):
	global cache_block_table, hit_count, miss_count
	value_of_valid = cache_block_table[str(position_of_mapped_cache_block)]["Valid"]
	value_of_tag = cache_block_table[str(position_of_mapped_cache_block)]["TAG"]
	if value_of_valid == "No":
		cache_block_table[str(position_of_mapped_cache_block)]["Valid"] = "Yes"
		cache_block_table[str(position_of_mapped_cache_block)]["TAG"] = TAG
		miss_count = miss_count + 1
	elif value_of_valid == "Yes":
		if value_of_tag == TAG:
			hit_count = hit_count + 1
		else:
			miss_count = miss_count + 1
			cache_block_table[str(position_of_mapped_cache_block)]["TAG"] = TAG
	else:
		print("Error")

filename = sys.argv[1]
cache_size = convert_cachesize_to_int(sys.argv[2])
print(cache_size, 'cache_size')
block_size = int(sys.argv[3])
number_of_cache_block = int(cache_size/block_size)
miss_count = 0
hit_count = 0
cache_block_table = produce_cache_block_table(number_of_cache_block)
#for raw in cache_block_table:
#	print(raw)
#	for col in cache_block_table[raw]
#		print(col,":", cache_block_table[raw][col])
#addressList
address_list = load_address(filename) #load address and convert to decimal
#for address in addressList:
#	print(address)
for address in address_list:
	position_of_address_in_memory = determine_memory_block(address, block_size)
	position_of_mapped_cache_block = determine_cache_block(position_of_address_in_memory, number_of_cache_block)
	TAG = determine_tag(position_of_address_in_memory, number_of_cache_block)
	check_cache_block(position_of_mapped_cache_block, TAG)

#for raw in cacheBlockTable:
#	print(raw)
#	for col in cacheBlockTable[raw]:
#		print(col,":", cacheBlockTable[raw][col])

print("Miss Count = " + str(miss_count))
print("Hit Count = " + str(hit_count))
print("Miss Rate = " + str(miss_count/(miss_count+hit_count)))


/* rijndael.js      Rijndael Reference Implementation
   Copyright (c) 2001 Fritz Schneider
 
 This software is provided as-is, without express or implied warranty.  
 Permission to use, copy, modify, distribute or sell this software, with or
 without fee, for any purpose and by any individual or organization, is hereby
 granted, provided that the above copyright notice and this paragraph appear 
 in all copies. Distribution as a part of an application or binary must
 include the above copyright notice in the documentation and/or other materials
 provided with the application or distribution.


   As the above disclaimer notes, you are free to use this code however you
   want. However, I would request that you send me an email 
   (fritz /at/ cs /dot/ ucsd /dot/ edu) to say hi if you find this code useful
   or instructional. Seeing that people are using the code acts as 
   encouragement for me to continue development. If you *really* want to thank
   me you can buy the book I wrote with Thomas Powell, _JavaScript:
   _The_Complete_Reference_ :)

   This code is an UNOPTIMIZED REFERENCE implementation of Rijndael. 
   If there is sufficient interest I can write an optimized (word-based, 
   table-driven) version, although you might want to consider using a 
   compiled language if speed is critical to your application. As it stands,
   one run of the monte carlo test (10,000 encryptions) can take up to 
   several minutes, depending upon your processor. You shouldn't expect more
   than a few kilobytes per second in throughput.

   Also note that there is very little error checking in these functions. 
   Doing proper error checking is always a good idea, but the ideal 
   implementation (using the instanceof operator and exceptions) requires
   IE5+/NS6+, and I've chosen to implement this code so that it is compatible
   with IE4/NS4. 

   And finally, because JavaScript doesn't have an explicit byte/char data 
   type (although JavaScript 2.0 most likely will), when I refer to "byte" 
   in this code I generally mean "32 bit integer with value in the interval 
   [0,255]" which I treat as a byte.

   See http://www-cse.ucsd.edu/~fritz/rijndael.html for more documentation
   of the (very simple) API provided by this code.

                                               Fritz Schneider
                                               fritz at cs.ucsd.edu
 
*/


// Rijndael parameters --  Valid values are 128, 192, or 256

var keySizeInBits = 128;
var blockSizeInBits = 128;

///////  You shouldn't have to modify anything below this line except for
///////  the function getRandomBytes().
//
// Note: in the following code the two dimensional arrays are indexed as
//       you would probably expect, as array[row][column]. The state arrays
//       are 2d arrays of the form state[4][Nb].


// The number of rounds for the cipher, indexed by [Nk][Nb]
var roundsArray = [ ,,,,[,,,,10,, 12,, 14],, 
                        [,,,,12,, 12,, 14],, 
                        [,,,,14,, 14,, 14] ];

// The number of bytes to shift by in shiftRow, indexed by [Nb][row]
var shiftOffsets = [ ,,,,[,1, 2, 3],,[,1, 2, 3],,[,1, 3, 4] ];

// The round constants used in subkey expansion
var Rcon = [ 
0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 
0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 
0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 
0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 
0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91 ];

// Precomputed lookup table for the SBox
var SBox = [
 99, 124, 119, 123, 242, 107, 111, 197,  48,   1, 103,  43, 254, 215, 171, 
118, 202, 130, 201, 125, 250,  89,  71, 240, 173, 212, 162, 175, 156, 164, 
114, 192, 183, 253, 147,  38,  54,  63, 247, 204,  52, 165, 229, 241, 113, 
216,  49,  21,   4, 199,  35, 195,  24, 150,   5, 154,   7,  18, 128, 226, 
235,  39, 178, 117,   9, 131,  44,  26,  27, 110,  90, 160,  82,  59, 214, 
179,  41, 227,  47, 132,  83, 209,   0, 237,  32, 252, 177,  91, 106, 203, 
190,  57,  74,  76,  88, 207, 208, 239, 170, 251,  67,  77,  51, 133,  69, 
249,   2, 127,  80,  60, 159, 168,  81, 163,  64, 143, 146, 157,  56, 245, 
188, 182, 218,  33,  16, 255, 243, 210, 205,  12,  19, 236,  95, 151,  68,  
23,  196, 167, 126,  61, 100,  93,  25, 115,  96, 129,  79, 220,  34,  42, 
144, 136,  70, 238, 184,  20, 222,  94,  11, 219, 224,  50,  58,  10,  73,
  6,  36,  92, 194, 211, 172,  98, 145, 149, 228, 121, 231, 200,  55, 109, 
141, 213,  78, 169, 108,  86, 244, 234, 101, 122, 174,   8, 186, 120,  37,  
 46,  28, 166, 180, 198, 232, 221, 116,  31,  75, 189, 139, 138, 112,  62, 
181, 102,  72,   3, 246,  14,  97,  53,  87, 185, 134, 193,  29, 158, 225,
248, 152,  17, 105, 217, 142, 148, 155,  30, 135, 233, 206,  85,  40, 223,
140, 161, 137,  13, 191, 230,  66, 104,  65, 153,  45,  15, 176,  84, 187,  
 22 ];

// Precomputed lookup table for the inverse SBox
var SBoxInverse = [
 82,   9, 106, 213,  48,  54, 165,  56, 191,  64, 163, 158, 129, 243, 215, 
251, 124, 227,  57, 130, 155,  47, 255, 135,  52, 142,  67,  68, 196, 222, 
233, 203,  84, 123, 148,  50, 166, 194,  35,  61, 238,  76, 149,  11,  66, 
250, 195,  78,   8,  46, 161, 102,  40, 217,  36, 178, 118,  91, 162,  73, 
109, 139, 209,  37, 114, 248, 246, 100, 134, 104, 152,  22, 212, 164,  92, 
204,  93, 101, 182, 146, 108, 112,  72,  80, 253, 237, 185, 218,  94,  21,  
 70,  87, 167, 141, 157, 132, 144, 216, 171,   0, 140, 188, 211,  10, 247, 
228,  88,   5, 184, 179,  69,   6, 208,  44,  30, 143, 202,  63,  15,   2, 
193, 175, 189,   3,   1,  19, 138, 107,  58, 145,  17,  65,  79, 103, 220, 
234, 151, 242, 207, 206, 240, 180, 230, 115, 150, 172, 116,  34, 231, 173,
 53, 133, 226, 249,  55, 232,  28, 117, 223, 110,  71, 241,  26, 113,  29, 
 41, 197, 137, 111, 183,  98,  14, 170,  24, 190,  27, 252,  86,  62,  75, 
198, 210, 121,  32, 154, 219, 192, 254, 120, 205,  90, 244,  31, 221, 168,
 51, 136,   7, 199,  49, 177,  18,  16,  89,  39, 128, 236,  95,  96,  81,
127, 169,  25, 181,  74,  13,  45, 229, 122, 159, 147, 201, 156, 239, 160,
224,  59,  77, 174,  42, 245, 176, 200, 235, 187,  60, 131,  83, 153,  97, 
 23,  43,   4, 126, 186, 119, 214,  38, 225, 105,  20,  99,  85,  33,  12,
125 ];

// Precomputed lookup table for fault location capabilities
var Ynh = new Array();
Ynh[0] = [1, 7, 60679, 21713, 43946, 21077, 44359, 33793];
Ynh[1] = [2, 57344, 3803, 35626, 22357, 43594, 36443, 32801];
Ynh[2] = [4, 3328, 46861, 29777, 44714, 21848, 42781, 1057];
Ynh[3] = [8, 176, 2942, 35374, 23893, 6826, 11102, 33824];
Ynh[4] = [16, 112, 53374, 19733, 47786, 9557, 54394, 16408];
Ynh[5] = [32, 14, 60848, 45736, 30037, 42154, 58808, 536];
Ynh[6] = [64, 53248, 28891, 17687, 60074, 21893, 29146, 16912];
Ynh[7] = [128, 2816, 47072, 41704, 54613, 43681, 46562, 16904];
Ynh[8] = [256, 1792, 2029, 53588, 43691, 21842, 18349, 388];
Ynh[9] = [512, 224, 56078, 10891, 21847, 19114, 23438, 8576];
Ynh[10] = [1024, 13, 3511, 20852, 43694, 22613, 7591, 8452];
Ynh[11] = [2048, 45056, 32267, 11914, 21853, 43546, 24107, 8324];
Ynh[12] = [4096, 28672, 32464, 5453, 43706, 21797, 31444, 6208];
Ynh[13] = [8192, 3584, 45293, 43186, 21877, 43684, 47333, 6146];
Ynh[14] = [16384, 208, 56176, 5957, 43754, 34133, 55921, 4162];
Ynh[15] = [32768, 11, 57527, 59554, 21973, 41386, 58037, 2114];

// This method circularly shifts the array left by the number of elements
// given in its parameter. It returns the resulting array and is used for 
// the ShiftRow step. Note that shift() and push() could be used for a more 
// elegant solution, but they require IE5.5+, so I chose to do it manually. 

function cyclicShiftLeft(theArray, positions) {
  var temp = theArray.slice(0, positions);
  theArray = theArray.slice(positions).concat(temp);
  return theArray;
}

// Cipher parameters ... do not change these
var Nk = keySizeInBits / 32;                   
var Nb = blockSizeInBits / 32;
var Nr = roundsArray[Nk][Nb];

// Multiplies the element "poly" of GF(2^8) by x. See the Rijndael spec.

function xtime(poly) {
  poly <<= 1;
  return ((poly & 0x100) ? (poly ^ 0x11B) : (poly));
}

// Multiplies the two elements of GF(2^8) together and returns the result.
// See the Rijndael spec, but should be straightforward: for each power of
// the indeterminant that has a 1 coefficient in x, add y times that power
// to the result. x and y should be bytes representing elements of GF(2^8)

function mult_GF256(x, y) {
  var bit, result = 0;
  
  for (bit = 1; bit < 256; bit *= 2, y = xtime(y)) {
    if (x & bit) 
      result ^= y;
  }
  return result;
}

function injectFaultInByte(inByte) {
  whichBit = Math.round(Math.random()*7);
  flippedByte = Math.pow(2, whichBit);
  outByte = inByte ^ flippedByte;
  return outByte;
}

function parity8(x) {
  x = x ^ (x >> 4);
  x = x ^ (x >> 2);
  x = x ^ (x >> 1);
  return (x & 1);
}

function generateParityArray(byteArray) {
  var result = [];// = [3,4,5,6];
  for (var i=0; i<byteArray.length; i++) {
    result[i] = parity8(byteArray[i]);
  }
  return result;
}

function generateErrorStateArray(byteArray) {
  var result = [];
  for (var i=0; i<byteArray.length; i++) {
    result[i] = 0;
  }
  return result;
}

// This function is used for fault detection capabilties. It injects a single bit fault 
// in the specified round and transform.
function injectFaultForFaultDetectionOnly(stateActual, currTrans) {
  if (experiment == "Fault detection" && currRound == roundNumber && currTrans == transformation) {
    r = 0;
    c = 0;
    switch(byteNumber) {
	case 1: r = 0; c = 0; break; 
        case 2: r = 1; c = 0; break;
	case 3: r = 2; c = 0; break;
	case 4: r = 3; c = 0; break;
	case 5: r = 0; c = 1;   break;
	case 6: r = 1; c = 1;   break;
	case 7: r = 2; c = 1;   break;
	case 8: r = 3; c = 1;   break;
	case 9: r = 0; c = 2;   break;
	case 10: r = 1; c = 2;   break;
	case 11: r = 2; c = 2;   break;
	case 12: r = 3; c = 2;   break;
	case 13: r = 0; c = 3;   break;
	case 14: r = 1; c = 3;   break;
	case 15: r = 2; c = 3;   break;
	case 16: r = 3; c = 3;   break;
	case 17: r = 0; c = 4;   break;
        case 18: r = 1; c = 4;   break;
	case 19: r = 2; c = 4;   break;
	case 20: r = 3; c = 4;   break;
	case 21: r = 0; c = 5;   break;
	case 22: r = 1; c = 5;   break;
	case 23: r = 2; c = 5;   break;
	case 24: r = 3; c = 5;   break;
	case 25: r = 0; c = 6;   break;
	case 26: r = 1; c = 6;   break;
	case 27: r = 2; c = 6;   break;
	case 28: r = 3; c = 6;   break;
	case 29: r = 0; c = 7;   break;
	case 30: r = 1; c = 7;   break;
	case 31: r = 2; c = 7;   break;
	case 32: r = 3; c = 7;   break;
	default: alert("Could not determine fault injection location"); break;
    }  
    
    stateActual[r][c] = injectFaultInByte(stateActual[r][c]);	   
  }
}

// This function is used for fault location capabilities. It always injects a fault
// at the beginning of a round.
function injectFaultForFaultLocationOnly(stateActual) {
  if (experiment == "Fault location" && currRound == roundNumber) {
    r = 0;
    c = 0;
    switch(byteNumber) {
	case 1: r = 0; c = 0;   break;
        case 2: r = 1; c = 0; break;
	case 3: r = 2; c = 0; break;
	case 4: r = 3; c = 0; break;
	case 5: r = 0; c = 1;   break;
	case 6: r = 1; c = 1;   break;
	case 7: r = 2; c = 1;   break;
	case 8: r = 3; c = 1;   break;
	case 9: r = 0; c = 2;   break;
	case 10: r = 1; c = 2;   break;
	case 11: r = 2; c = 2;   break;
	case 12: r = 3; c = 2;   break;
	case 13: r = 0; c = 3;   break;
	case 14: r = 1; c = 3;   break;
	case 15: r = 2; c = 3;   break;
	case 16: r = 3; c = 3;   break;
	case 17: r = 0; c = 4;   break;
        case 18: r = 1; c = 4; break;
	case 19: r = 2; c = 4; break;
	case 20: r = 3; c = 4; break;
	case 21: r = 0; c = 5;   break;
	case 22: r = 1; c = 5;   break;
	case 23: r = 2; c = 5;   break;
	case 24: r = 3; c = 5;   break;
	case 25: r = 0; c = 6;   break;
	case 26: r = 1; c = 6;   break;
	case 27: r = 2; c = 6;   break;
	case 28: r = 3; c = 6;   break;
	case 29: r = 0; c = 7;   break;
	case 30: r = 1; c = 7;   break;
	case 31: r = 2; c = 7;   break;
	case 32: r = 3; c = 7;  break;
	default: alert("Could not determine fault injection location"); break;
    }  
    stateActual[r][c] = injectFaultInByte(stateActual[r][c]);	   
  }
}

// Update the error state matrix. It is used for fault location capabilities
function updateErrorStateMatrix(stateActual, errorState) {
 for (var i = 0; i < 4; i++) {        
    for (var j = 0; j < Nb; j++) {
       if (parity[i][j] == parity8(stateActual[i][j])) {
          errorState[i][j] = 0;
       } else {
          errorState[i][j] = 1;
       }
    }
 }
}

// Variabled associated with detecting and locating faults
// These are initialized in main.html.
var currRound = 0;
var parity;
var errorState;
var experiment = "None"
var stopCheckingForFaults = false;
var roundNumber = 0;
var transformation = "SubBytes";
var byteNumber = 0;
var faultDetected = "";
var faultLocated = "";
var whichRound = "";
var whichByte = "";
var checkInRound = 0;
var errorStatusString = "";
var expectedParityString = "";
var actualParityString = "";

// Performs the substitution step of the cipher. State is the 2d array of
// state information (see spec) and direction is string indicating whether
// we are performing the forward substitution ("encrypt") or inverse 
// substitution (anything else)

function byteSub(stateActual, stateExpected, direction) {
  var S;
  if (direction == "encrypt")           // Point S to the SBox we're using
    S = SBox;
  else
    S = SBoxInverse;

  injectFaultForFaultDetectionOnly(stateActual, "SubBytes");

  for (var i = 0; i < 4; i++) {           // Substitute for every byte in state
    for (var j = 0; j < Nb; j++) {
	// compute Sbox and parity
        if ( parity[i][j] == parity8(stateActual[i][j]) ) {
	  stateActual[i][j] = S[stateActual[i][j]]; 
	  stateExpected[i][j] = S[stateExpected[i][j]]; 
	  parity[i][j] = parity8(stateActual[i][j]);
        } else {
          stateExpected[i][j] = S[stateExpected[i][j]]; 
	  stateActual[i][j] = 0; 
	  parity[i][j] = 1;  //when fault parity, output 000000001,
	}
    }
  }
}

// Performs the row shifting step of the cipher.

function shiftRow(stateActual, stateExpected, direction) {

  injectFaultForFaultDetectionOnly(stateActual, "ShiftRows");

  for (var i=1; i<4; i++)               // Row 0 never shifts
    if (direction == "encrypt") {
       stateActual[i] = cyclicShiftLeft(stateActual[i], shiftOffsets[Nb][i]);
       stateExpected[i] = cyclicShiftLeft(stateExpected[i], shiftOffsets[Nb][i]);
       parity[i] = cyclicShiftLeft(parity[i], shiftOffsets[Nb][i]);
    } else {
       stateActual[i] = cyclicShiftLeft(stateActual[i], Nb - shiftOffsets[Nb][i]);
       stateExpected[i] = cyclicShiftLeft(stateExpected[i], Nb - shiftOffsets[Nb][i]);
       parity[i] = cyclicShiftLeft(parity[i], Nb - shiftOffsets[Nb][i]);
    }
}

// Performs the column mixing step of the cipher. Most of these steps can
// be combined into table lookups on 32bit values (at least for encryption)
// to greatly increase the speed. 

function mixColumn(stateActual, stateExpected, direction) {

  injectFaultForFaultDetectionOnly(stateActual, "MixColumns");

  var b = [];                            // Result of matrix multiplications (actual)
  var b2 = [];                           // Result of matrix multiplications (expected)  
  var p = [];				// Result of parity of MixColumns
  for (var j = 0; j < Nb; j++) {         // Go through each column...
    for (var i = 0; i < 4; i++) {        // and for each row in the column...
      if (direction == "encrypt") {
        b[i] = mult_GF256(stateActual[i][j], 2) ^          // perform mixing
               mult_GF256(stateActual[(i+1)%4][j], 3) ^ 
               stateActual[(i+2)%4][j] ^ 
               stateActual[(i+3)%4][j];
        b2[i] = mult_GF256(stateExpected[i][j], 2) ^          // perform mixing
               mult_GF256(stateExpected[(i+1)%4][j], 3) ^ 
               stateExpected[(i+2)%4][j] ^ 
               stateExpected[(i+3)%4][j];
	p[i] = ( (stateActual[i][j] >> 7) ^ parity[i][j] ^
		 (stateActual[(i+1)%4][j] >> 7) ^
		 parity[(i+2)%4][j] ^
		 parity[(i+3)%4][j]
		 ) & 0x01;	
      } else { 
        b[i] = mult_GF256(stateActual[i][j], 0xE) ^ 
               mult_GF256(stateActual[(i+1)%4][j], 0xB) ^
               mult_GF256(stateActual[(i+2)%4][j], 0xD) ^
               mult_GF256(stateActual[(i+3)%4][j], 9);
        b2[i] = mult_GF256(stateExpected[i][j], 0xE) ^ 
               mult_GF256(stateExpected[(i+1)%4][j], 0xB) ^
               mult_GF256(stateExpected[(i+2)%4][j], 0xD) ^
               mult_GF256(stateExpected[(i+3)%4][j], 9);
	p[i] = ( (stateActual[i][j] >> 7) ^ (stateActual[i][j] >> 5) ^ parity[i][j] ^
               (stateActual[(i+1)%4][j] >> 6) ^ (stateActual[(i+1)%4][j] >> 5) ^ parity[(i+1)%4][j] ^
               (stateActual[(i+2)%4][j] >> 5) ^ parity[(i+2)%4][j] ^
               (stateActual[(i+3)%4][j] >> 7) ^ (stateActual[(i+3)%4][j] >> 6) ^ (stateActual[(i+3)%4][j] >> 5)
               ) & 0x01;
      }
    }
    for (var i = 0; i < 4; i++) {         // Place result back into column
      stateActual[i][j] = b[i];
      stateExpected[i][j] = b2[i];
      parity[i][j] = p[i];
    }
  }

}

// Adds the current round key to the state information. Straightforward.

function addRoundKey(stateActual, stateExpected, roundKey) {
 
  injectFaultForFaultDetectionOnly(stateActual, "AddRoundKey");

  for (var j = 0; j < Nb; j++) {                 // Step through columns...
    stateActual[0][j] ^= (roundKey[j] & 0xFF);         // and XOR
    stateActual[1][j] ^= ((roundKey[j]>>8) & 0xFF);
    stateActual[2][j] ^= ((roundKey[j]>>16) & 0xFF);
    stateActual[3][j] ^= ((roundKey[j]>>24) & 0xFF);
    
    stateExpected[0][j] ^= (roundKey[j] & 0xFF);         // and XOR
    stateExpected[1][j] ^= ((roundKey[j]>>8) & 0xFF);
    stateExpected[2][j] ^= ((roundKey[j]>>16) & 0xFF);
    stateExpected[3][j] ^= ((roundKey[j]>>24) & 0xFF);
    
    parity[0][j] ^= parity8(roundKey[j] & 0xFF);         // and XOR
    parity[1][j] ^= parity8((roundKey[j]>>8) & 0xFF);
    parity[2][j] ^= parity8((roundKey[j]>>16) & 0xFF);
    parity[3][j] ^= parity8((roundKey[j]>>24) & 0xFF);	
  }   

}

// This function creates the expanded key from the input (128/192/256-bit)
// key. The parameter key is an array of bytes holding the value of the key.
// The returned value is an array whose elements are the 32-bit words that 
// make up the expanded key.

function keyExpansion(key) {
  var expandedKey = new Array();
  var temp;

  // in case the key size or parameters were changed...
  Nk = keySizeInBits / 32;                   
  Nb = blockSizeInBits / 32;
  Nr = roundsArray[Nk][Nb];

  for (var j=0; j < Nk; j++)     // Fill in input key first
    expandedKey[j] = 
      (key[4*j]) | (key[4*j+1]<<8) | (key[4*j+2]<<16) | (key[4*j+3]<<24);

  // Now walk down the rest of the array filling in expanded key bytes as
  // per Rijndael's spec
  for (j = Nk; j < Nb * (Nr + 1); j++) {    // For each word of expanded key
    temp = expandedKey[j - 1];
    if (j % Nk == 0) 
      temp = ( (SBox[(temp>>8) & 0xFF]) |
               (SBox[(temp>>16) & 0xFF]<<8) |
               (SBox[(temp>>24) & 0xFF]<<16) |
               (SBox[temp & 0xFF]<<24) ) ^ Rcon[Math.floor(j / Nk) - 1];
    else if (Nk > 6 && j % Nk == 4)
      temp = (SBox[(temp>>24) & 0xFF]<<24) |
             (SBox[(temp>>16) & 0xFF]<<16) |
             (SBox[(temp>>8) & 0xFF]<<8) |
             (SBox[temp & 0xFF]);
    expandedKey[j] = expandedKey[j-Nk] ^ temp;
  }
  return expandedKey;
}

// Fault checking function. This is used by the fault detection routines. It is called at the end
// of every round. 
function checkForFaultDetection(stateActual, stateExpected) {
  if (experiment == "Fault detection" && !stopCheckingForFaults) {
    for (var i=0; i<4; i++) {
      for (var j=0; j<Nb; j++) {
	if (parity[i][j] != parity8(stateActual[i][j])) {
	  faultDetected = "yes";
	  actualParityString = byteArrayToString(generateParityArray(unpackBytes(stateActual)));
	  expectedParityString = byteArrayToString(generateParityArray(unpackBytes(stateExpected)));
	  stopCheckingForFaults = true;
	}
      }
    }
  }
}

// Fault checking function. This is used by the fault location routines. It is called at the end
// of every five rounds. 
function checkForFaultLocation(stateActual, stateExpected, direction) {
   if (experiment == "Fault location" && !stopCheckingForFaults) {
       byteArray = unpackBytes(errorState);
       result = 0;
       for(var i=0; i<byteArray.length; i++) {
	   result += byteArray[i]*Math.pow(2, i);
       }

       for (var i=0; i<16; i++) {
	 zArray = Ynh[i];
	 if (direction == "encrypt") {
           for (var j=1; j<zArray.length; j++) {
	      if (zArray[j] == result) {
		  faultDetected = "Yes";
		  faultLocated = "Yes";
		  whichByte = i + 1; //Adjust to 1-based index   
		  whichRound = currRound - j + 1;   //Adjust to 1-based index
		  errorStatusString = byteArrayToString(byteArray);
		  actualParityString = byteArrayToString(unpackBytes(parity));
		  expectedParityString = byteArrayToString(generateParityArray(unpackBytes(stateExpected)));
		  stopCheckingForFaults = true;
		  break;
	      }
	   }
	 } else { // direction == decrypt
	   for (var j=zArray.length; j>0; j--) {
	      if (zArray[j] == result) {
		  faultDetected = "Yes";
		  faultLocated = "Yes";
		  whichByte = i + 1; //Adjust to 1-based index   
		  whichRound = currRound + (8 - j) - 1;   //Adjust to 1-based index
		  errorStatusString = byteArrayToString(byteArray);
		  actualParityString = byteArrayToString(unpackBytes(parity));
		  expectedParityString = byteArrayToString(generateParityArray(unpackBytes(stateExpected)));
		  stopCheckingForFaults = true;
		  break;
	      }
	   }	
	 }
	  
	 if (faultLocated == "Yes") {
	     break;
	 }
       }
   }
}

// Rijndael's round functions... 

function Round(stateActual, stateExpected, roundKey) {
  injectFaultForFaultLocationOnly(stateActual);

  byteSub(stateActual, stateExpected, "encrypt");
  shiftRow(stateActual, stateExpected, "encrypt");
  mixColumn(stateActual, stateExpected, "encrypt");
  addRoundKey(stateActual, stateExpected, roundKey);
  
  updateErrorStateMatrix(stateActual, errorState);
  
  if (checkInRound == currRound) {   
      checkForFaultLocation(stateActual, stateExpected, "encrypt");
      checkForFaultDetection(stateActual, stateExpected);
  } 
}

function InverseRound(stateActual, stateExpected, roundKey) {
  injectFaultForFaultLocationOnly(stateActual);

  addRoundKey(stateActual, stateExpected, roundKey);
  mixColumn(stateActual, stateExpected, "decrypt");
  shiftRow(stateActual, stateExpected, "decrypt");
  byteSub(stateActual, stateExpected, "decrypt");

  updateErrorStateMatrix(stateActual, errorState);
  
  if (checkInRound == currRound) {
      checkForFaultLocation(stateActual, stateExpected, "decrypt");
      checkForFaultDetection(stateActual, stateExpected);
  }
}

function FinalRound(stateActual, stateExpected, roundKey) {
  injectFaultForFaultLocationOnly(stateActual);

  byteSub(stateActual, stateExpected, "encrypt");
  shiftRow(stateActual, stateExpected, "encrypt");
  addRoundKey(stateActual, stateExpected, roundKey);

  updateErrorStateMatrix(stateActual, errorState);
      
  if (checkInRound == currRound) { 
      checkForFaultLocation(stateActual, stateExpected, "encrypt");
      checkForFaultDetection(stateActual, stateExpected);
  }
}

function InverseFinalRound(stateActual, stateExpected, roundKey){
  injectFaultForFaultLocationOnly(stateActual);

  addRoundKey(stateActual, stateExpected, roundKey);
  shiftRow(stateActual, stateExpected, "decrypt");
  byteSub(stateActual, stateExpected, "decrypt");  
  
  updateErrorStateMatrix(stateActual, errorState);
  
  if (checkInRound == currRound) {
      checkForFaultLocation(stateActual, stateExpected, "decrypt");
      checkForFaultDetection(stateActual, stateExpected);
  }
}

// encrypt is the basic encryption function. It takes parameters
// block, an array of bytes representing a plaintext block, and expandedKey,
// an array of words representing the expanded key previously returned by
// keyExpansion(). The ciphertext block is returned as an array of bytes.

function encrypt(block, expandedKey) {
  var i;  
  if (!block || block.length*8 != blockSizeInBits)
     return; 
  if (!expandedKey)
     return;

  parity = generateParityArray(block);
  parity = packBytes(parity);
  errorState = generateErrorStateArray(block);
  errorState = packBytes(errorState);

  if (experiment != "None") {
     currRound = 0;
     faultDetected = "No";
     faultLocated = "No";
     whichRound = "-";
     whichByte = "-";
     errorStatusString = "-";
     expectedParityString = "-";
     actualParityString = "-";	
     stopCheckingForFaults = false;
  }

  // blockActual is the state matrix that can be injected with faults
  // blockExpected is the state matrix that is always clean
  // Both expected and actual results can then be displayed to the user.
  blockActual = packBytes(block);
  blockExpected = packBytes(block);
  
  // Special round 0
  addRoundKey(blockActual, blockExpected, expandedKey);
  
  for (i=1; i<Nr; i++) {
    currRound = i;
    Round(blockActual, blockExpected, expandedKey.slice(Nb*i, Nb*(i+1)));
  }
  currRound = i;
  FinalRound(blockActual, blockExpected, expandedKey.slice(Nb*Nr)); 
  
  var ret = new Array(unpackBytes(blockActual), unpackBytes(blockExpected));
  return ret;
}

// decrypt is the basic decryption function. It takes parameters
// block, an array of bytes representing a ciphertext block, and expandedKey,
// an array of words representing the expanded key previously returned by
// keyExpansion(). The decrypted block is returned as an array of bytes.

function decrypt(block, expandedKey) {
  var i;
  if (!block || block.length*8 != blockSizeInBits)
     return;
  if (!expandedKey)
     return;

  parity = generateParityArray(block);
  parity = packBytes(parity);
  errorState = generateErrorStateArray(block);
  errorState = packBytes(errorState);

  if (experiment != "None") {
     currRound = Nr;
     faultDetected = "No";
     faultLocated = "No";
     whichRound = "-";
     whichByte = "-";
     errorStatusString = "-";
     expectedParityString = "-";
     actualParityString = "-";	
     stopCheckingForFaults = false;
  }
  
  // blockActual is the state matrix that can be injected with faults
  // blockExpected is the state matrix that is always clean
  // Both expected and actual results can then be displayed to the user.
  blockActual = packBytes(block);
  blockExpected = packBytes(block);
  
  InverseFinalRound(blockActual, blockExpected, expandedKey.slice(Nb*Nr)); 
  for (i = Nr - 1; i>0; i--) {
    currRound = i;
    InverseRound(blockActual, blockExpected, expandedKey.slice(Nb*i, Nb*(i+1)));
  }
  currRound = i;
  addRoundKey(blockActual, blockExpected, expandedKey);
  
  var ret = new Array(unpackBytes(blockActual), unpackBytes(blockExpected));
  return ret;
}

// This method takes a byte array (byteArray) and converts it to a string by
// applying String.fromCharCode() to each value and concatenating the result.
// The resulting string is returned. Note that this function SKIPS zero bytes
// under the assumption that they are padding added in formatPlaintext().
// Obviously, do not invoke this method on raw data that can contain zero
// bytes. It is really only appropriate for printable ASCII/Latin-1 
// values. Roll your own function for more robust functionality :)

function byteArrayToString(byteArray) {
  var result = "";
  for(var i=0; i<byteArray.length; i++) {
      result += byteArray[i].toString(10);
  } 
  return result;
}

// This function takes an array of bytes (byteArray) and converts them
// to a hexadecimal string. Array element 0 is found at the beginning of 
// the resulting string, high nibble first. Consecutive elements follow
// similarly, for example [16, 255] --> "10ff". The function returns a 
// string.

function byteArrayToHex(byteArray) {
  var result = "";
  if (!byteArray)
    return;
  for (var i=0; i<byteArray.length; i++)
    result += ((byteArray[i]<16) ? "0" : "") + byteArray[i].toString(16);

  return result;
}

// This function converts a string containing hexadecimal digits to an 
// array of bytes. The resulting byte array is filled in the order the
// values occur in the string, for example "10FF" --> [16, 255]. This
// function returns an array. 

function hexToByteArray(hexString) {
  var byteArray = [];
  if (hexString.length % 2)             // must have even length
    return;
  if (hexString.indexOf("0x") == 0 || hexString.indexOf("0X") == 0)
    hexString = hexString.substring(2);
  for (var i = 0; i<hexString.length; i += 2) 
    byteArray[Math.floor(i/2)] = parseInt(hexString.slice(i, i+2), 16);
  return byteArray;
}

// This function packs an array of bytes into the four row form defined by
// Rijndael. It assumes the length of the array of bytes is divisible by
// four. Bytes are filled in according to the Rijndael spec (starting with
// column 0, row 0 to 3). This function returns a 2d array.

function packBytes(octets) {
  var state = new Array();
  if (!octets || octets.length % 4)
    return;

  state[0] = new Array();  state[1] = new Array(); 
  state[2] = new Array();  state[3] = new Array();
  for (var j=0; j<octets.length; j+= 4) {
     state[0][j/4] = octets[j];
     state[1][j/4] = octets[j+1];
     state[2][j/4] = octets[j+2];
     state[3][j/4] = octets[j+3];
  }
  return state;  
}

// This function unpacks an array of bytes from the four row format preferred
// by Rijndael into a single 1d array of bytes. It assumes the input "packed"
// is a packed array. Bytes are filled in according to the Rijndael spec. 
// This function returns a 1d array of bytes.

function unpackBytes(packed) {
  var result = new Array();
  for (var j=0; j<packed[0].length; j++) {
    result[result.length] = packed[0][j];
    result[result.length] = packed[1][j];
    result[result.length] = packed[2][j];
    result[result.length] = packed[3][j];
  }
  return result;
}

// This function takes a prospective plaintext (string or array of bytes)
// and pads it with zero bytes if its length is not a multiple of the block 
// size. If plaintext is a string, it is converted to an array of bytes
// in the process. The type checking can be made much nicer using the 
// instanceof operator, but this operator is not available until IE5.0 so I 
// chose to use the heuristic below. 

function formatPlaintext(plaintext) {
  var bpb = blockSizeInBits / 8;               // bytes per block
  var i;

  // if primitive string or String instance
  if (typeof plaintext == "string" || plaintext.indexOf) {
    plaintext = plaintext.split("");
    // Unicode issues here (ignoring high byte)
    for (i=0; i<plaintext.length; i++)
      plaintext[i] = plaintext[i].charCodeAt(0) & 0xFF;
  } 

  for (i = bpb - (plaintext.length % bpb); i > 0 && i < bpb; i--) 
    plaintext[plaintext.length] = 0;
  
  return plaintext;
}

// Returns an array containing "howMany" random bytes. YOU SHOULD CHANGE THIS
// TO RETURN HIGHER QUALITY RANDOM BYTES IF YOU ARE USING THIS FOR A "REAL"
// APPLICATION.

function getRandomBytes(howMany) {
  var i;
  var bytes = new Array();
  for (i=0; i<howMany; i++)
    bytes[i] = Math.round(Math.random()*255);
  return bytes;
}

// rijndaelEncrypt(plaintext, key, mode)
// Encrypts the plaintext using the given key and in the given mode. 
// The parameter "plaintext" can either be a string or an array of bytes. 
// The parameter "key" must be an array of key bytes. If you have a hex 
// string representing the key, invoke hexToByteArray() on it to convert it 
// to an array of bytes. The third parameter "mode" is a string indicating
// the encryption mode to use, either "ECB" or "CBC". If the parameter is
// omitted, ECB is assumed.
// 
// An array of bytes representing the cihpertext is returned. To convert 
// this array to hex, invoke byteArrayToHex() on it. If you are using this 
// "for real" it is a good idea to change the function getRandomBytes() to 
// something that returns truly random bits.

function rijndaelEncrypt(plaintext, key, mode) {
  var expandedKey, i, aBlock;
  var bpb = blockSizeInBits / 8;          // bytes per block
  var ct;                                 // ciphertext
  var ct2;
  
  if (!plaintext || !key)
    return;
  if (key.length*8 != keySizeInBits)
    return; 
  if (mode == "CBC")
    ct = getRandomBytes(bpb);             // get IV
  else {
    mode = "ECB";
    ct = new Array();
    ct2 = new Array();
  }

  // convert plaintext to byte array and pad with zeros if necessary. 
  plaintext = formatPlaintext(plaintext);
  expandedKey = keyExpansion(key);
  
  for (var block=0; block<plaintext.length / bpb; block++) {
    aBlock = plaintext.slice(block*bpb, (block+1)*bpb);
    if (mode == "CBC")
      for (var i=0; i<bpb; i++) 
        aBlock[i] ^= ct[block*bpb + i];
    var ret = encrypt(aBlock, expandedKey);
    ct = ct.concat(ret[0]);
    ct2 = ct2.concat(ret[1]);
  }

  var result = new Array(ct, ct2);
  return result;
}

// rijndaelDecrypt(ciphertext, key, mode)
// Decrypts the using the given key and mode. The parameter "ciphertext" 
// must be an array of bytes. The parameter "key" must be an array of key 
// bytes. If you have a hex string representing the ciphertext or key, 
// invoke hexToByteArray() on it to convert it to an array of bytes. The
// parameter "mode" is a string, either "CBC" or "ECB".
// 
// An array of bytes representing the plaintext is returned. To convert 
// this array to a hex string, invoke byteArrayToHex() on it. To convert it 
// to a string of characters, you can use byteArrayToString().

function rijndaelDecrypt(ciphertext, key, mode) {
  var expandedKey;
  var bpb = blockSizeInBits / 8;          // bytes per block
  var pt = new Array();                   // plaintext array
  var pt2 = new Array();
  var aBlock;                             // a decrypted block
  var block;                              // current block number

  if (!ciphertext || !key || typeof ciphertext == "string")
    return;
  if (key.length*8 != keySizeInBits)
    return; 
  if (!mode)
    mode = "ECB";                         // assume ECB if mode omitted

  expandedKey = keyExpansion(key);
 
  // work backwards to accomodate CBC mode 
  for (block=(ciphertext.length / bpb)-1; block>0; block--) {
    ret = decrypt(ciphertext.slice(block*bpb,(block+1)*bpb), expandedKey);  	
    aBlock = ret[0];
    aBlock2 = ret[1];
     
    if (mode == "CBC") {
      for (var i=0; i<bpb; i++) 
        pt[(block-1)*bpb + i] = aBlock[i] ^ ciphertext[(block-1)*bpb + i];
    } else {
      pt = aBlock.concat(pt);
      pt2 = aBlock2.concat(pt2);
    }
  }

  // do last block if ECB (skips the IV in CBC)
  if (mode == "ECB") {
    ret = decrypt(ciphertext.slice(0, bpb), expandedKey);
    aBlock = ret[0];
    aBlock2 = ret[1];
    pt = aBlock.concat(pt);
    pt2 = aBlock.concat(pt2);
  }

 var result = new Array(pt, pt2);
 return result;
}

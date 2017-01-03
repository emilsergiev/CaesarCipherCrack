# CaesarCipherCrack
Cracking the Caesar cipher

When encrypted text is entered, it display the original, without asking what is the offset.

How it works:
- It is known that the 3 most common letters in the English language are e, t, h
  (according to other crypt analists they are e, t, a! also, the most distinctive ordinary ABC letters between languages are a, e, o, i and l, n, t, d.)
- Finds the most common symbol in the encrypted text
- Calculates how much is the offset from it to e, t, h
  (if one of these 3 happens to be the most common char in the text then we use the 'a' char instead)
- The three decrypted versions are displayed of the original text
- If the user is not satisfied with the result, the remaining 22 versions of the text are shown

# A “better version**” (not implemented at this time***):
- The words for each of the 25 versions of the text are compared with a previously introduced dictionary (English – 10,000 words)
- The text that has the most words from the dictionary, is displayed on the screen

** (according to my school) I personally do not agree for this to be a better version for the following reasons:
- First reason is that this version will only work for English text which has been encrypted. While the original implemented version will work for any foreign language encrypted text that uses the pure Latin alphabet! 
- Also, it will needlessly slow down the program and will be very RAM memory intensive and slowing down the whole computer system as well.

*** However, I will be implementing this, in my opinion, “bad version” just for a school project purposes in the very near future.


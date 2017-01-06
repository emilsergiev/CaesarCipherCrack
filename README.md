# CaesarCipherCrack
Cracking the Caesar cipher

When encrypted text is entered, it display the original, without asking what is the offset.

How it works:
- It is known that the 3 most common letters in the English language are e, t, h
  (according to other crypt analists they are e, t, a... also, the most distinctive ordinary ABC letters between languages are a, e, o, i and l, n, t, d.)
- Finds the most common symbol in the encrypted text
- Calculates how much is the offset from it to e, t, h
  (if one of those 3 happens to be the most common char in the encrypted text then we use the 'a' char instead)
- The three decrypted versions of the original text are displayed on the screen
- If the user is not satisfied with the results, the remaining 22 versions of the text are shown

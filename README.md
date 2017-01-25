# CaesarCipherCrack
Cracking the Caesar cipher Version 1.0

When encrypted text is entered, it display the original, without asking what is the offset.

### How it works:
- It is known that the 3 most common letters in the English language are e, t, h (or a)
  -- according to other crypt annalists they are: e, t, a... 
  also, the most distinctive ordinary ABC letters between languages are a, e, o, i and l, n, t, d.
- First it finds the most common symbol in the encrypted text
- Then it calculates how much is the offset from it to e, t, h
  (if one of those 3 happens to be the most common char in the encrypted text then it uses the 'a' char instead)
- The three decrypted versions of the original text are displayed on the screen
- If the user is not satisfied with the results, the remaining 22 versions of the text are shown

# CaesarCipherCrackV2
Cracking the Caesar cipher Version 2.0

A different version using a different method

### How it works:
- The words for each of the 25 versions of the text are compared with a previously introduced dictionary (English – 10,000 words)
- The text that has the most words from the dictionary, is displayed on the screen

**Pros:**
- The main good side of this version is that it is much more user friendly.
- Another good side is that it will display only the correct decoded version of the English encrypted text without clattering up the display with the incorrect decodings.

**Cons:**
- The main downside is that this version will work only for English encrypted text. The original version will work for any foreign language encrypted text that uses the pure Latin alphabet! 
- ~~Another downside maybe is that it will be slower than the original version. It uses more RAM memory resources and maybe slowing down the whole computer system as well if the encrypted text is too large... I'm not an expert on memory management but my logic tells me that this method uses much more memory resources.~~
After running some tests this hardly seems an issue!  :simple_smile:
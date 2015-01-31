myLength :: [Int] -> Int

myLength xs = foldr (\ x -> (+) 1) 0 xs

-----------------------------

myMaximum :: [Int] -> Int

myMaximum [x]			= x
myMaximum (x:xs)
	| x > myMaximum xs	= x
	| otherwise			= myMaximum xs

-----------------------------

average :: [Int] -> Float

average l = fromIntegral (mySum l) / fromIntegral (myLength l)
	where
		 mySum xs = foldr (+) 0 xs

-----------------------------

buildPalindrome	:: [Int] -> [Int]

buildPalindrome l = myReverse l ++ l
	where
		myReverse []	= [] 
		myReverse [x] 	= [x]
		myReverse (x:xs)	= myReverse xs ++ [x]

------------------------------

remove :: [Int] -> [Int] -> [Int]

remove l1 []		= l1
remove l1 (x:xs) 	= remove (removeOne l1 x) xs
	where
		removeOne [] i		= []
		removeOne (x:xs) i
			| x == i 		= removeOne xs i
			| otherwise		= (x:(removeOne xs i))

-------------------------------

flatten :: [[Int]] -> [Int]

flatten [] 		= []
flatten (x:xs)	= x ++ flatten xs

-------------------------------

oddsNevens :: [Int] -> ([Int],[Int])

oddsNevens l = (odds l, evens l)
	where
		odds []				= []
		odds (x:xs)
			| mod x 2 == 0	= odds xs
			| otherwise		= x : odds xs

		evens []			= []
		evens (x:xs)
			| mod x 2 == 0	= x: evens xs
			| otherwise		= evens xs

-------------------------------

primeDivisors :: Int -> [Int]

primeDivisors 0 = []
primeDivisors 1 = []
primeDivisors n = worker n 2
	where
		worker n i
			| n < i 			= []
			| (mod n i) == 0	= i : worker (reduce n i) (i+1)
			| otherwise 		= worker n (i+1)

		reduce n i
			| (mod n i) == 0 	= reduce (div n i) i
			| otherwise			= n
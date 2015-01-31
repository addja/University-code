-- eql: indicates if two lists are equal
eql :: [Int] -> [Int] -> Bool

eql l1 l2 
	| length l1 /= length l2 	= False
	| otherwise 				= all (== True) (zipWith (==) l1 l2)

-- product of the elements of a list
prod :: [Int] -> Int

prod l 	= foldl (*) 1 l

-- product of the even elements of a list
prodOfEvens :: [Int] -> Int

prodOfEvens l = prod $ filter even l

-- returns and idefinite list of powers of 2
powersOf2 :: [Int]

powersOf2 = iterate (*2) 1

-- returns the scalar product of two lists
scalarProduct :: [Float] -> [Float] -> Float

scalarProduct l1 l2 = foldl (+) 0 (zipWith (*) l1 l2)
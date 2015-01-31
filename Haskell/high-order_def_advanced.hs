-- countIf = number of elements on a list which
-- met a given condition

countIf :: (Int -> Bool) -> [Int] -> Int

countIf f l = foldr (\x y -> y + modSum x) 0 l
	where
		modSum x
			| f x 		= 1
			| otherwise = 0

-- pam = given a list of integers and a list of functions
-- returns a list with lists with the results of applying
-- to the integers the functions

pam :: [Int] -> [Int -> Int] -> [[Int]]

pam l fs = map (\x -> map x l) fs

-- pam2 = given a list of integers and a list of functions
-- returns a list with lists, each one has the results of
-- applying the functions to a single element

pam2 :: [Int] -> [Int -> Int] -> [[Int]]

pam2 l fs = map (\y -> map (\x -> x y) fs) l

-- filterFoldl = makes a filter and then a foldl 

filterFoldl :: (Int -> Bool) -> (Int -> Int -> Int) -> Int -> [Int] -> Int

filterFoldl f1 f2 b l = foldl f2 b (filter f1 l)

-- insert = given a list a function and an element, inserts the element
-- in the last position where the condition is fulfilled

insert :: (Int -> Int -> Bool) -> [Int] -> Int -> [Int]

insert f [] b = [b]
insert f (x:xs) b
	| f x b 	= x:(insert f xs b)
	| otherwise = [b,x]++xs

-- insertionSort using insert

insertionSort :: (Int -> Int -> Bool) -> [Int] -> [Int]

insertionSort f l = foldl (insert f) [] l
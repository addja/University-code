----------------------------------
------- INSERTION SORT -----------
----------------------------------

-- inserts and element in a sorted list keeping it sorted
insert :: [Int] -> Int -> [Int]

insert [] n		= [n]
insert (x:xs) n 
	| x > n		= n : x : xs
	| otherwise	= x : insert xs n

----------------------------------

isort :: [Int] -> [Int]

isort []		= []
isort (x:xs)	=  foldl insert [x] xs

----------------------------------
------- SELECTION SORT -----------
----------------------------------

-- removes the first occurrence of an element in list
remove :: [Int] -> Int -> [Int]

remove [x] n		= []	-- x = n because of constraint 
remove (x:xs) n
	| x == n		= xs
	| otherwise		= x : remove xs n

---------------------------------

ssort :: [Int] -> [Int]

ssort []		= []
ssort l			=  worker l []
	where
		worker [x] l 		= l ++ [x]
		worker (x:xs) l2	= worker (remove (x:xs) min) (l2 ++ [min])
			where
				min = findMin xs x

				findMin [] n 		= n
				findMin (x:xs) n
					| x < n			= findMin xs x
					| otherwise		= findMin xs n

----------------------------------
----------- MERGE SORT -----------
----------------------------------

-- merges two sorted lists generating a sorted list
merge :: [Int] -> [Int] -> [Int]

merge l [] 	= l
merge [] l 	= l
merge (x:xs) (y:ys)
	| x < y 	= x : merge xs (y:ys)
	| otherwise	= y : merge (x:xs) ys

----------------------------------

msort :: [Int] -> [Int]

msort [] 	= []
msort [x]	= [x]
msort l 	= merge (msort pre) (msort suf)
	where
		(pre, suf) = splitAt (div (length l) 2) l

----------------------------------
----------- QUICK SORT -----------
----------------------------------

qsort :: [Int] -> [Int]

qsort []     = []
qsort (p:xs) = qsort (fst $ sorter p xs) ++ [p] ++ qsort (snd $ sorter p xs)
    where
        sorter _ [] = ([],[])
        sorter n (y:ys)
        	| y < n		= (y:l1, l2)
        	| otherwise = (l1, y:l2)
            	where (l1,l2) = sorter n ys

----------------------------------
--------- GENERAL SORT -----------
----------------------------------

genQsort :: Ord a => [a] -> [a]

genQsort [] 	= []
genQsort [x] 	= [x]
genQsort (x:xs) = genQsort (fst $ sorter x xs) ++ [x] ++ genQsort (snd $ sorter x xs)
    where
        sorter :: Ord a => a -> [a] -> ([a],[a])
        sorter _ [] = ([],[])
        sorter n (y:ys)
            | y < n 		= (y:l1, l2)
            | otherwise 	= (l1, y:l2)
            	where (l1,l2) = sorter n ys
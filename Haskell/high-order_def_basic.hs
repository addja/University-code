-- Exercices using recursivity to implement
-- high level functions

myFoldl :: (a -> b -> a) -> a -> [b] -> a

myFoldl f n [] = n 
myFoldl f n (x:xs) = myFoldl f (f n x) xs

----------------------------------------

myFoldr :: (a -> b -> b) -> b -> [a] -> b

myFoldr f n [] = n
myFoldr f n (x:xs) = f x (myFoldr f n xs)

-----------------------------------------

myIterate :: (a -> a) -> a -> [a]

myIterate f a = a : myIterate f (f a)

-----------------------------------------

myUntil :: (a -> Bool) -> (a -> a) -> a -> a

myUntil c f a
	| c a		= a
	| otherwise = myUntil c f (f a)

------------------------------------------

myMap :: (a -> b) -> [a] -> [b]

myMap f l = myFoldl (\x y -> x ++ [f y]) [] l

-------------------------------------------

myFilter :: (a -> Bool) -> [a] -> [a]

myFilter f l = myFoldl (\x y -> x ++ check y) [] l
	where 
		check a
			| f a 		= [a]
			| otherwise = []
-------------------------------------------

myAll :: (a -> Bool) -> [a] -> Bool

myAll f l = myFoldr (\x y -> check x && y) True l
	where 
		check a
			| f a 		= True
			| otherwise = False


--------------------------------------------

myAny :: (a -> Bool) -> [a] -> Bool

myAny f l = myFoldr (\x y -> check x || y) False l
	where 
		check a
			| f a 		= True
			| otherwise = False

--------------------------------------------

myZip :: [a] -> [b] -> [(a,b)]

myZip [] l = []
myZip l [] = []
myZip (x:xs) (y:ys) = (x,y) : myZip xs ys

---------------------------------------------

myZipWith :: (a -> b -> c) -> [a] -> [b] -> [c]

myZipWith f [] l = []
myZipWith f l [] = []
myZipWith f (x:xs) (y:ys) = f x y : myZipWith f xs ys
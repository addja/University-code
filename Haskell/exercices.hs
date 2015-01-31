inflists :: [[Integer]]
inflists = iterate (\(y:ys) -> ys) inflist
	where
		inflist = iterate (+1) 1


takeLESum :: Integer -> [Integer] -> [Integer]
takeLESum n l = takeMod n l 0
	where 
		takeMod n (x:xs) tmp
			| tmp + x > n	= []
			| otherwise		= x : takeMod n xs (tmp+x)


consecutSum :: Integer -> [Integer]
consecutSum n = consec (take (fromIntegral n) inflists)
	where
		consec::[[Integer]] -> [Integer]
		consec []		= []
		consec (x:xs)
			| sum (takeLESum n x) == n	= takeLESum n x
			| otherwise					= consec xs

------------------------------------------------------------

dc :: (a -> Bool) -> (a -> b) -> (a -> [a]) -> (a -> [b] -> b) -> a -> b
dc trivial resol parteix combina problema
	| trivial problema	= resol problema
	| otherwise 		= combina problema (map (dc trivial resol parteix combina) (parteix problema))


mergesort :: Ord a => [a] -> [a]
mergesort l = dc (trivial) (resol) (parteix) (combina) l
	where
		trivial a = length a < 2
		
		resol l = l
		
		parteix x = fst sp : [snd sp]
			where
				sp = splitAt (div (length x) 2) x
		
		combina a (x:y:[])	= msort x y
			where
				msort a [] = a
				msort [] b = b
				msort a@(x:xs) b@(y:ys)
					| x < y		= x:msort xs b
					| otherwise	= y:msort a ys

---------------------------------------------------------

data Expressio a = Binari (a -> a -> a) (Expressio a) (Expressio a) |
					Unari (a -> a) (Expressio a) |
					Fulla a 

aval :: Expressio a -> a
aval (Fulla a)		= a
aval (Unari f e) 	= f (aval e)
aval (Binari f l r) = f (aval l) (aval r)

instance Eq a => Eq (Expressio a) where
	a == b = aval a == aval b

data NExpressio a = Nari (a -> a -> a) [NExpressio a] |
					NUnari (a -> a) (NExpressio a) |
					NFulla a 

naval :: NExpressio a -> a
naval (NFulla a)		= a
naval (NUnari f e) 	= f (naval e)
naval (Nari f (x:xs)) = foldl (\y z -> f y (naval z)) (naval x) xs 

instance Eq a => Eq (NExpressio a) where
	a == b = naval a == naval b


-- ============================================= --

genPairs :: Eq a => [a] -> [a] -> [a] -> [(a,a)]
genPairs l1 l2 l3 = [ (x,y) | x <- l1, y <- l2, pertany x y l3 == 1 ]
	where
		pertany x y []	= 0
		pertany x y (z:zs)
			| x == z 	= 1 + pertany x y zs
			| y == z 	= 1 + pertany x y zs
			| otherwise = pertany x y zs


nodup :: Eq a => [a] -> [a]
nodup []		= []
nodup (x:xs)	= x : nodup (filter (/= x) xs)


data Arbre a = Node a (Arbre a) (Arbre a)
				| Abuit deriving Show

ttake :: Int -> Arbre a -> Arbre a
ttake _ Abuit			= Abuit
ttake 0 _				= Abuit
ttake n (Node a l r)	= Node a (ttake (n-1) l) (ttake (n-1) r)

inftree :: Arbre Int
inftree = treeGen inflist
	where
		inflist = iterate (+1) 1

		treeGen (x:xs) = Node x (treeGen xs) (treeGen xs)


data ErrorList a = ErrorList [a] Int

instance Eq a => Eq (ErrorList a) where
	ErrorList l1 i1 == ErrorList l2 i2 = errors <= i1 && errors <= i2
		where
			errors = countErrors l1 l2

			countErrors [] [] 	= 0
			countErrors [] l 	= length l
			countErrors l [] 	= length l
			countErrors (x:xs) (y:ys)
				| x == y 	= countErrors xs ys
				| otherwise = 1 + countErrors xs ys


-- ================================= --

genSum :: [Integer]
genSum = scanl (+) 0 nats
	where nats = iterate (+1) 1

esSum :: Integer -> Bool
esSum n =  elem n (takeWhile (<= n) genSum)


selPred :: a -> [a -> Bool] -> ([a -> Bool],[a -> Bool])
selPred a l = (filter (\x -> x a) l, filter (\x -> not $ x a) l)

check :: a -> [a -> Bool] -> [Bool]
check a f = map (\x -> x a) f

arbreFibonacci :: Arbre a -> Integer
arbreFibonacci Abuit		 		= 0
arbreFibonacci (Node _ Abuit Abuit)	= 1
arbreFibonacci (Node _ l r)
	| leftres == -1 				= -1
	| rightres == -1 				= -1
	| leftres == rightres + 1 		= leftres + 1 
	| leftres + 1 == rightres 		= rightres + 1 
	| otherwise 					= -1
		where
			leftres = arbreFibonacci l 
			rightres = arbreFibonacci r


data MOList a = MOList [[a]]

esta :: Eq a => a -> MOList a -> Bool
esta x (MOList lm) = foldl (\y z -> y || elem x z) False lm

inclosa :: Eq a => [a] -> MOList a -> Bool
inclosa l (MOList lm) = length l == length lm && not (elem False (zipWith (\y z -> elem y z) l lm))
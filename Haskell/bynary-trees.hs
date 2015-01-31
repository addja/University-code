-- Binary trees in haskell

data Tree a = Node a (Tree a) (Tree a) 
 			| Empty deriving (Show)

-- size = number of nodes

size :: Tree a -> Int

size Empty 			= 0
size (Node r lc rc) = 1 + size lc + size rc

-- height = height of a trees

height :: Tree a -> Int

height Empty 			= 0
height (Node r lc rc) = 1 + max (height lc) (height rc)

-- equal = says if two trees are equal or not

equal :: Eq a => Tree a -> Tree a -> Bool

equal Empty Empty = True
equal Empty _ 	  = False
equal _ Empty	  = False
equal (Node r1 lc1 rc1) (Node r2 lc2 rc2) =	
	r1 == r2 && equal lc1 lc2 && equal rc1 rc2

-- isomorphic = says if two trees are isomorphic or not

isomorphic :: Eq a => Tree a -> Tree a -> Bool

isomorphic Empty Empty 	= True
isomorphic Empty _		= False
isomorphic _ Empty	  	= False
isomorphic (Node x lc1 rc1) (Node y lc2 rc2) = x == y && (notRotated || rotated) 
	where
		notRotated = isomorphic lc1 lc2 && isomorphic rc1 rc2
		rotated = equal lc1 rc2 && equal rc1 lc2

-- preOrder = returns a list with the nodes visited in preorder

preOrder :: Tree a -> [a]

preOrder Empty = []
preOrder (Node r lc rc) = 
	r : preOrder lc ++ preOrder rc

-- postOrder = returns a list with the nodes visited in postorder

postOrder :: Tree a -> [a]

postOrder Empty = []
postOrder (Node r lc rc) = 
	postOrder lc ++ postOrder rc ++ [r]

-- inOrder = returns a list with the nodes visited in inorder

inOrder :: Tree a -> [a]

inOrder Empty = []
inOrder (Node r lc rc) = 
	inOrder lc ++ r : inOrder rc

-- breadthFirst = returns a list with the nodes visited in a
-- breadthFirstSearch

breadthFirst :: Tree a -> [a]

breadthFirst Empty 			 	= []
breadthFirst (Node r lc rc)		= r : bf [lc,rc]
	where 
		bf [] 				 	= []
		bf (Empty:xs)		 	= bf xs
		bf (Node r lc rc : xs)	= r : (bf $ xs ++ [lc,rc]) 

-- given a preOrder list and a inOrder list of a BST generates the BST

build :: Eq a => [a] -> [a] -> Tree a

build _ []		= Empty
build [] _		= Empty
build (x:xs) l 	= Node x (build (fst pre) (fst ino)) (build (snd pre) (snd ino))
	where 
		ino = (takeWhile (/= x) l, tail $ dropWhile (/= x) l)
		pre = (filter (\y -> contained y (fst ino)) xs, filter (\y -> contained y (snd ino)) xs)
		
		contained z [] 	= False
		contained z (w:ws)
			| z == w 	= True
			| otherwise	= contained z ws

-- overlap threes given a function

overlap :: (a -> a -> a) -> Tree a -> Tree a -> Tree a

overlap f Empty t 								= t
overlap f t Empty 								= t
overlap f (Node a t1a t2a) (Node b t1b t2b) = 
	Node (f a b) (overlap f t1a t1b) (overlap f t2a t2b) 



 
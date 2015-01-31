-- infinite list of ones
ones :: [Integer]

ones = 1 : ones

-- infinite list of naturals
nats :: [Integer]

nats = iterate (+1) 0

-- infinite lists of integers 
ints :: [Integer]

ints = iterate worker 0
	where 
		worker x
			| x > 0 	= -x
			| otherwise = -x + 1

-- triangular numbers
triangulars :: [Integer]

triangulars = tail (scanl (+) 0 $ tail nats)

-- factorials
factorials :: [Integer]

factorials = scanl (*) 1 $ tail nats

-- fibonacci numbers
fibs :: [Integer]

fibs = scanl (\x (y,z) -> y) 0 $ tail pairFib
	where pairFib = scanl (\(x,y) z -> (y,y+x)) (0,1) pairFib
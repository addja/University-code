absValue :: Int -> Int

absValue n
	| n > 0		= n
	| otherwise	= -n

------------------------------------

power :: Int -> Int -> Int

power n m
	| m == 0	= 1
	| m == 1	= n
	| m == 2 	= n * n
	| odd m 	= n * power n (m-1)
	| otherwise = power (power n (div m 2)) 2

------------------------------------

isPrime :: Int -> Bool

isPrime 0 = False
isPrime 1 = False
isPrime n = noDivisors 2
	where
		noDivisors i
			| i == n 		= True
			| mod n i == 0	= False
			| otherwise		= noDivisors (i+1)

------------------------------------

slowFib :: Int -> Int

slowFib 0 = 0
slowFib 1 = 1
slowFib n = slowFib (n-1) + slowFib (n-2)

------------------------------------

quickFib :: Int -> Int

quickFib n = fst (fib n)
	where
		fib 0 = (0, 0)
		fib 1 = (1, 0)
		fib i = (a+b, a)
			where (a,b) = fib (i-1)